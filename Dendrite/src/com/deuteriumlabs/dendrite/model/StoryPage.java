package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Projection;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;

/**
 * Represents a page of a story. <code>StoryPage</code> instances act as nodes
 * in a tree to form a complete story. The unordered nature of the page IDs of
 * the pages in a story causes the branches of different stories to be
 * interwoven.
 */
public class StoryPage extends Model {
	private static final String ELLIPSIS = "…";
	private static final int MAX_SUMMARY_LEN = 30;
	private static final char ANCESTRY_DELIMITER = '>';
	private static final String ANCESTRY_PROPERTY = "ancestry";
	private static final String AUTHOR_ID_PROPERTY = "authorId";
	private static final String AUTHOR_NAME_PROPERTY = "authorName";
	private static final String BEGINNING_NUMBER_PROPERTY = "beginningNumber";
	private static final String BEGINNING_VERSION_PROPERTY = "beginningVersion";
	private static final String FORMERLY_LOVING_USERS_PROPERTY = "formerlyLovingUsers";
	private static final String ID_NUMBER_PROPERTY = "idNumber";
	private static final String ID_VERSION_PROPERTY = "idVersion";
	private static final String KIND_NAME = "StoryPage";
	private static final int LEN_ALPHABET = 26;
	private static final char[] LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };
	private static final String LOVING_USERS_PROPERTY = "lovingUsers";
	private static final String TAGS_PROPERTY = "tags";
	private static final String TEXT_PROPERTY = "text";
	private static final int SIZE_INFLUENCE = 1;
	private static final int LOVE_INFLUENCE = 1;
	private static final String IS_FIRST_PG_PROPERTY = "isFirstPg";

	public static String convertNumberToVersion(final int num) {
		final int lowNum = ((num - 1) % LEN_ALPHABET) + 1;
		final char lowLetter = convertNumToLetter(lowNum);
		final int highNums = (num - 1) / LEN_ALPHABET;
		String versionStr = Character.toString(lowLetter);
		if (highNums > 0) {
			final String highLetters = convertNumberToVersion(highNums);
			versionStr = highLetters + versionStr;
		}
		return versionStr;
	}

	private static char convertNumToLetter(int num) {
		Map<Integer, Character> map = new HashMap<Integer, Character>();
		for (int i = 0; i < LETTERS.length; i++)
			map.put(i + 1, LETTERS[i]);
		return map.get(num);
	}

	public static int countAllPagesWrittenBy(final String authorId) {
		final Query query = new Query(KIND_NAME);
		final Filter filter = getAuthorIdFilter(authorId);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}

	/**
	 * @param greater
	 * @param less
	 * @return
	 */
	static int countSubtreeBetween(final String greater, final String less) {
		final Query query = new Query(KIND_NAME);
		final String propertyName = ANCESTRY_PROPERTY;
		FilterOperator operator = FilterOperator.GREATER_THAN;
		String value = greater;
		final Filter greaterFilter;
		greaterFilter = new FilterPredicate(propertyName, operator, value);
		operator = FilterOperator.LESS_THAN;
		value = less;
		final Filter lessFilter;
		lessFilter = new FilterPredicate(propertyName, operator, value);
		final Filter filter;
		filter = CompositeFilterOperator.and(greaterFilter, lessFilter);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}

	public static int countVersions(final int num) {
		final Query query = new Query(KIND_NAME);
		final Filter filter = getIdNumFilter(num);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}

	public static List<StoryPage> getAllVersions(final PageId pageId) {
		final Query query = new Query(KIND_NAME);
		final int num = pageId.getNumber();
		final Filter filter = StoryPage.getIdNumFilter(num);
		query.setFilter(filter);
		DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final List<Entity> entities = preparedQuery.asList(fetchOptions);
		final List<StoryPage> pages = getPgsFromEntities(entities);
		return pages;
	}

	private static Filter getAuthorIdFilter(String authorId) {
		final String propertyName = AUTHOR_ID_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = authorId;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Builds a filter to restrict a query to a particular ID.
	 * 
	 * @param id
	 *            The page ID to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdFilter(final PageId id) {
		final int num = id.getNumber();
		final Filter numFilter = getIdNumFilter(num);
		final String version = id.getVersion();
		final Filter versionFilter = getIdVersionFilter(version);
		return CompositeFilterOperator.and(numFilter, versionFilter);
	}

	/**
	 * Builds a filter to restrict a query to a particular ID number. Without
	 * also applying a filter to restrict to a particular ID version, this will
	 * provide a collection of versions of one page in a story.
	 * 
	 * @param num
	 *            The page number to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdNumFilter(final int num) {
		final String propertyName = ID_NUMBER_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = num;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the number component of the story page ID from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the ID
	 * @return The number component of the story page ID
	 */
	private static int getIdNumFromEntity(final Entity entity) {
		final Long num = (Long) entity.getProperty(ID_NUMBER_PROPERTY);
		return num.intValue();
	}

	/**
	 * Builds a filter to restrict a query to a particular ID version. This will
	 * probably not be very useful without other query filters.
	 * 
	 * @param version
	 *            The page version to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdVersionFilter(final String version) {
		final String propertyName = ID_VERSION_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = version;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the version component of the story page ID from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the ID
	 * @return The version component of the story page ID
	 */
	private static String getIdVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(ID_VERSION_PROPERTY);
	}

	public static List<StoryPage> getPagesWrittenBy(String authorId, int start,
			int end) {
		final Query query = new Query(KIND_NAME);
		query.addSort(BEGINNING_NUMBER_PROPERTY);
		query.addSort(ID_NUMBER_PROPERTY);
		query.addSort(ID_VERSION_PROPERTY);
		final Filter filter = getAuthorIdFilter(authorId);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final int limit = end - start;
		final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
		final int offset = start;
		fetchOptions.offset(offset);
		List<Entity> entities = preparedQuery.asList(fetchOptions);
		List<StoryPage> pgs = getPgsFromEntities(entities);
		return pgs;
	}

	/**
	 * @param pageId
	 * @return
	 */
	public static StoryPage getParentOf(final PageId childId) {
		childId.setVersion("a");
		final StoryPage child = new StoryPage();
		child.setId(childId);
		child.read();
		return child.getParent();
	}

	private static List<StoryPage> getPgsFromEntities(
			final List<Entity> entities) {
		final List<StoryPage> pgs = new ArrayList<StoryPage>();
		for (final Entity entity : entities) {
			StoryPage pg = new StoryPage(entity);
			pgs.add(pg);
		}
		return pgs;
	}

	public static String getRandomVersion(final PageId id) {
		id.setVersion("a");
		final StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.read();
		if (pg.isInStore() == true) {
			final long denominator = pg.calculateChanceDenominator();
			Random generator = new Random();
			int randomNum = generator.nextInt((int) denominator) + 1;
			while (randomNum >= 0) {
				final long numerator = pg.calculateChanceNumerator();
				randomNum -= numerator;
				if (randomNum >= 0) {
					pg.incrementVersion();
				}
			}
			final String version = pg.getId().getVersion();
			return version;
		} else {
			return "a";
		}
	}

	private List<PageId> ancestry;
	private String authorId;
	private String authorName;
	private PageId beginning;
	private String chance;
	private List<String> formerlyLovingUsers;
	private PageId id;
	private List<String> lovingUsers;
	private StoryPage parent;
	private List<String> tags;
	private Text text;
	private boolean isFirstPg;

	public StoryPage() {
		this.setBeginning(null);
	}

	public StoryPage(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	/**
	 * The chance of a page being displayed is determined by two factors:
	 * 
	 * (1) the size of its subtree, which is measured by counting all of its
	 * descendants, and
	 * 
	 * (2) its number of lovers.
	 * 
	 * Each factor is calculated as a fraction of two integers and are each
	 * multiplied by a constant representing how influential each factor is upon
	 * the total weight.
	 * 
	 * For example, a page has 3 children while all alternatives have 8
	 * children. This produces a chance of 3/8 from the size of its subtree. The
	 * page also has 10 lovers while all alternatives have 15 lovers. This
	 * produces a chance of 10/15 from the lovers. In this example, the subtree
	 * size accounts for 6/10 of the total weight, while the lovers accounts for
	 * 4/10 of the total weight. The total weight can then be calculated by
	 * summing the product of each factor by how influential it is:
	 * 
	 * (3/8 * 600/1000) + (10/15 * 400/1000)
	 * 
	 * = ((3 * 600 * 15) + (10 * 400 * 8) ) / (8 * 15 * 1000)
	 * 
	 * = 0.49166666666
	 * 
	 * This example page would have a 49% chance of being displayed.
	 */
	private void calculateChance() {
		final long numerator = this.calculateChanceNumerator();
		final long denominator = this.calculateChanceDenominator();
		this.setChance(numerator + "/" + denominator);
	}

	/**
	 * @return
	 */
	private long calculateChanceDenominator() {
		final int sizeDenominator = this.getSizeDenominator();
		final int sizeInfluence = this.getSizeInfluence();
		final int loveDenominator = this.getLoveDenominator();
		final int loveInfluence = this.getLoveInfluence();
		return (sizeDenominator * sizeInfluence)
				+ (loveDenominator * loveInfluence);
	}

	private int getSizeDenominator() {
		if (this.isTheFirstPage()) {
			/*
			 * For example, the first page of the story is 1a. Find the number
			 * of nodes in all subtrees of all versions of 1a. So, if there are
			 * two versions, 1a and 1b, I need to find the size of both subtrees
			 * and add 1 to each size and then add them together. This is O(n)
			 * where n is the number of versions. Is there an O(1) solution?
			 * We're looking for all nodes with subtrees which match the
			 * following regex: "1[a-z]+.*". From this, we know that the string
			 * must be greater than "1\`" and less than "1\{".
			 */
			return this.getSizeOfBeginningSubtree();
		} else {
			return this.getSizeOfSiblingSubtrees();
		}
	}

	/**
	 * @return
	 */
	private int calculateChanceNumerator() {
		final int sizeNumerator = this.getSizeNumerator();
		final int sizeInfluence = this.getSizeInfluence();
		final int loveNumerator = this.getLoveNumerator();
		final int loveInfluence = this.getLoveInfluence();
		return (sizeNumerator * sizeInfluence)
				+ (loveNumerator + loveInfluence);
	}

	private int getLoveDenominator() {
		final int numLoversOfAllVersions = this.getNumLoversOfAllVersions();
		return numLoversOfAllVersions;
	}

	private int getNumLoversOfAllVersions() {
		final PageId id = this.getId();
		final int pgNum = id.getNumber();
		final int numLovers = StoryPage.countLoversOfAllVersions(pgNum);
		return numLovers;
	}

	private static int countLoversOfAllVersions(final int pgNum) {
		final Query query = new Query(KIND_NAME);
		Projection loversProjection;
		String propertyName = LOVING_USERS_PROPERTY;
		loversProjection = new PropertyProjection(propertyName, String.class);
		query.addProjection(loversProjection);
		FilterPredicate filter;
		filter = Query.FilterOperator.EQUAL.of(ID_NUMBER_PROPERTY, pgNum);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final List<Entity> entities = store.prepare(query).asList(fetchOptions);
		int count = 0;
		for (final Entity entity : entities) {
			if (entity.getProperty(LOVING_USERS_PROPERTY) != null) {
				count++;
			}
		}
		return count;
	}

	private int getLoveNumerator() {
		final int numLoversOfThisPg = this.getNumLovingUsers();
		return numLoversOfThisPg;
	}

	private int getLoveInfluence() {
		return LOVE_INFLUENCE;
	}

	private int getSizeInfluence() {
		return SIZE_INFLUENCE;
	}

	private int getSizeNumerator() {
		return this.getSizeOfSubtree() + 1;
	}

	@Override
	public void create() {
		this.generateAncestry();
		this.determineWhetherFirstPg();
		super.create();
	}

	public void determineWhetherFirstPg() {
		final PageId beginningId = this.getBeginning();
		final int beginningNum = beginningId.getNumber();

		final PageId pgId = this.getId();
		final int pgNum = pgId.getNumber();

		final boolean isFirstPg = (beginningNum == pgNum);
		this.setFirstPg(isFirstPg);
	}

	/**
     * 
     */
	public void generateAncestry() {
		final StoryPage parent = this.getParent();
		final List<PageId> ancestry;
		if (parent != null) {
			parent.read();
			ancestry = parent.getAncestry();
		} else {
			ancestry = new ArrayList<PageId>();
		}
		final PageId id = this.getId();
		ancestry.add(id);
		this.setAncestry(ancestry);
	}

	/**
	 * @return ancestry The list of pages that lead to this page.
	 */
	public List<PageId> getAncestry() {
		return this.ancestry;
	}

	/**
	 * @param entity
	 * @return
	 */
	private List<PageId> getAncestryFromEntity(final Entity entity) {
		final String str = (String) entity.getProperty(ANCESTRY_PROPERTY);
		return parseAncestry(str);
	}

	public String getAuthorId() {
		return this.authorId;
	}

	private String getAuthorIdFromEntity(final Entity entity) {
		return (String) entity.getProperty(AUTHOR_ID_PROPERTY);
	}

	public String getAuthorName() {
		return this.authorName;
	}

	private String getAuthorNameFromEntity(final Entity entity) {
		return (String) entity.getProperty(AUTHOR_NAME_PROPERTY);
	}

	public PageId getBeginning() {
		return this.beginning;
	}

	private int getBeginningNumFromEntity(final Entity entity) {
		final String property = BEGINNING_NUMBER_PROPERTY;
		final Long num = (Long) entity.getProperty(property);
		if (num != null) {
			return num.intValue();
		} else {
			return 0;
		}
	}

	private String getBeginningVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(BEGINNING_VERSION_PROPERTY);
	}

	/**
	 * @return
	 */
	public String getChance() {
		if (this.chance == null) {
			this.calculateChance();
		}
		return this.chance;
	}

	/**
	 * @return
	 */
	public List<String> getFormerlyLovingUsers() {
		return this.formerlyLovingUsers;
	}

	/**
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getFormerlyLovingUsersFromEntity(final Entity entity) {
		final String property = FORMERLY_LOVING_USERS_PROPERTY;
		return (List<String>) entity.getProperty(property);
	}

	/**
	 * Returns the ID of this story page.
	 * 
	 * @return The ID of this story page
	 */
	public PageId getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deuteriumlabs.dendrite.model.Model#getKindName()
	 */
	@Override
	String getKindName() {
		return KIND_NAME;
	}

	/**
	 * @return
	 */
	public List<String> getLovingUsers() {
		return this.lovingUsers;
	}

	/**
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getLovingUsersFromEntity(final Entity entity) {
		return (List<String>) entity.getProperty(LOVING_USERS_PROPERTY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deuteriumlabs.dendrite.model.Model#getMatchingQuery()
	 */
	@Override
	Query getMatchingQuery() {
		final Query query = new Query(KIND_NAME);
		final PageId id = this.getId();
		final Filter filter = getIdFilter(id);
		return query.setFilter(filter);
	}

	/**
	 * @return
	 */
	public int getNumLovingUsers() {
		final List<String> lovingUsers = this.getLovingUsers();
		return lovingUsers.size();
	}

	/**
	 * @return
	 */
	public StoryPage getParent() {
		return this.parent;
	}

	/**
	 * @return
	 */
	private int getSizeOfBeginningSubtree() {
		final int num = this.getId().getNumber();
		final String greaterThanOrEqual = num + "`";
		final String lessThan = num + "{";
		return StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
	}

	/**
	 * @return
	 */
	private int getSizeOfSiblingSubtrees() {
		final List<PageId> ancestry = this.getAncestry();
		String subtreeAncestry = "";
		for (int i = 0; i < ancestry.size() - 1; i++) {
			subtreeAncestry += ancestry.get(i);
			subtreeAncestry += String.valueOf(ANCESTRY_DELIMITER);
		}
		final int num = this.getId().getNumber();
		subtreeAncestry += num;
		final String greaterThanOrEqual = subtreeAncestry + "`";
		final String lessThan = subtreeAncestry + "{";
		return StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
	}

	/**
	 * @return
	 */
	private int getSizeOfSubtree() {
		final List<PageId> ancestry = this.getAncestry();
		String subtreeAncestry = "";
		for (int i = 0; i < ancestry.size(); i++) {
			subtreeAncestry += ancestry.get(i);
			if (i < ancestry.size() - 1) {
				subtreeAncestry += String.valueOf(ANCESTRY_DELIMITER);
			}
		}
		final String greater = subtreeAncestry + ANCESTRY_DELIMITER;
		final char nextChar = ANCESTRY_DELIMITER + 1;
		final String less = subtreeAncestry + nextChar;
		return StoryPage.countSubtreeBetween(greater, less);
	}

	public List<String> getTags() {
		if (this.tags == null) {
			this.initTags();
		}
		return this.tags;
	}

	private void initTags() {
		final List<String> tags = new ArrayList<String>();
		this.setTags(tags);
	}

	@SuppressWarnings("unchecked")
	private List<String> getTagsFromEntity(final Entity entity) {
		return (List<String>) entity.getProperty(TAGS_PROPERTY);
	}

	/**
	 * Returns the text of this story page.
	 * 
	 * @return The text of this story page
	 */
	public Text getText() {
		return this.text;
	}

	/**
	 * Returns the text of the story page from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the text
	 * @return The text of the story page
	 */
	private Text getTextFromEntity(final Entity entity) {
		return (Text) entity.getProperty(TEXT_PROPERTY);
	}

	/**
	 * @return
	 */
	private void incrementVersion() {
		this.getId().incrementVersion();
		this.read();
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean isLovedBy(final String userId) {
		final List<String> lovingUsers = this.getLovingUsers();
		return lovingUsers.contains(userId);
	}

	/**
	 * @return
	 */
	private boolean isTheFirstPage() {
		return this.getId().getNumber() == this.getBeginning().getNumber();
	}

	/**
	 * @param str
	 * @return
	 */
	private List<PageId> parseAncestry(final String str) {
		final List<PageId> ancestry = new ArrayList<PageId>();
		if (str != null && str.equals("") == false) {
			final String regex = String.valueOf(ANCESTRY_DELIMITER);
			final String[] words = str.split(regex);
			for (final String word : words) {
				ancestry.add(new PageId(word));
			}
		}
		return ancestry;
	}

	/**
	 * @param entity
	 */
	private void readAncestryFromEntity(final Entity entity) {
		final List<PageId> ancestry = getAncestryFromEntity(entity);
		this.setAncestry(ancestry);
		this.setParentFromAncestry(ancestry);
	}

	private void readAuthorIdFromEntity(final Entity entity) {
		final String authorId = getAuthorIdFromEntity(entity);
		this.setAuthorId(authorId);
	}

	private void readAuthorNameFromEntity(final Entity entity) {
		final String authorName = getAuthorNameFromEntity(entity);
		this.setAuthorName(authorName);
	}

	private void readBeginningFromEntity(final Entity entity) {
		final PageId beginning = new PageId();
		final int num = getBeginningNumFromEntity(entity);
		beginning.setNumber(num);
		final String version = getBeginningVersionFromEntity(entity);
		beginning.setVersion(version);
		final boolean isValid = beginning.isValid();
		if (isValid)
			this.setBeginning(beginning);
		else
			this.setBeginning(null);
	}

	/**
	 * @param entity
	 */
	private void readFormerlyLovingUsersFromEntity(final Entity entity) {
		List<String> users = getFormerlyLovingUsersFromEntity(entity);
		if (users == null) {
			users = new ArrayList<String>();
		}
		this.setFormerlyLovingUsers(users);
	}

	/**
	 * Reads the values from the entity corresponding to the ID of this story
	 * page.
	 * 
	 * @param entity
	 *            The entity storing the ID
	 */
	private void readIdFromEntity(final Entity entity) {
		final PageId id = new PageId();
		final int num = getIdNumFromEntity(entity);
		id.setNumber(num);
		final String version = getIdVersionFromEntity(entity);
		id.setVersion(version);
		this.setId(id);
	}

	/**
	 * @param entity
	 */
	private void readLovingUsersFromEntity(final Entity entity) {
		List<String> users = getLovingUsersFromEntity(entity);
		if (users == null) {
			users = new ArrayList<String>();
		}
		this.setLovingUsers(users);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google
	 * .appengine.api.datastore.Entity)
	 */
	@Override
	void readPropertiesFromEntity(final Entity entity) {
		this.readAncestryFromEntity(entity);
		this.readIdFromEntity(entity);
		this.readTextFromEntity(entity);
		this.readBeginningFromEntity(entity);
		this.readAuthorNameFromEntity(entity);
		this.readAuthorIdFromEntity(entity);
		this.readLovingUsersFromEntity(entity);
		this.readFormerlyLovingUsersFromEntity(entity);
		this.readTagsFromEntity(entity);
		this.readIsFirstPgFromEntity(entity);
	}

	private void readIsFirstPgFromEntity(final Entity entity) {
		final boolean isFirstPg = getIsFirstPgFromEntity(entity);
		this.setFirstPg(isFirstPg);
	}

	private void setFirstPg(final boolean isFirstPg) {
		this.isFirstPg = isFirstPg;
	}

	private boolean getIsFirstPgFromEntity(final Entity entity) {
		final String propertyName = IS_FIRST_PG_PROPERTY;
		final Boolean property = (Boolean) entity.getProperty(propertyName);
		if (property != null) {
			return property.booleanValue();
		} else {
			return false;
		}
	}

	private void readTagsFromEntity(final Entity entity) {
		List<String> tags = getTagsFromEntity(entity);
		if (tags == null) {
			tags = new ArrayList<String>();
		}
		this.setTags(tags);
	}

	/**
	 * Reads the value from the entity corresponding to the text of this story
	 * page.
	 * 
	 * @param entity
	 *            The entity storing the text
	 */
	private void readTextFromEntity(final Entity entity) {
		final Text text = getTextFromEntity(entity);
		this.setText(text);
	}

	/**
	 * @param ancestry
	 */
	private void setAncestry(final List<PageId> ancestry) {
		this.ancestry = ancestry;
	}

	/**
	 * @param entity
	 */
	private void setAncestryInEntity(final Entity entity) {
		final List<PageId> ancestry = this.getAncestry();
		String entityVal = "";
		for (int i = 0; i < ancestry.size(); i++) {
			entityVal += ancestry.get(i);
			if (i < ancestry.size() - 1) {
				entityVal += ANCESTRY_DELIMITER;
			}
		}
		entity.setProperty(ANCESTRY_PROPERTY, entityVal);
	}

	public void setAuthorId(final String authorId) {
		this.authorId = authorId;
	}

	private void setAuthorIdInEntity(final Entity entity) {
		final String authorId = this.getAuthorId();
		if (authorId != null) {
			entity.setProperty(AUTHOR_ID_PROPERTY, authorId);
		}
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	private void setAuthorNameInEntity(final Entity entity) {
		final String authorName = this.getAuthorName();
		if (authorName != null) {
			entity.setProperty(AUTHOR_NAME_PROPERTY, authorName);
		}
	}

	public void setBeginning(PageId beginning) {
		this.beginning = beginning;
	}

	private void setBeginningInEntity(final Entity entity) {
		final PageId beginning = this.getBeginning();
		final int num = beginning.getNumber();
		entity.setProperty(BEGINNING_NUMBER_PROPERTY, num);
		final String version = beginning.getVersion();
		entity.setProperty(BEGINNING_VERSION_PROPERTY, version);
	}

	/**
	 * @param chance
	 */
	private void setChance(final String chance) {
		this.chance = chance;
	}

	/**
	 * @param users
	 */
	public void setFormerlyLovingUsers(final List<String> users) {
		this.formerlyLovingUsers = users;
	}

	/**
	 * @param entity
	 */
	private void setFormerlyLovingUsersInEntity(final Entity entity) {
		final List<String> users = this.getFormerlyLovingUsers();
		entity.setProperty(FORMERLY_LOVING_USERS_PROPERTY, users);
	}

	/**
	 * Sets the ID of this story page.
	 * 
	 * @param id
	 *            The new ID for this story page
	 */
	public void setId(final PageId id) {
		this.id = id;
		final PageId beginning = this.getBeginning();
		if (beginning == null) {
			this.setBeginning(id);
		}
	}

	/**
	 * Sets the values in the entity corresponding to the ID for this story
	 * page.
	 * 
	 * @param entity
	 *            The entity in which the values are to be stored
	 */
	private void setIdInEntity(final Entity entity) {
		final PageId id = this.getId();
		final int num = id.getNumber();
		entity.setProperty(ID_NUMBER_PROPERTY, num);
		final String version = id.getVersion();
		entity.setProperty(ID_VERSION_PROPERTY, version);
	}

	/**
	 * @param lovingUsers
	 */
	public void setLovingUsers(final List<String> lovingUsers) {
		this.lovingUsers = lovingUsers;
	}

	/**
	 * @param entity
	 */
	private void setLovingUsersInEntity(final Entity entity) {
		final List<String> lovingUsers = this.getLovingUsers();
		entity.setProperty(LOVING_USERS_PROPERTY, lovingUsers);
	}

	public void setParent(final StoryPage parent) {
		this.parent = parent;
	}

	/**
	 * @param ancestry
	 */
	private void setParentFromAncestry(final List<PageId> ancestry) {
		if (ancestry.size() > 1) {
			final int parentIndex = ancestry.size() - 2;
			final PageId parentId = ancestry.get(parentIndex);
			final StoryPage parent = new StoryPage();
			parent.setId(parentId);
			parent.read();
			this.setParent(parent);
		} else {
			this.setParent(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google
	 * .appengine.api.datastore.Entity)
	 */
	@Override
	void setPropertiesInEntity(final Entity entity) {
		this.setAncestryInEntity(entity);
		this.setIdInEntity(entity);
		this.setTextInEntity(entity);
		this.setBeginningInEntity(entity);
		this.setAuthorNameInEntity(entity);
		this.setAuthorIdInEntity(entity);
		this.setLovingUsersInEntity(entity);
		this.setFormerlyLovingUsersInEntity(entity);
		this.setTagsInEntity(entity);
		this.setIsFirstPgInEntity(entity);
	}

	private void setIsFirstPgInEntity(final Entity entity) {
		final boolean isFirstPg = this.isFirstPg();
		entity.setProperty(IS_FIRST_PG_PROPERTY, isFirstPg);
	}

	public boolean isFirstPg() {
		return this.isFirstPg;
	}

	private void setTags(final List<String> tags) {
		this.tags = tags;
	}

	private void setTagsInEntity(final Entity entity) {
		final List<String> tags = this.getTags();
		entity.setProperty(TAGS_PROPERTY, tags);
	}

	/**
	 * Sets the text of this story page.
	 * 
	 * @param string
	 *            The new text for this story page
	 */
	public void setText(final String string) {
		final Text text = new Text(string);
		this.setText(text);
	}

	public void setText(final Text text) {
		this.text = text;
	}

	/**
	 * Sets the value in the entity corresponding to the text of this story
	 * page.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setTextInEntity(Entity entity) {
		final Text text = this.getText();
		entity.setProperty(TEXT_PROPERTY, text);
	}

	@Override
	public String toString() {
		final PageId id = this.getId();
		final String summary = this.getText().toString();
		return "{ " + id + ": " + summary + " }";
	}

	public boolean addTag(final String tag) {
		final List<String> tags = this.getTags();
		final boolean isDuplicate = tags.contains(tag);
		if (isDuplicate == false) {
			tags.add(tag);
			this.setTags(tags);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeTag(final String tag) {
		final List<String> tags = this.getTags();
		boolean isUpdateNeeded = false;
		while (tags.contains(tag)) {
			tags.remove(tag);
			isUpdateNeeded = true;
		}
		if (isUpdateNeeded) {
			this.setTags(tags);
			return true;
		} else {
			return false;
		}
	}

	public String getSummary() {
		final Text text = this.getText();
		final String full = text.getValue();
		return sumarise(full, MAX_SUMMARY_LEN);
	}

	public String getLongSummary() {
		final Text text = this.getText();
		if (text != null) {
			final String full = text.getValue();
			return sumarise(full, 100);
		} else {
			return "This page has not been written yet.";
		}
	}

	private String sumarise(final String full, final int len) {
		final int fullSize = full.length();
		if (fullSize < (len - 1))
			return full;
		else {
			final String cropped = full.substring(0, (len - 1));
			return cropped + ELLIPSIS;
		}
	}

	public static int countLoversBetween(final String greater, final String less) {
		final Query query = new Query(KIND_NAME);
		Class<String> type = String.class;
		String projectionProperty = LOVING_USERS_PROPERTY;
		final PropertyProjection projection;
		projection = new PropertyProjection(projectionProperty, type);
		query.addProjection(projection);
		final String propertyName = ANCESTRY_PROPERTY;
		FilterOperator operator = FilterOperator.GREATER_THAN;
		String value = greater;
		final Filter greaterFilter;
		greaterFilter = new FilterPredicate(propertyName, operator, value);
		operator = FilterOperator.LESS_THAN;
		value = less;
		final Filter lessFilter;
		lessFilter = new FilterPredicate(propertyName, operator, value);
		final Filter filter;
		filter = CompositeFilterOperator.and(greaterFilter, lessFilter);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final List<Entity> entities = preparedQuery.asList(fetchOptions);
		int count = 0;
		for (final Entity entity : entities) {
			final String loverId;
			loverId = (String) entity.getProperty(projectionProperty);
			if (loverId != null && loverId.equals("") == false) {
				count++;
			}
		}
		return count;
	}

	public static List<StoryPage> getFirstPgsMatchingTag(final String tag) {
		final Query query = new Query(KIND_NAME);
		String propertyName = IS_FIRST_PG_PROPERTY;
		FilterOperator operator = FilterOperator.EQUAL;
		final Filter isFirstPgFilter;
		isFirstPgFilter = new FilterPredicate(propertyName, operator, true);
		propertyName = TAGS_PROPERTY;
		final String propertyVal = tag;
		final Filter tagFilter;
		tagFilter = new FilterPredicate(propertyName, operator, propertyVal);
		final Filter filter;
		filter = CompositeFilterOperator.and(isFirstPgFilter, tagFilter);
		query.setFilter(filter);
		query.addSort(ID_NUMBER_PROPERTY);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final List<Entity> entities = preparedQuery.asList(fetchOptions);

		final List<StoryPage> pgs = new ArrayList<StoryPage>();
		for (final Entity entity : entities) {
			final StoryPage pg = new StoryPage(entity);
			pgs.add(pg);
		}
		return pgs;
	}
}
