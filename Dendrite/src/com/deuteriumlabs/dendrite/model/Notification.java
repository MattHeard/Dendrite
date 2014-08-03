/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * 
 */
public class Notification extends Model {
    
    private static final String IS_NEW_PROPERTY = "isNew";
    private static final String KIND_NAME = "Notification";
    private static final String RECIPIENT_ID_PROPERTY = "recipient";
    private static final String TIME_PROPERTY = "time";

    /**
     * @param entity
     * @return
     */
    private static boolean getIsNewFromEntity(final Entity entity) {
        final Boolean isNew = (Boolean) entity.getProperty(IS_NEW_PROPERTY);
        return isNew;
    }

    /**
     * @param recipientId
     * @return
     */
    private static Filter getRecipientFilter(final String recipientId) {
        final String propertyName = RECIPIENT_ID_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final String value = recipientId;
        return new FilterPredicate(propertyName, operator, value);
    }

    /**
     * @param entity
     * @return
     */
    private static String getRecipientFromEntity(final Entity entity) {
        final String id = (String) entity.getProperty(RECIPIENT_ID_PROPERTY);
        return id;
    }
    /**
     * @param time
     * @return
     */
    private static Filter getTimeFilter(final Date time) {
        final String propertyName = TIME_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final Date value = time;
        return new FilterPredicate(propertyName, operator, value);
    }
    
    /**
     * @param entity
     * @return
     */
    private static Date getTimeFromEntity(final Entity entity) {
        final Date time = (Date) entity.getProperty(TIME_PROPERTY);
        return time;
    }
    private boolean isNew;
    private String recipientId;

    private Date time;

    public Notification() {
        this.setTimeToNow();
        this.setAsNew();
    }

    /* (non-Javadoc)
     * @see com.deuteriumlabs.dendrite.model.Model#getKindName()
     */
    @Override
    String getKindName() {
        return KIND_NAME;
    }

    /* (non-Javadoc)
     * @see com.deuteriumlabs.dendrite.model.Model#getMatchingQuery()
     */
    @Override
    Query getMatchingQuery() {
        final Query query = new Query(KIND_NAME);
        final String recipientId = this.getRecipientId();
        final Filter recipientFilter = getRecipientFilter(recipientId);
        final Date time = this.getTime();
        final Filter timeFilter = getTimeFilter(time);
        final Filter filter;
        filter = CompositeFilterOperator.and(recipientFilter, timeFilter); 
        return query.setFilter(filter);
    }

    /**
     * @return
     */
    private String getRecipientId() {
        return this.recipientId;
    }

    /**
     * @return
     */
    private Date getTime() {
        return this.time;
    }

    /**
     * @return
     */
    protected boolean isNew() {
        return this.isNew;
    }

    /**
     * @param entity
     */
    private void readIsNewFromEntity(final Entity entity) {
        final boolean isNew = getIsNewFromEntity(entity);
        this.setNew(isNew);
    }

    /* (non-Javadoc)
     * @see com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google.appengine.api.datastore.Entity)
     */
    @Override
    void readPropertiesFromEntity(final Entity entity) {
        this.readRecipientFromEntity(entity);
        this.readTimeFromEntity(entity);
        this.readIsNewFromEntity(entity);
    }

    /**
     * @param entity
     */
    private void readRecipientFromEntity(final Entity entity) {
        final String id = getRecipientFromEntity(entity);
        this.setRecipientId(id);
    }

    /**
     * @param entity
     */
    private void readTimeFromEntity(final Entity entity) {
        final Date time = getTimeFromEntity(entity);
        this.setTime(time);
    }

    /**
     * 
     */
    private void setAsNew() {
        this.setNew(true);
    }

    /**
     * @param entity
     */
    private void setIsNewInEntity(final Entity entity) {
        final boolean isNew = this.isNew();
        entity.setProperty(IS_NEW_PROPERTY, isNew);
    }

    /**
     * @param isNew
     */
    private void setNew(final boolean isNew) {
        this.isNew = isNew;
    }

    /* (non-Javadoc)
     * @see com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google.appengine.api.datastore.Entity)
     */
    @Override
    void setPropertiesInEntity(final Entity entity) {
        this.setRecipientInEntity(entity);
        this.setTimeInEntity(entity);
        this.setIsNewInEntity(entity);
    }

    /**
     * @param id
     */
    public void setRecipientId(final String id) {
        this.recipientId = id;
    }

    /**
     * @param entity
     */
    private void setRecipientInEntity(final Entity entity) {
        final String id = this.getRecipientId();
        entity.setProperty(RECIPIENT_ID_PROPERTY, id);
    }

    /**
     * @param time
     */
    private void setTime(final Date time) {
        this.time = time;
    }

    /**
     * @param entity
     */
    private void setTimeInEntity(final Entity entity) {
        final Date time = this.getTime();
        entity.setProperty(TIME_PROPERTY, time);
    }

    /**
     * 
     */
    private void setTimeToNow() {
        // Date() allocates a Date object and initialises it so that it
        // represents the time at which it was allocated, measured to the
        // nearest millisecond.
        final Date now = new Date();
        this.setTime(now);
    }

}
