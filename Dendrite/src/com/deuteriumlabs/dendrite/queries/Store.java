package com.deuteriumlabs.dendrite.queries;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Store {
    private static DatastoreService store = DatastoreServiceFactory.getDatastoreService();
    
    public DatastoreService get() {
        return store;
    }
    
    public PreparedQuery prepare(final Query query) {
        return store.prepare(query);
    }
}
