package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class Model {

	/**
	 * Returns the datastore. The main purpose of this method is to shorten the
	 * method name for less clumsy regular use.
	 * @return the datastore
	 */
	private static DatastoreService getStore() {
		return DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * Builds an entity containing the current data of this model and puts it in
	 * the datastore.
	 */
	public void create() {
		final String kindName = this.getKindName();
		final Entity entity = new Entity(kindName);
		this.setPropertiesInEntity(entity);
		final DatastoreService store = getStore();
		store.put(entity);
	}
	
	/**
	 * Returns the model's kind. Each model kind has a unique name which allows
	 * for identification in the datastore. Subclasses implement this method to
	 * manage their own kind name.
	 * @return the kind of this model
	 */
	abstract String getKindName();

	/**
	 * Returns a query which should identify a single entity matching this
	 * model. Subclasses implement this method to use their own unique
	 * identifying properties to build filters for building this query.
	 * @return the query for identifying this model instance
	 */
	abstract Query getMatchingQuery();

	/**
	 * Returns true if this model instance is in the store.
	 * @return true if this is in the store, false otherwise.
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
	 * Sets the values in the entity corresponding to the properties of this
	 * model instance.
	 * @param entity
	 */
	abstract void setPropertiesInEntity(final Entity entity);

}
