package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Represents an option leading from one story page to another.
 * <code>StoryOption</code> instances act as branches in a tree to form a
 * complete story. Each story page will have between 0 and 5 (inclusive) options
 * and each option can either be dangling or connected.
 * 
 * A dangling option is an option with a defined source page, defined option
 * text, and a defined index in the ordered list of options on the source page,
 * but does not have a defined destination page. When a story page is first
 * written, all of its options are dangling rather than connected.
 * 
 * A connected option is an option with a defined source page, defined option
 * text, a defined list index, and also a defined destination page. When a
 * dangling link is followed, the user is taken to a form to create a new page.
 * When this new page is created, it completes the link that was followed. Other
 * users who follow that same link will then be taken to the newly written page
 * rather than to the page creation form.
 */
public class StoryOption extends Model {
	private static final int INVALID_LIST_INDEX = -1;
	private static final int INVALID_TARGET_PAGE_NUMBER = 0;
	private static final String KIND_NAME = "StoryOption";
	private static final String LIST_INDEX_PROPERTY = "listIndex";
	private static final String SOURCE_NUMBER_PROPERTY = "sourceNumber";
	private static final String SOURCE_VERSION_PROPERTY = "sourceVersion";
	private static final String TARGET_PROPERTY = "target";
	private static final String TEXT_PROPERTY = "text";

	public static int countOptions(final PageId source) {
		final Query query = new Query(KIND_NAME);
		final Filter filter = getSourceFilter(source);
		query.setFilter(filter);
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		return preparedQuery.countEntities(fetchOptions);
	}

	/**
	 * Builds a filter to restrict a query to a particular list index.
	 * 
	 * @param listIndex
	 *            The list index to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getListIndexFilter(final int listIndex) {
		final String propertyName = LIST_INDEX_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = listIndex;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the list index from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the list index
	 * @return The list index
	 */
	private static int getListIndexFromEntity(final Entity entity) {
		final Long listIndex = (Long) entity.getProperty(LIST_INDEX_PROPERTY);
		return listIndex.intValue();
	}

	/**
	 * Builds a filter to restrict a query to a particular source page ID.
	 * 
	 * @param source
	 *            The page ID of the source page to filter by
	 * @return The filter to apply to the query
	 */
	private static Filter getSourceFilter(final PageId source) {
		final int number = source.getNumber();
		final Filter numberFilter = getSourceNumberFilter(number);
		final String version = source.getVersion();
		final Filter versionFilter = getSourceVersionFilter(version);
		return CompositeFilterOperator.and(numberFilter, versionFilter);
	}

	/**
	 * Builds a filter to restrict a query to a particular source page ID
	 * number.
	 * 
	 * @param number
	 *            The source page number to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getSourceNumberFilter(final int number) {
		final String propertyName = SOURCE_NUMBER_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = number;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the number component of the source page ID from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the source page ID
	 * @return The number component of the source page ID
	 */
	private static int getSourceNumberFromEntity(final Entity entity) {
		final Long number = (Long) entity.getProperty(SOURCE_NUMBER_PROPERTY);
		return number.intValue();
	}

