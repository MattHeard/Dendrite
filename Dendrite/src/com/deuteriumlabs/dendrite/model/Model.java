/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;

public abstract class Model {
    private static final String CREATION_DATE_PROPERTY = "creationDate";

    protected static DatastoreEntity getSingleEntity(
            final PreparedQuery preparedQuery) {
        try {
            return new DatastoreEntity(preparedQuery.asSingleEntity());
        } catch (final TooManyResultsException e) {
            return null;
        }
    }

    protected static DatastoreService getStore() {
        return DatastoreServiceFactory.getDatastoreService();
    }

    private DatastoreEntity entity = null;

    public void create() {
        final String kindName = getKindName();
        entity = createNewEntity(kindName);
        putEntityInStore(entity);
    }

    public void delete() {
        if (entity == null) {
            try {
                entity = getMatchingEntity();
            } catch (final EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (entity != null) {
            deleteEntityByKey(entity);
            entity = null;
        }
    }

    public boolean isInStore() {
        final Query query = getMatchingQuery();
        final DatastoreService store = getStore();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final int count = preparedQuery.countEntities(fetchOptions);
        return (count > 0);
    }

    public void read() {
        if (entity == null) {
            try {
                entity = getMatchingEntity();
            } catch (final EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (entity != null) {
            readPropertiesFromEntity(entity);
        }
    }

    public void update() {
        if (entity == null) {
            try {
                entity = getMatchingEntity();
            } catch (final EntityNotFoundException e) {
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
        setCreationDate(entity);
        setPropertiesInEntity(entity);
        entity.putInStore();
    }

    private void setCreationDate(final DatastoreEntity entity) {
        final Date date = new Date();
        entity.setProperty(CREATION_DATE_PROPERTY, date);
    }

    protected DatastoreEntity createNewEntity(final String kindName) {
        return new DatastoreEntity(kindName);
    }

    protected DatastoreEntity getMatchingEntity()
            throws EntityNotFoundException {
        final DatastoreService store = getStore();
        final Query query = getMatchingQuery();
        final PreparedQuery preparedQuery = store.prepare(query);
        return getSingleEntity(preparedQuery);
    }

    abstract String getKindName();
    abstract Query getMatchingQuery();
    abstract void readPropertiesFromEntity(final DatastoreEntity entity);
    abstract void setPropertiesInEntity(final DatastoreEntity entity);
}
