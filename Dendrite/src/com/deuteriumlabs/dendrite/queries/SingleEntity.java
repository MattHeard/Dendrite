package com.deuteriumlabs.dendrite.queries;

import com.deuteriumlabs.dendrite.model.DatastoreEntity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;

public class SingleEntity {
    private PreparedQuery preparedQuery;

    public SingleEntity(final PreparedQuery preparedQuery) {
        this.preparedQuery = preparedQuery;
    }
    
    public DatastoreEntity get() {
        try {
            return new DatastoreEntity(preparedQuery.asSingleEntity());
        } catch (final TooManyResultsException e) {
            return null;
        }
    }
}
