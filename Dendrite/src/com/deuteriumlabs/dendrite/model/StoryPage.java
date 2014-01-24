package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 *	Represents a page of a story. <code>StoryPage</code> instances act as nodes
 *	in a tree to form a complete story. The unordered nature of the page IDs of
 *	the pages in a story causes the branches of different stories to be
 *	interwoven.
 */
public class StoryPage extends Model {
	private static final String ID_NUMBER_PROPERTY = "idNumber";
	private static final String ID_VERSION_PROPERTY = "idVersion";
	private static final String KIND_NAME = "StoryPage";
	
	/**
	 * Builds a filter to restrict a query to a particular ID.
	 * @param id the page ID to filter for
	 * @return the filter to apply to the query
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
	 * @param number the page number to filter for
	 * @return the filter to apply to the query
	 */
	private static Filter getIdNumberFilter(final int number) {
		final String propertyName = ID_NUMBER_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = number;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Builds a filter to restrict a query to a particular ID version. This will
	 * probably not be very useful without other query filters.
	 * @param version the page version to filter for
	 * @return the filter to apply to the query
	 */
	private static Filter getIdVersionFilter(final String version) {
		final String propertyName = ID_VERSION_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = version;
		return new FilterPredicate(propertyName, operator, value);
	}

	private PageId id;

	/**
	 * Returns the ID of this story page.
	 * @return the ID of this story page
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
	 * Sets the ID of the story page.
	 * @param id the new id for the story page
	 */
	public void setId(final PageId id) {
		this.id = id;
	}

	/**
	 * Sets the values in the entity corresponding to the ID for the story page.
	 * @param entity The entity in which the values are to be stored
	 */
	private void setIdPropertiesInEntity(final Entity entity) {
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
		this.setIdPropertiesInEntity(entity);
	}
}
