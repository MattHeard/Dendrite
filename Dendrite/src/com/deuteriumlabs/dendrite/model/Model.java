/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;

/**
 * Represents the data of the website. An interface is provided for putting and
 * getting data from the Google App Engine datastore.
 */
public abstract class Model {
    private DatastoreEntity entity = null;
    
	private static final String CREATION_DATE_PROPERTY = "creationDate";

	/**
	 * Returns a single entity from the prepared query.
	 * 
	 * @param preparedQuery
	 *            the prepared query matching a single entity
	 * @return The single entity from the prepared query
	 */
	protected static DatastoreEntity getSingleEntity(final PreparedQuery preparedQuery) {
		try {
			return new DatastoreEntity(preparedQuery.asSingleEntity());
		} catch (TooManyResultsException e) {
			return null;
		}
	}

	/**
	 * Returns the datastore. The main purpose of this method is to shorten the
	 * method name for less clumsy regular use.
	 * 
	 * @return The datastore
	 */
	protected static DatastoreService getStore() {
		return DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * Builds an entity containing the current data of this model and puts it in
	 * the datastore.
	 */
	public void create() {
		final String kindName = this.getKindName();
		entity = createNewEntity(kindName);
		putEntityInStore(entity);
	}

	/**
	 * Retrieves the entity matching this model instance and deletes it from the
	 * store.
	 */
	public void delete() {
		if (entity == null) {
            try {
                entity = this.getMatchingEntity();
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
		}
		if (entity != null) {
			deleteEntityByKey(entity);
			entity = null;
		}
	}

	/**
	 * Returns <code>true</code> if this model instance is in the store.
	 * 
	 * @return <code>true</code> if this is in the store, <code>false</code>
	 *         otherwise.
	 */
	public boolean isInStore() {
		final Query query = this.getMatchingQuery();
		final DatastoreService store = getStore();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final int count = preparedQuery.countEntities(fetchOptions);
		return (count > 0);
	}

	/**
	 * Retrieves the matching entity from the datastore, reads the properties
	 * from the entity, and inserts those properties into this model instance.
	 */
	public void read() {
	    if (entity == null) {
            try {
                entity = this.getMatchingEntity();
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
	    }
		if (entity != null) {
			this.readPropertiesFromEntity(entity);
		}
	}

	/**
	 * Retrieves the matching entity from the datastore, replaces all of the
	 * properties in that entity, and then puts it back in the datastore.
	 */
	public void update() {
	    if (entity == null) {
            try {
                entity = this.getMatchingEntity();
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
	    }
		if (entity != null) {
			putEntityInStore(entity);
		}
	}

	private void deleteEntityByKey(final DatastoreEntity entity) {
		final Key key = entity.getKey();
		final DatastoreService store = getStore();
		store.delete(key);
	}

	private void putEntityInStore(final DatastoreEntity entity) {
		this.setCreationDate(entity);
		this.setPropertiesInEntity(entity);
		entity.putInStore();
	}

	private void setCreationDate(final DatastoreEntity entity) {
		final Date date = new Date();
		entity.setProperty(CREATION_DATE_PROPERTY, date);
	}

	protected DatastoreEntity createNewEntity(final String kindName) {
		return new DatastoreEntity(kindName);
	}

	/**
	 * Returns the entity matching this model instance. Returns
	 * <code>null</code> if there is a problem.
	 *
	 * @return The entity matching this model instance, or <code>null</code> if
	 *         the retrieval fails
	 * @throws EntityNotFoundException 
	 */
	protected DatastoreEntity getMatchingEntity()
	        throws EntityNotFoundException {
		final DatastoreService store = getStore();
		final Query query = this.getMatchingQuery();
		final PreparedQuery preparedQuery = store.prepare(query);
		return getSingleEntity(preparedQuery);
	}

	/**
	 * Returns the model's kind. Each model kind has a unique name which allows
	 * for identification in the datastore. Subclasses implement this method to
	 * manage their own kind name.
	 * 
	 * @return The kind of this model
	 */
	abstract String getKindName();

	/**
	 * Returns a query which should identify a single entity matching this
	 * model. Subclasses implement this method to use their own unique
	 * identifying properties to build filters for building this query.
	 * 
	 * @return The query for identifying this model instance
	 */
	abstract Query getMatchingQuery();

	/**
	 * Reads the values from the entity corresponding to the properties of this
	 * model instance.
	 * 
	 * @param entity
	 *            The entity storing the properties
	 */
	abstract void readPropertiesFromEntity(final DatastoreEntity entity);

	/**
	 * Sets the values in the entity corresponding to the properties of this
	 * model instance.
	 * 
	 * @param entity
	 *            The entity storing the properties
	 */
	abstract void setPropertiesInEntity(final DatastoreEntity entity);
}
