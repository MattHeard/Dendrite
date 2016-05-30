/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.deuteriumlabs.dendrite.dependencies.Store;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Represents the beginning of a story. <code>StoryBeginning</code> instances
 * provide an easy way to find the first page in a story and to provide storage
 * for information shared across all pages in a story, such as the story title.
 */
public class StoryBeginning extends Model {
    private static final String KIND_NAME = "StoryBeginning";
    private static final String LOVE_PROPERTY = "love";
    private static final String PAGE_NUMBER_PROPERTY = "pageNumber";
    private static final String QUALITY_PROPERTY = "quality";
    private static final String SIZE_PROPERTY = "size";
    private static final String TITLE_PROPERTY = "title";

    public static int countAllBeginnings() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        query.setKeysOnly();
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    /**
     * Returns a subsection of the list of all beginnings. This is particularly
     * useful for getting paginated lists of beginnings in order of page number.
     * 
     * @param start
     *            The first index for retrieval
     * @param end
     *            The off-the-end index for retrieval
     * @return The list of beginnings between start and end, not including end
     */
    public static List<StoryBeginning> getBeginnings(final int start,
            final int end, final Store store, final DatastoreQuery query) {
        query.addSort(QUALITY_PROPERTY, SortDirection.DESCENDING);
        query.addSort(PAGE_NUMBER_PROPERTY);
        final PreparedQuery preparedQuery = store.prepare(query);
        final int limit = end - start;
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
        final int offset = start;
        fetchOptions.offset(offset);
        final List<DatastoreEntity> entities = DatastoreEntity
                .fromPreparedQuery(preparedQuery, fetchOptions);
        final List<StoryBeginning> beginnings = getBeginningsFromEntities(
                entities);
        return beginnings;
    }

    /**
     * Returns a list of story beginnings extracted from beginning entities.
     * 
     * @param entities
     *            The entities containing the story beginning properties
     * @return The list of story beginnings with extracted properties
     */
    private static List<StoryBeginning> getBeginningsFromEntities(
            final List<DatastoreEntity> entities) {
        final List<StoryBeginning> beginnings = new ArrayList<StoryBeginning>();
        for (final DatastoreEntity entity : entities) {
            final StoryBeginning beginning = new StoryBeginning(entity);
            beginnings.add(beginning);
        }
        return beginnings;
    }

    /**
     * Builds a filter to restrict a query to a particular page number.
     * 
     * @param pageNumber
     *            The page number to filter for
     * @return The filter to apply to the query
     */
    private static Filter getPageNumberFilter(final int pageNumber) {
        final String propertyName = PAGE_NUMBER_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final int value = pageNumber;
        return new FilterPredicate(propertyName, operator, value);
    }

    /**
     * Returns the page number from the given entity.
     * 
     * @param entity
     *            The entity containing the page number
     * @return The page number
     */
    private static int getPageNumberFromEntity(final DatastoreEntity entity) {
        final Long pageNumber = (Long) entity.getProperty(PAGE_NUMBER_PROPERTY);
        return pageNumber.intValue();
    }