	/**
	 * Builds a filter to restrict a query to a particular source page ID
	 * version.
	 * 
	 * @param version
	 *            The source page version to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getSourceVersionFilter(final String version) {
		final String propertyName = SOURCE_VERSION_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = version;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the version component of the source page ID from the given
	 * entity.
	 * 
	 * @param entity
	 *            The entity containing the source page ID
	 * @return The version component of the source page ID
	 */
	private static String getSourceVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(SOURCE_VERSION_PROPERTY);
	}

	/**
	 * Returns the text from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the text
	 * @return The text
	 */
	private static String getTextFromEntity(final Entity entity) {
		return (String) entity.getProperty(TEXT_PROPERTY);
	}

	private int listIndex;
	private PageId source;
	private int target;
	private String text;

	public StoryOption() {
		this.setListIndex(INVALID_LIST_INDEX);
		this.setTarget(INVALID_TARGET_PAGE_NUMBER);
	}

	private boolean areSourceAndIndexSet() {
		final PageId source = this.getSource();
		final boolean isSourceSet = (source != null && source.isValid());
		final int listIndex = this.getListIndex();
		final boolean isListIndexSet = (listIndex != INVALID_LIST_INDEX);
		return isSourceSet && isListIndexSet;
	}

	private Entity getEntityWithTarget() {
		final DatastoreService store = getStore();
		final Query query1 = new Query(KIND_NAME);
		final int target = this.getTarget();
		final Filter filter = this.getTargetFilter(target);
		final Query query = query1.setFilter(filter);
		final PreparedQuery preparedQuery = store.prepare(query);
		return getSingleEntity(preparedQuery);
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
	 * Returns the list index of this story option.
	 * 
	 * @return The list index of this story option
	 */
	public int getListIndex() {
		return this.listIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deuteriumlabs.dendrite.model.Model#getMatchingQuery()
	 */
	@Override
	Query getMatchingQuery() {
		if (this.areSourceAndIndexSet() == true) {
			return getQueryMatchingSourceAndIndex();
		} else {
			return getQueryMatchingTarget();
		}
	}

	private Query getQueryMatchingSourceAndIndex() {
		final Query query = new Query(KIND_NAME);
		final PageId source = this.getSource();
		final Filter sourceFilter = getSourceFilter(source);
		final int listIndex = this.getListIndex();
		final Filter listIndexFilter = getListIndexFilter(listIndex);
		final Filter filter;
		filter = CompositeFilterOperator.and(sourceFilter, listIndexFilter);
		return query.setFilter(filter);
	}

	private Query getQueryMatchingTarget() {
		final Query query = new Query(KIND_NAME);
		final int target = this.getTarget();
		final Filter filter = getTargetFilter(target);
		return query.setFilter(filter);
	}

	/**
	 * Returns the source page ID of this story option.
	 * 
	 * @return The source page ID of this story option
	 */
	public PageId getSource() {
		return this.source;
	}

	public int getTarget() {
		return this.target;
	}

	private Filter getTargetFilter(int target) {
		final String propertyName = TARGET_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final int value = target;
		return new FilterPredicate(propertyName, operator, value);
	}

	private int getTargetFromEntity(final Entity entity) {
		final Long number = (Long) entity.getProperty(TARGET_PROPERTY);
		if (number != null)
			return number.intValue();
		else
			return INVALID_TARGET_PAGE_NUMBER;
	}

	/**
	 * Returns the text of this story option.
	 * 
	 * @return The text of this story option
	 */
	public String getText() {
		return this.text;
	}

	public boolean isConnected() {
		final int target = this.getTarget();
		return (target > 0);
	}

	/**
	 * Reads the value from the entity corresponding to the list index of this
	 * story option.
	 * 
	 * @param entity
	 *            The entity storing the list index
	 */
	private void readListIndexFromEntity(final Entity entity) {
		final int listIndex = getListIndexFromEntity(entity);
		this.setListIndex(listIndex);
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
		this.readSourceFromEntity(entity);
		this.readListIndexFromEntity(entity);
		this.readTextFromEntity(entity);
		this.readTargetFromEntity(entity);
	}

	/**
	 * Reads the values from the entity corresponding to the source page ID of
	 * this story option.
	 * 
	 * @param entity
	 *            The entity storing the source page ID
	 */
	private void readSourceFromEntity(final Entity entity) {
		final PageId source = new PageId();
		final int number = getSourceNumberFromEntity(entity);
		source.setNumber(number);
		final String version = getSourceVersionFromEntity(entity);
		source.setVersion(version);
		this.setSource(source);
	}

	private void readTargetFromEntity(final Entity entity) {
		final int target = getTargetFromEntity(entity);
		this.setTarget(target);
	}

	/**
	 * Reads the value from the entity corresponding to the text of this story
	 * option.
	 * 
	 * @param entity
	 *            The entity storing the text
	 */
	private void readTextFromEntity(final Entity entity) {
		final String text = getTextFromEntity(entity);
		this.setText(text);
	}

	public void readWithTarget() {
		final Entity entity = this.getEntityWithTarget();
		if (entity != null)
			this.readPropertiesFromEntity(entity);
	}

	/**
	 * Sets the list index of this story option
	 * 
	 * @param listIndex
	 *            The new list index for this story option
	 */
	public void setListIndex(final int listIndex) {
		this.listIndex = listIndex;
	}

	/**
	 * Sets the value in the entity corresponding to the list index for this
	 * story option.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setListIndexInEntity(final Entity entity) {
		final int listIndex = this.getListIndex();
		entity.setProperty(LIST_INDEX_PROPERTY, listIndex);
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
		this.setSourceInEntity(entity);
		this.setListIndexInEntity(entity);
		this.setTextInEntity(entity);
		this.setTargetInEntity(entity);
	}

	/**
	 * Sets the source page ID of this story option.
	 * 
	 * @param source
	 *            The new source page ID for this story option
	 */
	public void setSource(final PageId source) {
		this.source = source;
	}

	/**
	 * Sets the values in the entity corresponding to the source page ID of this
	 * story option.
	 * 
	 * @param entity
	 *            The entity in which the values are to be stored
	 */
	private void setSourceInEntity(final Entity entity) {
		final PageId source = this.getSource();
		final int number = source.getNumber();
		entity.setProperty(SOURCE_NUMBER_PROPERTY, number);
		final String version = source.getVersion();
		entity.setProperty(SOURCE_VERSION_PROPERTY, version);
	}

	public void setTarget(final int target) {
		this.target = target;
	}

	private void setTargetInEntity(final Entity entity) {
		final int target = this.getTarget();
		if (target != 0)
			entity.setProperty(TARGET_PROPERTY, target);
	}

	/**
	 * Sets the text of this story option.
	 * 
	 * @param text
	 *            The new text for this story option
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Sets the value in the entity corresponding to the text of this story
	 * option.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setTextInEntity(final Entity entity) {
		final String text = this.getText();
		entity.setProperty(TEXT_PROPERTY, text);
	}

}
