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
	private static final String AUTHOR_ID_PROPERTY = "authorId";
	private static final String AUTHOR_NAME_PROPERTY = "authorName";
	private static final String BEGINNING_NUMBER_PROPERTY = "beginningNumber";
	private static final String BEGINNING_VERSION_PROPERTY = "beginningVersion";
	private static final String ID_NUMBER_PROPERTY = "idNumber";
	private static final String ID_VERSION_PROPERTY = "idVersion";
	private static final String KIND_NAME = "StoryPage";
	private static final int LEN_ALPHABET = 26;
	private static final char[] LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };
	private static final String TEXT_PROPERTY = "text";

	private static char convertNumberToLetter(int number) {
		Map<Integer, Character> map = new HashMap<Integer, Character>();
		for (int i = 0; i < LETTERS.length; i++)
			map.put(i + 1, LETTERS[i]);
		return map.get(number);
	}

	public static String convertNumberToVersion(final int number) {
		final int lowNumber = ((number - 1) % LEN_ALPHABET) + 1;
		final char lowLetter = convertNumberToLetter(lowNumber);
		final int highNumbers = (number - 1) / LEN_ALPHABET;
		String versionString = Character.toString(lowLetter);
		if (highNumbers > 0) {
			final String highLetters = convertNumberToVersion(highNumbers);
			versionString = highLetters + versionString;
		}
		return versionString;
	}

	public static int countVersions(final int number) {
		final Query query = new Query(KIND_NAME);
		final Filter filter = getIdNumberFilter(number);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
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
		final int number = id.getNumber();
		final Filter numberFilter = getIdNumberFilter(number);
		final String version = id.getVersion();
		final Filter versionFilter = getIdVersionFilter(version);
		return CompositeFilterOperator.and(numberFilter, versionFilter);
	}

	/**
	 * Builds a filter to restrict a query to a particular ID number. Without
	 * also applying a filter to restrict to a particular ID version, this will
	 * provide a collection of versions of one page in a story.
	 * 
	 * @param number
	 *            The page number to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdNumberFilter(final int number) {
		final String propertyName = ID_NUMBER_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = number;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the number component of the story page ID from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the ID
	 * @return The number component of the story page ID
	 */
	private static int getIdNumberFromEntity(final Entity entity) {
		final Long number = (Long) entity.getProperty(ID_NUMBER_PROPERTY);
		return number.intValue();
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

	private static List<StoryPage> getPagesFromEntities(
			final List<Entity> entities) {
		final List<StoryPage> pages = new ArrayList<StoryPage>();
		for (final Entity entity : entities) {
			StoryPage page = new StoryPage(entity);
			pages.add(page);
		}
		return pages;
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
		List<StoryPage> pages = getPagesFromEntities(entities);
		return pages;
	}

	public static String getRandomVersion(PageId id) {
		final int number = id.getNumber();
		final Query query = new Query(KIND_NAME);
		final Filter filter = getIdNumberFilter(number);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final int count = preparedQuery.countEntities(fetchOptions);
		if (count > 1) {
			Random generator = new Random();
			final int randomNumber = generator.nextInt(count) + 1;
			final String version = convertNumberToVersion(randomNumber);
			return version;
		} else
			return "a";
	}

	private String authorId;
	private String authorName;

	private PageId beginning;

	private PageId id;

	private Text text;

	public StoryPage() {
		this.setBeginning(null);
	}

	public StoryPage(final Entity entity) {
		this.readPropertiesFromEntity(entity);
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

	private int getBeginningNumberFromEntity(final Entity entity) {
		final String property = BEGINNING_NUMBER_PROPERTY;
		final Long number = (Long) entity.getProperty(property);
		if (number != null)
			return number.intValue();
		else
			return 0;
	}

	private String getBeginningVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(BEGINNING_VERSION_PROPERTY);
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
		final int number = getBeginningNumberFromEntity(entity);
		beginning.setNumber(number);
		final String version = getBeginningVersionFromEntity(entity);
		beginning.setVersion(version);
		final boolean isValid = beginning.isValid();
		if (isValid)
			this.setBeginning(beginning);
		else
			this.setBeginning(null);
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
		final int number = getIdNumberFromEntity(entity);
		id.setNumber(number);
		final String version = getIdVersionFromEntity(entity);
		id.setVersion(version);
		this.setId(id);
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
		this.readIdFromEntity(entity);
		this.readTextFromEntity(entity);
		this.readBeginningFromEntity(entity);
		this.readAuthorNameFromEntity(entity);
		this.readAuthorIdFromEntity(entity);
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

	public void setAuthorId(final String authorId) {
		this.authorId = authorId;
	}

	private void setAuthorIdInEntity(final Entity entity) {
		final String authorId = this.getAuthorId();
		if (authorId != null)
			entity.setProperty(AUTHOR_ID_PROPERTY, authorId);
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	private void setAuthorNameInEntity(final Entity entity) {
		final String authorName = this.getAuthorName();
		if (authorName != null)
			entity.setProperty(AUTHOR_NAME_PROPERTY, authorName);
	}

	public void setBeginning(PageId beginning) {
		this.beginning = beginning;
	}

	private void setBeginningInEntity(final Entity entity) {
		final PageId beginning = this.getBeginning();
		final int number = beginning.getNumber();
		entity.setProperty(BEGINNING_NUMBER_PROPERTY, number);
		final String version = beginning.getVersion();
		entity.setProperty(BEGINNING_VERSION_PROPERTY, version);
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
		if (beginning == null)
			this.setBeginning(id);
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
		final int number = id.getNumber();
		entity.setProperty(ID_NUMBER_PROPERTY, number);
		final String version = id.getVersion();
		entity.setProperty(ID_VERSION_PROPERTY, version);
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
		this.setIdInEntity(entity);
		this.setTextInEntity(entity);
		this.setBeginningInEntity(entity);
		this.setAuthorNameInEntity(entity);
		this.setAuthorIdInEntity(entity);
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

	public static int countAllPagesWrittenBy(final String authorId) {
		final Query query = new Query(KIND_NAME);
		final Filter filter = getAuthorIdFilter(authorId);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}
}