    /**
     * Returns the title of the story from the given entity.
     * 
     * @param entity
     *            The entity containing the title
     * @return The title of the story
     */
    private static String getTitleFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(TITLE_PROPERTY);
    }

    private int love;
    private int pageNumber;
    private int quality;
    private int size;

    private String title;

    /**
     * Default constructor. Explicitly defined because of the other constructor.
     */
    public StoryBeginning() {
    }

    /**
     * Builds a story beginning from the properties extracted from the entity.
     * 
     * @param entity
     *            The entity containing the properties
     */
    public StoryBeginning(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    /**
     * Returns the page number of the first page in this story.
     * 
     * @return the page number of the first page in this story
     */
    public int getPageNumber() {
        return pageNumber;
    }

    public int getQuality() {
        if (quality == 0) {
            recalculateQuality();
        }
        return quality;
    }

    public int getSize() {
        if (size == 0) {
            recalculateSize();
        }
        return size;
    }

    /**
     * Returns the title of this story.
     * 
     * @return the title of this story
     */
    public String getTitle() {
        return title;
    }

    public void recalculateQuality() {
        recalculateSize();
        recalculateLove();
        final int size = getSize();
        final int love = getLove();
        final int quality = size + love;
        setQuality(quality);
    }

    /**
     * Sets the page number of the first page in this story.
     * 
     * @param pageNumber
     *            the page number of the first page in this story
     */
    public void setPageNumber(final int pageNumber) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber;
        } else {
            this.pageNumber = 0;
        }
    }

    /**
     * Sets the title of this story.
     * 
     * @param title
     *            The title of this story
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    private int getLove() {
        if (love == -1) {
            recalculateLove();
        }
        return love;
    }

    private int getLoveFromEntity(final DatastoreEntity entity) {
        final Long love = (Long) entity.getProperty(LOVE_PROPERTY);
        if (love != null) {
            return love.intValue();
        } else {
            return -1;
        }
    }

    private int getQualityFromEntity(final DatastoreEntity entity) {
        final Long quality = (Long) entity.getProperty(QUALITY_PROPERTY);
        if (quality != null) {
            return quality.intValue();
        } else {
            return 0;
        }
    }

    private int getSizeFromEntity(final DatastoreEntity entity) {
        final Long size = (Long) entity.getProperty(SIZE_PROPERTY);
        if (size != null) {
            return size.intValue();
        } else {
            return 0;
        }
    }

    private void readLoveFromEntity(final DatastoreEntity entity) {
        final int love = getLoveFromEntity(entity);
        setLove(love);
    }

    /**
     * Reads the value from the entity corresponding to the page number of the
     * first page in this story.
     * 
     * @param entity
     *            The entity storing the page number
     */
    private void readPageNumberFromEntity(final DatastoreEntity entity) {
        final int pageNumber = getPageNumberFromEntity(entity);
        setPageNumber(pageNumber);
    }

    private void readQualityFromEntity(final DatastoreEntity entity) {
        final int quality = getQualityFromEntity(entity);
        setQuality(quality);
    }

    private void readSizeFromEntity(final DatastoreEntity entity) {
        final int size = getSizeFromEntity(entity);
        setSize(size);
    }

    /**
     * Reads the value from the entity corresponding to the title of this story.
     * 
     * @param entity
     *            The entity storing the title
     */
    private void readTitleFromEntity(final DatastoreEntity entity) {
        final String title = getTitleFromEntity(entity);
        if (title == null) {
            setTitle("???");
        } else {
            setTitle(title);
        }
    }

    private void recalculateLove() {
        final int num = getPageNumber();
        final String greaterThanOrEqual = num + "`";
        final String lessThan = num + "{";
        final int love;
        love = StoryPage.countLoversBetween(greaterThanOrEqual, lessThan);
        setLove(love);
    }

    private void recalculateSize() {
        final int num = getPageNumber();
        final String greaterThanOrEqual = num + "`";
        final String lessThan = num + "{";
        final int size;
        size = StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
        setSize(size);
    }

    private void setLove(final int love) {
        this.love = love;
    }

    private void setLoveInEntity(final DatastoreEntity entity) {
        final int love = getLove();
        entity.setProperty(LOVE_PROPERTY, love);
    }

    /**
     * Sets the value in the entity corresponding to the page number of the
     * first page in this story.
     * 
     * @param entity
     *            The entity in which the value is to be stored
     */
    private void setPageNumberInEntity(final DatastoreEntity entity) {
        final int pageNumber = getPageNumber();
        entity.setProperty(PAGE_NUMBER_PROPERTY, pageNumber);
    }

    private void setQuality(final int quality) {
        this.quality = quality;
    }

    private void setQualityInEntity(final DatastoreEntity entity) {
        final int quality = getQuality();
        entity.setProperty(QUALITY_PROPERTY, quality);
    }

    private void setSize(final int size) {
        this.size = size;
    }

    private void setSizeInEntity(final DatastoreEntity entity) {
        final int size = getSize();
        entity.setProperty(SIZE_PROPERTY, size);
    }

    /**
     * Sets the value in the entity corresponding to the title of this story.
     * 
     * @param entity
     *            The entity in which the value is to be stored
     */
    private void setTitleInEntity(final DatastoreEntity entity) {
        final String title = getTitle();
        entity.setProperty(TITLE_PROPERTY, title);
    }

    /* (non-Javadoc)
     * 
     * @see com.deuteriumlabs.dendrite.model.Model#getKindName() */
    @Override
    String getKindName() {
        return KIND_NAME;
    }

    /* (non-Javadoc)
     * 
     * @see com.deuteriumlabs.dendrite.model.Model#getMatchingQuery() */
    @Override
    DatastoreQuery getMatchingQuery() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final int pageNumber = getPageNumber();
        final Filter pageNumberFilter = getPageNumberFilter(pageNumber);
        query.setFilter(pageNumberFilter);
        return query;
    }

    /* (non-Javadoc)
     * 
     * @see com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.
     * google .appengine.api.datastore.Entity) */
    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        readPageNumberFromEntity(entity);
        readTitleFromEntity(entity);
        readSizeFromEntity(entity);
        readLoveFromEntity(entity);
        readQualityFromEntity(entity);
    }

    /* (non-Javadoc)
     * 
     * @see
     * com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google
     * .appengine.api.datastore.Entity) */
    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        setPageNumberInEntity(entity);
        setTitleInEntity(entity);
        setSizeInEntity(entity);
        setLoveInEntity(entity);
        setQualityInEntity(entity);
    }
}
