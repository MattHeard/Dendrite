/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
	private static final String PAGE_NUMBER_PROPERTY = "pageNumber";
	private static final String TITLE_PROPERTY = "title";
	private static final String SIZE_PROPERTY = "size";
	private static final String QUALITY_PROPERTY = "quality";
	private static final String LOVE_PROPERTY = "love";

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
			final int end) {
		final Query query = new Query(KIND_NAME);
		query.addSort(QUALITY_PROPERTY, SortDirection.DESCENDING);
		query.addSort(PAGE_NUMBER_PROPERTY);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final int limit = end - start;
		final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
		final int offset = start;
		fetchOptions.offset(offset);
		List<Entity> entities = preparedQuery.asList(fetchOptions);
		List<StoryBeginning> beginnings = getBeginningsFromEntities(entities);
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
			final List<Entity> entities) {
		final List<StoryBeginning> beginnings = new ArrayList<StoryBeginning>();
		for (final Entity entity : entities) {
			StoryBeginning beginning = new StoryBeginning(entity);
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
	private static int getPageNumberFromEntity(final Entity entity) {
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
	private static String getTitleFromEntity(final Entity entity) {
		return (String) entity.getProperty(TITLE_PROPERTY);
	}

	private int pageNumber;
	private String title;
	private int size;
	private int love;
	private int quality;

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
	public StoryBeginning(final Entity entity) {
		this.readPropertiesFromEntity(entity);
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
		final int pageNumber = this.getPageNumber();
		final Filter pageNumberFilter = getPageNumberFilter(pageNumber);
		return query.setFilter(pageNumberFilter);
	}

	/**
	 * Returns the page number of the first page in this story.
	 * 
	 * @return the page number of the first page in this story
	 */
	public int getPageNumber() {
		return this.pageNumber;
	}

	/**
	 * Returns the title of this story.
	 * 
	 * @return the title of this story
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Reads the value from the entity corresponding to the page number of the
	 * first page in this story.
	 * 
	 * @param entity
	 *            The entity storing the page number
	 */
	private void readPageNumberFromEntity(final Entity entity) {
		final int pageNumber = getPageNumberFromEntity(entity);
		this.setPageNumber(pageNumber);
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
		this.readPageNumberFromEntity(entity);
		this.readTitleFromEntity(entity);
		this.readSizeFromEntity(entity);
		this.readLoveFromEntity(entity);
		this.readQualityFromEntity(entity);
	}

	private void readQualityFromEntity(final Entity entity) {
		final int quality = getQualityFromEntity(entity);
		this.setQuality(quality);
	}

	private int getQualityFromEntity(final Entity entity) {
		final Long quality = (Long) entity.getProperty(QUALITY_PROPERTY);
		if (quality != null) {
			return quality.intValue();
		} else {
			return 0;
		}
	}

	private void readLoveFromEntity(final Entity entity) {
		final int love = getLoveFromEntity(entity);
		this.setLove(love);
	}

	private int getLoveFromEntity(final Entity entity) {
		final Long love = (Long) entity.getProperty(LOVE_PROPERTY);
		if (love != null) {
			return love.intValue();
		} else {
			return -1;
		}
	}

	private void readSizeFromEntity(final Entity entity) {
		final int size = getSizeFromEntity(entity);
		this.setSize(size);
	}

	private int getSizeFromEntity(final Entity entity) {
		final Long size = (Long) entity.getProperty(SIZE_PROPERTY);
		if (size != null) {
			return size.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * Reads the value from the entity corresponding to the title of this story.
	 * 
	 * @param entity
	 *            The entity storing the title
	 */
	private void readTitleFromEntity(final Entity entity) {
		final String title = getTitleFromEntity(entity);
		if (title == null) {
			this.setTitle("???");
		} else {
			this.setTitle(title);
		}
	}

	/**
	 * Sets the page number of the first page in this story.
	 * 
	 * @param pageNumber
	 *            the page number of the first page in this story
	 */
	public void setPageNumber(final int pageNumber) {
		if (pageNumber > 0)
			this.pageNumber = pageNumber;
		else
			this.pageNumber = 0;
	}

	/**
	 * Sets the value in the entity corresponding to the page number of the
	 * first page in this story.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setPageNumberInEntity(final Entity entity) {
		final int pageNumber = this.getPageNumber();
		entity.setProperty(PAGE_NUMBER_PROPERTY, pageNumber);
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
		this.setPageNumberInEntity(entity);
		this.setTitleInEntity(entity);
		this.setSizeInEntity(entity);
		this.setLoveInEntity(entity);
		this.setQualityInEntity(entity);
	}

	private void setSizeInEntity(final Entity entity) {
		final int size = this.getSize();
		entity.setProperty(SIZE_PROPERTY, size);
	}

	private void setLoveInEntity(final Entity entity) {
		final int love = this.getLove();
		entity.setProperty(LOVE_PROPERTY, love);
	}

	private void setQualityInEntity(final Entity entity) {
		final int quality = this.getQuality();
		entity.setProperty(QUALITY_PROPERTY, quality);
	}

	public int getQuality() {
		if (this.quality == 0) {
			this.recalculateQuality();
		}
		return this.quality;
	}

	public int getSize() {
		if (this.size == 0) {
			this.recalculateSize();
		}
		return this.size;
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

	/**
	 * Sets the value in the entity corresponding to the title of this story.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setTitleInEntity(final Entity entity) {
		final String title = this.getTitle();
		entity.setProperty(TITLE_PROPERTY, title);
	}

	public static int countAllBeginnings() {
		final Query query = new Query(KIND_NAME).setKeysOnly();
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}

	public void recalculateQuality() {
		this.recalculateSize();
		this.recalculateLove();
		final int size = this.getSize();
		final int love = this.getLove();
		final int quality = size + love;
		this.setQuality(quality);
	}

	private void setQuality(final int quality) {
		this.quality = quality;
	}

	private int getLove() {
		if (this.love == -1) {
			this.recalculateLove();
		}
		return this.love;
	}

	private void recalculateLove() {
		final int num = this.getPageNumber();
		final String greaterThanOrEqual = num + "`";
		final String lessThan = num + "{";
		final int love;
		love = StoryPage.countLoversBetween(greaterThanOrEqual, lessThan);
		this.setLove(love);
	}

	private void setLove(final int love) {
		this.love = love;
	}

	private void recalculateSize() {
		final int num = this.getPageNumber();
		final String greaterThanOrEqual = num + "`";
		final String lessThan = num + "{";
		final int size;
		size = StoryPage.countSubtreeBetween(greaterThanOrEqual, lessThan);
		this.setSize(size);
	}

	private void setSize(final int size) {
		this.size = size;
	}
}
