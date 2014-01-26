package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Represents the visitor to the website. <code>User</code> instances provide
 * two main purposes: storing user preferences and crediting authors. The
 * website is designed to be as usable as possible by anonymous, logged-out
 * visitors, but logging in makes it possible to store user preferences between
 * visits and makes it possible to credit multiple story pages to a single
 * author.
 */
public class User extends Model {
	private static final String DEFAULT_PEN_NAME_PROPERTY = "defaultPenName";
	private static final String ID_PROPERTY = "id";
	private static final String KIND_NAME = "User";

	/**
	 * Returns the default pen name from the given entity.
	 * @param entity The entity containing the default pen name
	 * @return The default pen name
	 */
	private static String getDefaultPenNameFromEntity(final Entity entity) {
		return (String) entity.getProperty(DEFAULT_PEN_NAME_PROPERTY);
	}

	/**
	 * Builds a filter to restrict a query to a particular user ID.
	 * @param id The ID to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdFilter(final String id) {
		final String propertyName = ID_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = id;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the user ID from the given entity.
	 * @param entity The entity containing the user ID
	 * @return The user ID
	 */
	private static String getIdFromEntity(final Entity entity) {
		return (String) entity.getProperty(ID_PROPERTY);
	}

	private String defaultPenName;
	private String id;

	/**
	 * Returns the default pen name of this user.
	 * @return The default pen name of this user
	 */
	public String getDefaultPenName() {
		return this.defaultPenName;
	}

	/**
	 * Returns the unique ID of this user.
	 * @return The unique ID of this user
	 */
	private String getId() {
		return this.id;
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
		final String id = this.getId();
		final Filter idFilter = getIdFilter(id);
		return query.setFilter(idFilter);
	}

	/**
	 * Reads the value from the entity corresponding to the default pen name of
	 * this user.
	 * @param entity The entity storing the default pen name
	 */
	private void readDefaultPenNameFromEntity(final Entity entity) {
		final String defaultPenName = getDefaultPenNameFromEntity(entity);
		this.setDefaultPenName(defaultPenName);
	}

	/**
	 * Reads the value from the entity corresponding to the unique ID of this
	 * user.
	 * @param entity The entity storing the user ID
	 */
	private void readIdFromEntity(final Entity entity) {
		final String id = getIdFromEntity(entity);
		this.setId(id);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void readPropertiesFromEntity(final Entity entity) {
		this.readIdFromEntity(entity);
		this.readDefaultPenNameFromEntity(entity);
	}

	/**
	 * Sets the default pen name of this user.
	 * @param defaultPenName The default pen name of this user
	 */
	public void setDefaultPenName(final String defaultPenName) {
		this.defaultPenName = defaultPenName;
	}

	/**
	 * Sets the value in the entity corresponding to the default pen name of
	 * this user.
	 * @param entity The entity in which the value is to be stored
	 */
	private void setDefaultPenNameInEntity(final Entity entity) {
		final String defaultPenName = this.getDefaultPenName();
		entity.setProperty(DEFAULT_PEN_NAME_PROPERTY, defaultPenName);
	}

	/**
	 * Sets the unique ID of this user.
	 * @param id The unique ID of this user
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Sets the value in the entity corresponding to the unique ID of this user.
	 * @param entity The entity in which the value is to be stored
	 */
	private void setIdInEntity(final Entity entity) {
		final String id = this.getId();
		entity.setProperty(ID_PROPERTY, id);
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google.appengine.api.datastore.Entity)
	 */
	@Override
	void setPropertiesInEntity(final Entity entity) {
		this.setIdInEntity(entity);
		this.setDefaultPenNameInEntity(entity);
	}
}
