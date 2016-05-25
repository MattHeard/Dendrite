package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Query;

public class DatastoreQuery {

    private Query query;

    public DatastoreQuery(final String kind) {
        query = new Query(kind);
    }

    public Query get() {
        return query;
    }

}
