package com.deuteriumlabs.dendrite.dependencies;

import com.google.appengine.api.datastore.Projection;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreQuery {
    private Query query;

    public DatastoreQuery(final String kind) {
        query = new Query(kind);
    }

    public DatastoreQuery(Query query) {
        this.query = query;
    }

    public Query get() {
        return query;
    }

    public DatastoreQuery setFilter(final Filter filter) {
        query.setFilter(filter);
        return this;
    }

    public DatastoreQuery addSort(final String propertyName,
            final SortDirection direction) {
        query.addSort(propertyName, direction);
        return this;
    }

    public DatastoreQuery addSort(String propertyName) {
        query.addSort(propertyName);
        return this;
    }

    public DatastoreQuery setKeysOnly() {
        query.setKeysOnly();
        return this;
    }

    public DatastoreQuery addProjection(PropertyProjection projection) {
        query.addProjection(projection);
        return this;
    }

    public DatastoreQuery addProjection(Projection projection) {
        query.addProjection(projection);
        return this;
    }

    public String getKind() {
        return query.getKind();
    }

    public Filter getFilter() {
        return query.getFilter();
    }

}
