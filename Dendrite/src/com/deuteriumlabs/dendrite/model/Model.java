/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.Date;

import com.deuteriumlabs.dendrite.queries.SingleEntity;
import com.deuteriumlabs.dendrite.queries.Store;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;

public abstract class Model {
    private static final String CREATION_DATE_PROPERTY = "creationDate";

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
        final DatastoreQuery query = getMatchingQuery();
        final Store store = new Store();
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
        final DatastoreService store = new Store().get();
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
        final Store store = new Store();
        final DatastoreQuery query = getMatchingQuery();
        final PreparedQuery preparedQuery = store.prepare(query);
        return new SingleEntity(preparedQuery).get();
    }

    abstract String getKindName();
    abstract DatastoreQuery getMatchingQuery();
    abstract void readPropertiesFromEntity(final DatastoreEntity entity);
    abstract void setPropertiesInEntity(final DatastoreEntity entity);
}
