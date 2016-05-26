package com.deuteriumlabs.dendrite.dependencies;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;

public class Store {
    private static DatastoreService store = DatastoreServiceFactory.getDatastoreService();
    
    public DatastoreService get() {
        return store;
    }
    
    public PreparedQuery prepare(final DatastoreQuery query) {
        return store.prepare(query.get());
    }

    public void delete(final Key key) {
        store.delete(key);
    }

    public void put(final Entity entity) {
        store.put(entity);
    }
}
