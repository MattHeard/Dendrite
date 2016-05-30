package com.deuteriumlabs.dendrite.dependencies;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;

public class DatastoreEntity {
    public static List<DatastoreEntity> fromPreparedQuery(
            final PreparedQuery preparedQuery,
            final FetchOptions fetchOptions) {
        final List<Entity> unwrappedEntities = preparedQuery
                .asList(fetchOptions);
        final List<DatastoreEntity> wrappedEntities = new ArrayList<DatastoreEntity>();
        for (final Entity unwrappedEntity : unwrappedEntities) {
            wrappedEntities.add(new DatastoreEntity(unwrappedEntity));
        }
        return wrappedEntities;
    }

    private final Entity entity;

    public DatastoreEntity(final Entity entity) {
        this.entity = entity;
    }

    public DatastoreEntity(final String kind) {
        entity = new Entity(kind);
    }

    public DatastoreEntity(final String kind, final String key) {
        entity = new Entity(kind, key);
    }

    public Key getKey() {
        return entity.getKey();
    }

    public Object getProperty(final String name) {
        return entity.getProperty(name);
    }

    public boolean hasProperty(final String name) {
        return entity.hasProperty(name);
    }

    public void putInStore() {
        new Store().put(entity);
    }

    public void setProperty(final String name, final Object value) {
        entity.setProperty(name, value);
    }
}
