package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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

	/**
	 * Returns a single entity from the prepared query.
	 * 
	 * @param preparedQuery
	 *            the prepared query matching a single entity
	 * @return The single entity from the prepared query
	 */
	protected static Entity getSingleEntity(final PreparedQuery preparedQuery) {
		try {
			Model.logQuery(15);
			return preparedQuery.asSingleEntity();
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
		final Entity entity = new Entity(kindName);
		Model.logCreate(1);
		putEntityInStore(entity);
	}

	/**
	 * Retrieves the entity matching this model instance and deletes it from the
	 * store.
	 */
	public void delete() {
		final Entity entity = this.getMatchingEntity();
		if (entity != null) {
			Model.logDelete(1);
			deleteEntityByKey(entity);
		}
	}

	private void deleteEntityByKey(final Entity entity) {
		final Key key = entity.getKey();
		final DatastoreService store = getStore();
		store.delete(key);
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
	 * Returns the entity matching this model instance. Returns
	 * <code>null</code> if there is a problem.
	 * 
	 * @return The entity matching this model instance, or <code>null</code> if
	 *         the retrieval fails
	 */
	private Entity getMatchingEntity() {
		final DatastoreService store = getStore();
		final Query query = this.getMatchingQuery();
		final PreparedQuery preparedQuery = store.prepare(query);
		return getSingleEntity(preparedQuery);
	}

	/**
	 * Returns a query which should identify a single entity matching this
	 * model. Subclasses implement this method to use their own unique
	 * identifying properties to build filters for building this query.
	 * 
	 * @return The query for identifying this model instance
	 */
	abstract Query getMatchingQuery();

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
		Model.logQuery(5);
		final int count = preparedQuery.countEntities(fetchOptions);
		return (count > 0);
	}

	/**
	 * Retrieves the matching entity from the datastore, reads the properties
	 * from the entity, and inserts those properties into this model instance.
	 */
	public void read() {
		final Entity entity = this.getMatchingEntity();
		if (entity != null) {
			Model.logRead(1);
			this.readPropertiesFromEntity(entity);
		}
	}

	/**
	 * Reads the values from the entity corresponding to the properties of this
	 * model instance.
	 * 
	 * @param entity
	 *            The entity storing the properties
	 */
	abstract void readPropertiesFromEntity(final Entity entity);

	/**
	 * Sets the values in the entity corresponding to the properties of this
	 * model instance.
	 * 
	 * @param entity
	 *            The entity storing the properties
	 */
	abstract void setPropertiesInEntity(final Entity entity);

	/**
	 * Retrieves the matching entity from the datastore, replaces all of the
	 * properties in that entity, and then puts it back in the datastore.
	 */
	public void update() {
		final Entity entity = this.getMatchingEntity();
		if (entity != null) {
			Model.logUpdate(1);
			putEntityInStore(entity);
		}
	}

	private void putEntityInStore(final Entity entity) {
		this.setPropertiesInEntity(entity);
		final DatastoreService store = getStore();
		store.put(entity);
	}

	static int[] queryArrCount = new int[1000];
	static int[] createArrCount = new int[1000];
	static int[] readArrCount = new int[1000];
	static int[] updateArrCount = new int[1000];
	static int[] deleteArrCount = new int[1000];
	static int queryTotalCount = 1;
	static int createTotalCount = 1;
	static int readTotalCount = 1;
	static int updateTotalCount = 1;
	static int deleteTotalCount = 1;

	public static void logQuery(final int logNum) {
		int count = queryArrCount[logNum] + 1;
		int total = queryTotalCount;
		System.out.println("query (" + logNum + ") " + count + "/" + total);
		queryArrCount[logNum]++;
		queryTotalCount++;
	}

	public static void logCreate(final int logNum) {
		int count = createArrCount[logNum] + 1;
		int total = createTotalCount;
		System.out.println("create (" + logNum + ") " + count + "/" + total);
		createArrCount[logNum]++;
		createTotalCount++;
	}

	public static void logRead(final int logNum) {
		int count = readArrCount[logNum] + 1;
		int total = readTotalCount;
		System.out.println("read (" + logNum + ") " + count + "/" + total);
		readArrCount[logNum]++;
		readTotalCount++;
	}

	public static void logUpdate(final int logNum) {
		int count = updateArrCount[logNum] + 1;
		int total = updateTotalCount;
		System.out.println("update (" + logNum + ") " + count + "/" + total);
		updateArrCount[logNum]++;
		updateTotalCount++;
	}

	public static void logDelete(final int logNum) {
		int count = deleteArrCount[logNum] + 1;
		int total = deleteTotalCount;
		System.out.println("delete (" + logNum + ") " + count + "/" + total);
		deleteArrCount[logNum]++;
		deleteTotalCount++;
	}

}
