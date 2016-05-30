/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.deuteriumlabs.dendrite.dependencies.Store;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Projection;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;

public class StoryPage extends Model {
    private static final char ANCESTRY_DELIMITER = '>';
    private static final String ANCESTRY_PROPERTY = "ancestry";
    private static final String AUTHOR_ID_PROPERTY = "authorId";
    private static final String AUTHOR_NAME_PROPERTY = "authorName";
    private static final String BEGINNING_NUMBER_PROPERTY = "beginningNumber";
    private static final String BEGINNING_VERSION_PROPERTY = "beginningVersion";
    private static final String ELLIPSIS = "…";
    private static final String FORMERLY_LOVING_USERS_PROPERTY = "formerlyLovingUsers";
    private static final String ID_NUMBER_PROPERTY = "idNumber";
    private static final String ID_VERSION_PROPERTY = "idVersion";
    private static final String IS_FIRST_PG_PROPERTY = "isFirstPg";
    private static final String KIND_NAME = "StoryPage";
    private static final int LEN_ALPHABET = 26;
    private static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyz"
            .toCharArray();
    private static final int LOVE_INFLUENCE = 1;
    private static final String LOVING_USERS_PROPERTY = "lovingUsers";
    private static final int MAX_SUMMARY_LEN = 30;
    private static final int SIZE_INFLUENCE = 1;
    private static final String TAGS_PROPERTY = "tags";
    private static final String TEXT_PROPERTY = "text";

    // TODO(Matt Heard): Extract into service object
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

    // TODO(Matt Heard): Move into Author object
    public static int countAllPagesWrittenBy(final String authorId) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final Filter filter = getAuthorIdFilter(authorId);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    // TODO(Matt Heard): Extract into service object
    // TODO(Matt Heard): Investigate using Datastore statistics value instead
    public static int countAllPgs() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    // TODO(Matt Heard): Extract into service object
    public static int countFirstPgsMatchingTag(final String tag) {
        final PreparedQuery preparedQuery = getPreparedQueryForFirstPgsMatchingTag(
                tag);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    // TODO(Matt Heard): Clarify names of 'greater' and 'less' variables.
    public static int countLoversBetween(final String greater,
            final String less) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final Class<String> type = String.class;
        final String projectionProperty = LOVING_USERS_PROPERTY;
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
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        int count = 0;
        for (final DatastoreEntity entity : entities) {
            final String loverId;
            loverId = (String) entity.getProperty(projectionProperty);
            if ((loverId != null) && (loverId.equals("") == false)) {
                count++;
            }
        }
        return count;
    }

