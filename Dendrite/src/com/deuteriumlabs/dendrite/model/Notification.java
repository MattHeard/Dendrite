/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 
 */
public class Notification extends Model {
    
    private static final String IS_NEW_PROPERTY = "isNew";
    private static final String KIND_NAME = "Notification";
    private static final String RECIPIENT_ID_PROPERTY = "recipient";
    private static final String TIME_PROPERTY = "time";

    /**
     * @param id
     * @return
     */
    public static int countNewNotificationsForRecipient(
            final String recipientId) {
        final Query query = new Query(KIND_NAME);
        final Filter recipientFilter = getRecipientFilter(recipientId);
        final boolean isNew = true;
        final Filter isNewFilter = getIsNewFilter(isNew);
        final Filter filter;
        filter = CompositeFilterOperator.and(recipientFilter, isNewFilter); 
        query.setFilter(filter);
        final DatastoreService store = getStore();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    /**
     * @param entity
     * @return
     */
    private static long getEntityIdFromEntity(final Entity entity) {
        final long id = entity.getKey().getId();
        return id;
    }

    /**
     * @param isNew
     * @return
     */
    private static Filter getIsNewFilter(final boolean isNew) {
        final String propertyName = IS_NEW_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final boolean value = isNew;
        return new FilterPredicate(propertyName, operator, value);
    }
    /**
     * @param entity
     * @return
     */
    private static boolean getIsNewFromEntity(final Entity entity) {
        final Boolean isNew = (Boolean) entity.getProperty(IS_NEW_PROPERTY);
        return isNew;
    }
    
    /**
     * @param userId
     * @return
     */
    public static List<Notification> getNotificationsForUser(
            final String userId) {
        final Query query = new Query(KIND_NAME);
        final Filter filter = getRecipientFilter(userId);
        query.setFilter(filter);
        query.addSort(TIME_PROPERTY, SortDirection.ASCENDING);
        final DatastoreService store = getStore();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final List<Entity> entities = preparedQuery.asList(fetchOptions);
        final List<Notification> notifications;
        notifications = getNotificationsFromEntities(entities);
        return notifications;
    }
    
    /**
     * @param entities
     * @return
     */
    private static List<Notification> getNotificationsFromEntities(
            List<Entity> entities) {
        final List<Notification> notifications = new ArrayList<Notification>();
        for (final Entity entity : entities) {
            final Notification notification;
            final String LOVER_ID_PROPERTY;
            LOVER_ID_PROPERTY = PgLovedNotification.LOVER_ID_PROPERTY;
            final String CHILD_AUTHOR_ID_PROPERTY =
            		PgChildNotification.CHILD_AUTHOR_ID_PROPERTY;
            final String REWRITE_AUTHOR_ID_PROPERTY =
            		PgRewriteNotification.REWRITE_AUTHOR_ID_PROPERTY;
            final String FOLLOWER_ID_PROPERTY;
            FOLLOWER_ID_PROPERTY = FollowNotification.FOLLOWER_ID_PROPERTY;
			if (entity.hasProperty(LOVER_ID_PROPERTY) == true) {
                notification = new PgLovedNotification(entity);
            } else if (entity.hasProperty(CHILD_AUTHOR_ID_PROPERTY) == true) {
            	notification = new PgChildNotification(entity);
            } else if (entity.hasProperty(REWRITE_AUTHOR_ID_PROPERTY) == true) {
            	notification = new PgRewriteNotification(entity);
            } else if (entity.hasProperty(FOLLOWER_ID_PROPERTY) == true) {
            	notification = new FollowNotification(entity);
            } else {
                notification = new Notification(entity);
            }
            notifications.add(notification);
        }
        return notifications;
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

    private long entityId;

    private boolean isNew;

    private String recipientId;

    private Date time;

    public Notification() {
        this.setTimeToNow();
        this.setAsNew();
    }

    /**
     * @param entity
     */
    public Notification(final Entity entity) {
        this.readPropertiesFromEntity(entity);
    }

    /**
     * @return
     */
    private long getEntityId() {
        return this.entityId;
    }

    public long getId() {
        return this.getEntityId();
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
    public String getMsg() {
        return "Something happened. (Unknown notification.)";
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
    private void readEntityIdFromEntity(final Entity entity) {
        final long id = getEntityIdFromEntity(entity);
        this.setEntityId(id);
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
        this.readEntityIdFromEntity(entity);
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
     * @param id
     */
    private void setEntityId(final long id) {
        this.entityId = id;
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

    /**
     * @param notificationId
     */
    public void setId(final String notificationId) {
        try {
            final long entityId = Long.parseLong(notificationId);
            this.setEntityId(entityId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return
     */
    public void deleteById() {
        final long id = this.getEntityId();
        final Key key = KeyFactory.createKey(KIND_NAME, id);
        final DatastoreService store = getStore();
        store.delete(key);
    }

}
