package com.deuteriumlabs.dendrite.dependencies;

import com.google.appengine.api.datastore.Projection;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreQuery {
    private final Query query;

    public DatastoreQuery(final Query query) {
        this.query = query;
    }

    public DatastoreQuery(final String kind) {
        query = new Query(kind);
    }

    public DatastoreQuery addProjection(final Projection projection) {
        query.addProjection(projection);

        return this;
    }

    public DatastoreQuery addProjection(final PropertyProjection projection) {
        query.addProjection(projection);

        return this;
    }

    public DatastoreQuery addSort(final String propertyName) {
        query.addSort(propertyName);

        return this;
    }

    public DatastoreQuery addSort(final String propertyName,
            final SortDirection direction) {
        query.addSort(propertyName, direction);

        return this;
    }

    public Query get() {
        return query;
    }

    public Filter getFilter() {
        return query.getFilter();
    }

    public String getKind() {
        return query.getKind();
    }

    public DatastoreQuery setFilter(final Filter filter) {
        query.setFilter(filter);
        return this;
    }

    public DatastoreQuery setKeysOnly() {
        query.setKeysOnly();

        return this;
    }

}
