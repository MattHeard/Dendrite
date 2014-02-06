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

/**
 * Represents the beginning of a story. <code>StoryBeginning</code> instances
 * provide an easy way to find the first page in a story and to provide storage
 * for information shared across all pages in a story, such as the story title.
 */
public class StoryBeginning extends Model {
	private static final String KIND_NAME = "StoryBeginning";
	private static final String PAGE_NUMBER_PROPERTY = "pageNumber";
	private static final String TITLE_PROPERTY = "title";

	/**
	 * Returns a subsection of the list of all beginnings. This is particularly
	 * useful for getting paginated lists of beginnings in order of page number.
	 * @param start The first index for retrieval
	 * @param end The off-the-end index for retrieval
	 * @return The list of beginnings between start and end, not including end
	 */
	public static List<StoryBeginning> getBeginnings(final int start, 
			final int end) {
		final Query query = new Query(KIND_NAME);
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
	 * @param entities The entities containing the story beginning properties
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
	 * @param pageNumber The page number to filter for
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
	 * @param entity The entity containing the page number
	 * @return The page number
	 */
	private static int getPageNumberFromEntity(final Entity entity) {
		final Long pageNumber = (Long) entity.getProperty(PAGE_NUMBER_PROPERTY);
		return pageNumber.intValue();
	}

	/**
	 * Returns the title of the story from the given entity.
	 * @param entity The entity containing the title
	 * @return The title of the story
	 */
	private static String getTitleFromEntity(final Entity entity) {
		return (String) entity.getProperty(TITLE_PROPERTY);
	}

	private int pageNumber;
	private String title;

	/**
	 * Default constructor. Explicitly defined because of the other constructor.
	 */
	public StoryBeginning() { }

	/**
	 * Builds a story beginning from the properties extracted from the entity.
	 * @param entity The entity containing the properties
	 */
	public StoryBeginning(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#getKindName()
	 */
	@Override
	String getKindName() {
		return KIND_NAME;
	}

	/* (non-Javadoc)
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
	 * @return the page number of the first page in this story
	 */
	public int getPageNumber() {
		return this.pageNumber;
	}

	/**
	 * Returns the title of this story.
	 * @return the title of this story
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Reads the value from the entity corresponding to the page number of the
	 * first page in this story.
	 * @param entity The entity storing the page number
	 */
	private void readPageNumberFromEntity(final Entity entity) {
		final int pageNumber = getPageNumberFromEntity(entity);
		this.setPageNumber(pageNumber);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void readPropertiesFromEntity(final Entity entity) {
		this.readPageNumberFromEntity(entity);
		this.readTitleFromEntity(entity);
	}

	/**
	 * Reads the value from the entity corresponding to the title of this story.
	 * @param entity The entity storing the title
	 */
	private void readTitleFromEntity(final Entity entity) {
		final String title = getTitleFromEntity(entity);
		this.setTitle(title);
	}

	/**
	 * Sets the page number of the first page in this story.
	 * @param pageNumber the page number of the first page in this story
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
	 * @param entity The entity in which the value is to be stored
	 */
	private void setPageNumberInEntity(final Entity entity) {
		final int pageNumber = this.getPageNumber();
		entity.setProperty(PAGE_NUMBER_PROPERTY, pageNumber);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void setPropertiesInEntity(final Entity entity) {
		this.setPageNumberInEntity(entity);
		this.setTitleInEntity(entity);
	}

	/**
	 * Sets the title of this story.
	 * @param title The title of this story
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Sets the value in the entity corresponding to the title of this story.
	 * @param entity The entity in which the value is to be stored
	 */
	private void setTitleInEntity(final Entity entity) {
		final String title = this.getTitle();
		entity.setProperty(TITLE_PROPERTY, title);
	}

}
