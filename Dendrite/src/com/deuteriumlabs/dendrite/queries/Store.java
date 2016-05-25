package com.deuteriumlabs.dendrite.queries;

import com.deuteriumlabs.dendrite.model.DatastoreQuery;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;

public class Store {
    private static DatastoreService store = DatastoreServiceFactory.getDatastoreService();
    
    public DatastoreService get() {
        return store;
    }
    
    public PreparedQuery prepare(final DatastoreQuery query) {
        return store.prepare(query.get());
    }
}