    public static int countVersions(final int num) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final Filter filter = getIdNumFilter(num);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    public static List<StoryPage> getAllVersions(final PageId pageId) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final int num = pageId.getNumber();
        final Filter filter = StoryPage.getIdNumFilter(num);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        final List<StoryPage> pages = getPgsFromEntities(entities);
        return pages;
    }

    public static List<StoryPage> getFirstPgsMatchingTag(final String tag) {
        final PreparedQuery preparedQuery = getPreparedQueryForFirstPgsMatchingTag(
                tag);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        final List<StoryPage> pgs = new ArrayList<StoryPage>();
        for (final DatastoreEntity entity : entities) {
            final StoryPage pg = new StoryPage(entity);
            pgs.add(pg);
        }
        return pgs;
    }

    public static List<StoryPage> getPagesWrittenBy(final String authorId,
            final int start, final int end) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        query.addSort(BEGINNING_NUMBER_PROPERTY);
        query.addSort(ID_NUMBER_PROPERTY);
        query.addSort(ID_VERSION_PROPERTY);
        final Filter filter = getAuthorIdFilter(authorId);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final int limit = end - start;
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
        final int offset = start;
        fetchOptions.offset(offset);
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        final List<StoryPage> pgs = getPgsFromEntities(entities);
        return pgs;
    }

    public static StoryPage getParentOf(final PageId childId) {
        childId.setVersion("a");
        final StoryPage child = new StoryPage();
        child.setId(childId);
        child.read();
        return child.getParent();
    }

    public static String getRandomVersion(final PageId id,
            final Random generator) {
        id.setVersion("a");
        final StoryPage pg = new StoryPage();
        pg.setId(id);
        pg.read();
        if (pg.isInStore() == true) {
            final long denominator = pg.calculateChanceDenominator();
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

    private static char convertNumToLetter(final int num) {
        final Map<Integer, Character> map = new HashMap<Integer, Character>();
        for (int i = 0; i < LETTERS.length; i++) {
            map.put(i + 1, LETTERS[i]);
        }
        return map.get(num);
    }

    private static int countLoversOfAllVersions(final int pgNum) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        Projection loversProjection;
        final String propertyName = LOVING_USERS_PROPERTY;
        loversProjection = new PropertyProjection(propertyName, String.class);
        query.addProjection(loversProjection);
        FilterPredicate filter;
        filter = FilterOperator.EQUAL.of(ID_NUMBER_PROPERTY, pgNum);
        query.setFilter(filter);
        final Store store = new Store();
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final PreparedQuery preparedQuery = store.prepare(query);
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        int count = 0;
        for (final DatastoreEntity entity : entities) {
            if (entity.getProperty(LOVING_USERS_PROPERTY) != null) {
                count++;
            }
        }
        return count;
    }

    private static Filter getAuthorIdFilter(final String authorId) {
        final String propertyName = AUTHOR_ID_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final String value = authorId;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static Filter getIdFilter(final PageId id) {
        final int num = id.getNumber();
        final Filter numFilter = getIdNumFilter(num);
        final String version = id.getVersion();
        final Filter versionFilter = getIdVersionFilter(version);
        return CompositeFilterOperator.and(numFilter, versionFilter);
    }

    private static Filter getIdNumFilter(final int num) {
        final String propertyName = ID_NUMBER_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final int value = num;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static int getIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(ID_NUMBER_PROPERTY);
        return num.intValue();
    }

    private static Filter getIdVersionFilter(final String version) {
        final String propertyName = ID_VERSION_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final String value = version;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static String getIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(ID_VERSION_PROPERTY);
    }

    private static List<StoryPage> getPgsFromEntities(
            final List<DatastoreEntity> entities) {
        final List<StoryPage> pgs = new ArrayList<StoryPage>();
        for (final DatastoreEntity entity : entities) {
            final StoryPage pg = new StoryPage(entity);
            pgs.add(pg);
        }
        return pgs;
    }

    private static PreparedQuery getPreparedQueryForFirstPgsMatchingTag(
            final String tag) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        String propertyName = IS_FIRST_PG_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
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
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        return preparedQuery;
    }

    static int countSubtreeBetween(final String greater, final String less) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final String propertyName = ANCESTRY_PROPERTY;
        FilterOperator operator = FilterOperator.GREATER_THAN;

        // TODO(Matt Heard) Clarify names of 'greater' and 'less' variables.
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
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    private List<PageId> ancestry;
    private String authorId;
    private String authorName;
    private PageId beginning;
    private String chance;
    private List<String> formerlyLovingUsers;
    private PageId id;
    private boolean isFirstPg;
    private List<String> lovingUsers;
    private StoryPage parent;
    private List<String> tags;
    private Text text;

    public StoryPage() {
        setBeginning(null);
    }

    public StoryPage(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    public boolean addTag(final String tag) {
        final List<String> tags = getTags();
        final boolean isDuplicate = tags.contains(tag);
        if (isDuplicate == false) {
            tags.add(tag);
            setTags(tags);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void create() {
        generateAncestry();
        determineWhetherFirstPg();
        super.create();
    }

    public List<PageId> getAncestry() {
        return ancestry;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public PageId getBeginning() {
        return beginning;
    }

    public String getChance() {
        if (chance == null) {
            calculateChance();
        }
        return chance;
    }

    public List<String> getFormerlyLovingUsers() {
        return formerlyLovingUsers;
    }

    public PageId getId() {
        return id;
    }

    public String getLongSummary() {
        if (text != null) {
            final String full = text.getValue();
            return summarise(full, 100);
        } else {
            return "This page has not been written yet.";
        }
    }

    public List<String> getLovingUsers() {
        return lovingUsers;
    }

    public int getNumLovingUsers() {
        return lovingUsers.size();
    }

    public StoryPage getParent() {
        return parent;
    }

    public String getSummary() {
        return summarise(text.getValue(), MAX_SUMMARY_LEN);
    }

    public List<String> getTags() {
        if (tags == null) {
            initTags();
        }
        return tags;
    }

    public Text getText() {
        return text;
    }

    public boolean isFirstPg() {
        return isFirstPg;
    }

    public boolean isLovedBy(final String userId) {
        return lovingUsers.contains(userId);
    }

    public boolean removeTag(final String tag) {
        final List<String> tags = getTags();
        boolean isUpdateNeeded = false;
        while (tags.contains(tag)) {
            tags.remove(tag);
            isUpdateNeeded = true;
        }
        if (isUpdateNeeded) {
            setTags(tags);
        }
        return isUpdateNeeded;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    public void setBeginning(final PageId beginning) {
        this.beginning = beginning;
    }

    public void setFormerlyLovingUsers(final List<String> users) {
        formerlyLovingUsers = users;
    }

    public void setId(final PageId id) {
        this.id = id;
        if (beginning == null) {
            setBeginning(id);
        }
    }

    public void setLovingUsers(final List<String> lovingUsers) {
        this.lovingUsers = lovingUsers;
    }

    public void setParent(final StoryPage parent) {
        this.parent = parent;
    }

    public void setText(final String string) {
        text = new Text(string);
    }

    public void setText(final Text text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{ " + id + ": " + getText().toString() + " }";
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
        final long numerator = calculateChanceNumerator();
        final long denominator = calculateChanceDenominator();
        setChance(numerator + "/" + denominator);
    }

    private long calculateChanceDenominator() {
        final int sizeDenominator = getSizeDenominator();
        final int sizeInfluence = getSizeInfluence();
        final int loveDenominator = getLoveDenominator();
        final int loveInfluence = getLoveInfluence();
        return (sizeDenominator * sizeInfluence)
                + (loveDenominator * loveInfluence);
    }

    private int calculateChanceNumerator() {
        final int sizeNumerator = getSizeNumerator();
        final int sizeInfluence = getSizeInfluence();
        final int loveNumerator = getLoveNumerator();
        final int loveInfluence = getLoveInfluence();
        return (sizeNumerator * sizeInfluence)
                + (loveNumerator + loveInfluence);
    }

    private void determineWhetherFirstPg() {
        final boolean isFirstPg = (beginning.getNumber() == id.getNumber());
        setFirstPg(isFirstPg);
    }

    private void generateAncestry() {
        final List<PageId> ancestry;
        if (parent != null) {
            parent.read();
            ancestry = parent.getAncestry();
        } else {
            ancestry = new ArrayList<PageId>();
        }
        ancestry.add(id);
        setAncestry(ancestry);
    }

    private List<PageId> getAncestryFromEntity(final DatastoreEntity entity) {
        final String ancestry = (String) entity.getProperty(ANCESTRY_PROPERTY);
        return parseAncestry(ancestry);
    }

    private String getAuthorIdFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(AUTHOR_ID_PROPERTY);
    }

    private String getAuthorNameFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(AUTHOR_NAME_PROPERTY);
    }

    private int getBeginningNumFromEntity(final DatastoreEntity entity) {
        final String property = BEGINNING_NUMBER_PROPERTY;
        final Long num = (Long) entity.getProperty(property);
        return (num == null) ? 0 : num.intValue();
    }

    private String getBeginningVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(BEGINNING_VERSION_PROPERTY);
    }

    @SuppressWarnings("unchecked")
    private List<String> getFormerlyLovingUsersFromEntity(
            final DatastoreEntity entity) {
        return (List<String>) entity
                .getProperty(FORMERLY_LOVING_USERS_PROPERTY);
    }

    private boolean getIsFirstPgFromEntity(final DatastoreEntity entity) {
        final Boolean property = (Boolean) entity
                .getProperty(IS_FIRST_PG_PROPERTY);
        return (property == null) ? false : property.booleanValue();
    }

    private Key getKey() {
        return KeyFactory.createKey(KIND_NAME, id.toString());
    }

    private int getLoveDenominator() {
        return getNumLoversOfAllVersions();
    }

    private int getLoveInfluence() {
        return LOVE_INFLUENCE;
    }

    private int getLoveNumerator() {
        return getNumLovingUsers();
    }

    @SuppressWarnings("unchecked")
    private List<String> getLovingUsersFromEntity(
            final DatastoreEntity entity) {
        return (List<String>) entity.getProperty(LOVING_USERS_PROPERTY);
    }

    private int getNumLoversOfAllVersions() {
        final int pgNum = id.getNumber();
        return StoryPage.countLoversOfAllVersions(pgNum);
    }

    private int getSizeDenominator() {
        if (isTheFirstPage()) {
            /* For example, the first page of the story is 1a. Find the number
             * of nodes in all subtrees of all versions of 1a. So, if there are
             * two versions, 1a and 1b, I need to find the size of both subtrees
             * and add 1 to each size and then add them together. This is O(n)
             * where n is the number of versions. Is there an O(1) solution?
             * We're looking for all nodes with subtrees which match the
             * following regex: "1[a-z]+.*". From this, we know that the string
             * must be greater than "1\`" and less than "1\{". */
            return getSizeOfBeginningSubtree();
        } else {
            return getSizeOfSiblingSubtrees();
        }
    }

    private int getSizeInfluence() {
        return SIZE_INFLUENCE;
    }

    private int getSizeNumerator() {
        return getSizeOfSubtree() + 1;
    }

    private int getSizeOfBeginningSubtree() {
        final int num = id.getNumber();
        final String greaterThanOrEqual = num + "`";
        final String lessThan = num + "{";
        return StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
    }

    private int getSizeOfSiblingSubtrees() {
        String subtreeAncestry = "";
        for (int i = 0; i < (ancestry.size() - 1); i++) {
            subtreeAncestry += ancestry.get(i);
            subtreeAncestry += String.valueOf(ANCESTRY_DELIMITER);
        }
        subtreeAncestry += id.getNumber();
        final String greaterThanOrEqual = subtreeAncestry + "`";
        final String lessThan = subtreeAncestry + "{";
        return StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
    }

    private int getSizeOfSubtree() {
        String subtreeAncestry = "";
        for (int i = 0; i < ancestry.size(); i++) {
            subtreeAncestry += ancestry.get(i);
            if (i < (ancestry.size() - 1)) {
                subtreeAncestry += String.valueOf(ANCESTRY_DELIMITER);
            }
        }
        final String greater = subtreeAncestry + ANCESTRY_DELIMITER;
        final char nextChar = ANCESTRY_DELIMITER + 1;
        final String less = subtreeAncestry + nextChar;
        return StoryPage.countSubtreeBetween(greater, less);
    }

    @SuppressWarnings("unchecked")
    private List<String> getTagsFromEntity(final DatastoreEntity entity) {
        return (List<String>) entity.getProperty(TAGS_PROPERTY);
    }

    private Text getTextFromEntity(final DatastoreEntity entity) {
        return (Text) entity.getProperty(TEXT_PROPERTY);
    }

    private void incrementVersion() {
        id.incrementVersion();
        read();
    }

    private void initTags() {
        final List<String> tags = new ArrayList<String>();
        setTags(tags);
    }

    /**
     * @return
     */
    private boolean isTheFirstPage() {
        return id.getNumber() == beginning.getNumber();
    }

    private List<PageId> parseAncestry(final String str) {
        final List<PageId> ancestry = new ArrayList<PageId>();
        if ((str != null) && (str.equals("") == false)) {
            final String regex = String.valueOf(ANCESTRY_DELIMITER);
            final String[] words = str.split(regex);
            for (final String word : words) {
                ancestry.add(new PageId(word));
            }
        }
        return ancestry;
    }

    private void readAncestryFromEntity(final DatastoreEntity entity) {
        final List<PageId> ancestry = getAncestryFromEntity(entity);
        setAncestry(ancestry);
        setParentFromAncestry(ancestry);
    }

    private void readAuthorIdFromEntity(final DatastoreEntity entity) {
        setAuthorId(getAuthorIdFromEntity(entity));
    }

    private void readAuthorNameFromEntity(final DatastoreEntity entity) {
        setAuthorName(getAuthorNameFromEntity(entity));
    }

    private void readBeginningFromEntity(final DatastoreEntity entity) {
        final PageId beginning = new PageId();
        final int num = getBeginningNumFromEntity(entity);
        beginning.setNumber(num);
        final String version = getBeginningVersionFromEntity(entity);
        beginning.setVersion(version);
        final boolean isValid = beginning.isValid();
        if (isValid) {
            setBeginning(beginning);
        } else {
            setBeginning(null);
        }
    }

    private void readFormerlyLovingUsersFromEntity(
            final DatastoreEntity entity) {
        List<String> users = getFormerlyLovingUsersFromEntity(entity);
        if (users == null) {
            users = new ArrayList<String>();
        }
        setFormerlyLovingUsers(users);
    }

    private void readIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int num = getIdNumFromEntity(entity);
        id.setNumber(num);
        final String version = getIdVersionFromEntity(entity);
        id.setVersion(version);
        setId(id);
    }

    private void readIsFirstPgFromEntity(final DatastoreEntity entity) {
        final boolean isFirstPg = getIsFirstPgFromEntity(entity);
        setFirstPg(isFirstPg);
    }

    private void readLovingUsersFromEntity(final DatastoreEntity entity) {
        List<String> users = getLovingUsersFromEntity(entity);
        if (users == null) {
            users = new ArrayList<String>();
        }
        setLovingUsers(users);
    }

    private void readTagsFromEntity(final DatastoreEntity entity) {
        List<String> tags = getTagsFromEntity(entity);
        if (tags == null) {
            tags = new ArrayList<String>();
        }
        setTags(tags);
    }

    private void readTextFromEntity(final DatastoreEntity entity) {
        final Text text = getTextFromEntity(entity);
        this.setText(text);
    }

    private void setAncestry(final List<PageId> ancestry) {
        this.ancestry = ancestry;
    }

    private void setAncestryInEntity(final DatastoreEntity entity) {
        final List<PageId> ancestry = getAncestry();
        String entityVal = "";
        for (int i = 0; i < ancestry.size(); i++) {
            entityVal += ancestry.get(i);
            if (i < (ancestry.size() - 1)) {
                entityVal += ANCESTRY_DELIMITER;
            }
        }
        entity.setProperty(ANCESTRY_PROPERTY, entityVal);
    }

    private void setAuthorIdInEntity(final DatastoreEntity entity) {
        final String authorId = getAuthorId();
        if (authorId != null) {
            entity.setProperty(AUTHOR_ID_PROPERTY, authorId);
        }
    }

    private void setAuthorNameInEntity(final DatastoreEntity entity) {
        final String authorName = getAuthorName();
        if (authorName != null) {
            entity.setProperty(AUTHOR_NAME_PROPERTY, authorName);
        }
    }

    private void setBeginningInEntity(final DatastoreEntity entity) {
        final PageId beginning = getBeginning();
        final int num = beginning.getNumber();
        entity.setProperty(BEGINNING_NUMBER_PROPERTY, num);
        final String version = beginning.getVersion();
        entity.setProperty(BEGINNING_VERSION_PROPERTY, version);
    }

    private void setChance(final String chance) {
        this.chance = chance;
    }

    private void setFirstPg(final boolean isFirstPg) {
        this.isFirstPg = isFirstPg;
    }

    private void setFormerlyLovingUsersInEntity(final DatastoreEntity entity) {
        final List<String> users = getFormerlyLovingUsers();
        entity.setProperty(FORMERLY_LOVING_USERS_PROPERTY, users);
    }

    private void setIdInEntity(final DatastoreEntity entity) {
        final PageId id = getId();
        final int num = id.getNumber();
        entity.setProperty(ID_NUMBER_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(ID_VERSION_PROPERTY, version);
    }

    private void setIsFirstPgInEntity(final DatastoreEntity entity) {
        final boolean isFirstPg = isFirstPg();
        entity.setProperty(IS_FIRST_PG_PROPERTY, isFirstPg);
    }

    private void setLovingUsersInEntity(final DatastoreEntity entity) {
        final List<String> lovingUsers = getLovingUsers();
        entity.setProperty(LOVING_USERS_PROPERTY, lovingUsers);
    }

    private void setParentFromAncestry(final List<PageId> ancestry) {
        if (ancestry.size() > 1) {
            final int parentIndex = ancestry.size() - 2;
            final PageId parentId = ancestry.get(parentIndex);
            final StoryPage parent = new StoryPage();
            parent.setId(parentId);
            parent.read();
            setParent(parent);
        } else {
            setParent(null);
        }
    }

    private void setTags(final List<String> tags) {
        this.tags = tags;
    }

    private void setTagsInEntity(final DatastoreEntity entity) {
        final List<String> tags = getTags();
        entity.setProperty(TAGS_PROPERTY, tags);
    }

    private void setTextInEntity(final DatastoreEntity entity) {
        final Text text = getText();
        entity.setProperty(TEXT_PROPERTY, text);
    }

    private String summarise(final String full, final int len) {
        final int fullSize = full.length();
        if (fullSize < (len - 1)) {
            return full;
        } else {
            final String cropped = full.substring(0, (len - 1));
            return cropped + ELLIPSIS;
        }
    }

    @Override
    protected DatastoreEntity createNewEntity(final String kindName) {
        final String key = getId().toString();
        return new DatastoreEntity(KIND_NAME, key);
    }

    @Override
    protected DatastoreEntity getMatchingEntity()
            throws EntityNotFoundException {
        final Key key = getKey();
        return new DatastoreEntity(new Store().get().get(key));
    }

    @Override
    String getKindName() {
        return KIND_NAME;
    }

    @Override
    DatastoreQuery getMatchingQuery() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final PageId id = getId();
        final Filter filter = getIdFilter(id);
        query.setFilter(filter);
        return query;
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        readAncestryFromEntity(entity);
        readIdFromEntity(entity);
        readTextFromEntity(entity);
        readBeginningFromEntity(entity);
        readAuthorNameFromEntity(entity);
        readAuthorIdFromEntity(entity);
        readLovingUsersFromEntity(entity);
        readFormerlyLovingUsersFromEntity(entity);
        readTagsFromEntity(entity);
        readIsFirstPgFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        setAncestryInEntity(entity);
        setIdInEntity(entity);
        setTextInEntity(entity);
        setBeginningInEntity(entity);
        setAuthorNameInEntity(entity);
        setAuthorIdInEntity(entity);
        setLovingUsersInEntity(entity);
        setFormerlyLovingUsersInEntity(entity);
        setTagsInEntity(entity);
        setIsFirstPgInEntity(entity);
    }
}