package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Represents a page of a story. <code>StoryPage</code> instances act as nodes
 * in a tree to form a complete story. The unordered nature of the page IDs of
 * the pages in a story causes the branches of different stories to be
 * interwoven.
 */
public class StoryPage extends Model {
	private static final String ID_NUMBER_PROPERTY = "idNumber";
	private static final String ID_VERSION_PROPERTY = "idVersion";
	private static final String KIND_NAME = "StoryPage";
	private static final String TEXT_PROPERTY = "text";
	
	/**
	 * Builds a filter to restrict a query to a particular ID.
	 * @param id The page ID to filter for
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
	 * @param number The page number to filter for
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
	 * @param entity The entity containing the ID
	 * @return The number component of the story page ID
	 */
	private static int getIdNumberFromEntity(final Entity entity) {
		final Long number = (Long) entity.getProperty(ID_NUMBER_PROPERTY);
		return number.intValue();
	}

	/**
	 * Builds a filter to restrict a query to a particular ID version. This will
	 * probably not be very useful without other query filters.
	 * @param version The page version to filter for
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
	 * @param entity The entity containing the ID
	 * @return The version component of the story page ID
	 */
	private static String getIdVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(ID_VERSION_PROPERTY);
	}

	private PageId id;
	private String text;

	/**
	 * Returns the ID of this story page.
	 * @return The ID of this story page
	 */
	public PageId getId() {
		return id;
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
		final PageId id = this.getId();
		final Filter filter = getIdFilter(id);
		return query.setFilter(filter);
	}

	/**
	 * Returns the text of this story page.
	 * @return The text of this story page
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Returns the text of the story page from the given entity.
	 * @param entity The entity containing the text
	 * @return The text of the story page
	 */
	private String getTextFromEntity(final Entity entity) {
		return (String) entity.getProperty(TEXT_PROPERTY);
	}

	/**
	 * Reads the values from the entity corresponding to the ID of this story
	 * page.
	 * @param entity The entity storing the ID
	 */
	private void readIdFromEntity(final Entity entity) {
		final PageId id = new PageId();
		final int number = getIdNumberFromEntity(entity);
		id.setNumber(number);
		final String version = getIdVersionFromEntity(entity);
		id.setVersion(version);
		this.setId(id);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void readPropertiesFromEntity(final Entity entity) {
		this.readIdFromEntity(entity);
		this.readTextFromEntity(entity);
	}

	/**
	 * Reads the value from the entity corresponding to the text of this story
	 * page.
	 * @param entity The entity storing the text
	 */
	private void readTextFromEntity(final Entity entity) {
		final String text = getTextFromEntity(entity);
		this.setText(text);
	}

	/**
	 * Sets the ID of this story page.
	 * @param id The new ID for this story page
	 */
	public void setId(final PageId id) {
		this.id = id;
	}

	/**
	 * Sets the values in the entity corresponding to the ID for this story
	 * page.
	 * @param entity The entity in which the values are to be stored
	 */
	private void setIdInEntity(final Entity entity) {
		final PageId id = this.getId();
		final int number = id.getNumber();
		entity.setProperty(ID_NUMBER_PROPERTY, number);
		final String version = id.getVersion();
		entity.setProperty(ID_VERSION_PROPERTY, version);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void setPropertiesInEntity(final Entity entity) {
		this.setIdInEntity(entity);
		this.setTextInEntity(entity);
	}

	/**
	 * Sets the text of this story page.
	 * @param text The new text for this story page
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Sets the value in the entity corresponding to the text of this story
	 * page.
	 * @param entity The entity in which the value is to be stored
	 */
	private void setTextInEntity(Entity entity) {
		final String text = this.getText();
		entity.setProperty(TEXT_PROPERTY, text);
	}
}
