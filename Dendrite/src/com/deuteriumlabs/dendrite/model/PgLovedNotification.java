/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;

/**
 * 
 */
public class PgLovedNotification extends Notification {

    private static final String LOVER_ID_PROPERTY = "loverId";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
    
    private PageId pgId;
    private String loverId;

    /**
     * @param pgId The page which has been loved.
     */
    public void setPgId(final PageId pgId) {
        this.pgId = pgId;
    }

    /**
     * @param loverId The user who now loves the page.
     */
    public void setLoverId(final String loverId) {
        this.loverId = loverId;
    }

    @Override
    void readPropertiesFromEntity(final Entity entity) {
        super.readPropertiesFromEntity(entity);
        this.readPgIdFromEntity(entity);
        this.readLoverIdFromEntity(entity);
    }

    /**
     * @param entity
     */
    private void readLoverIdFromEntity(final Entity entity) {
        final String id = getLoverFromEntity(entity);
        this.setLoverId(id);
    }

    /**
     * @param entity
     * @return
     */
    private String getLoverFromEntity(final Entity entity) {
        final String id = (String) entity.getProperty(LOVER_ID_PROPERTY);
        return id;
    }

    /**
     * @param entity
     */
    private void readPgIdFromEntity(final Entity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        this.setPgId(id);
    }

    /**
     * @param entity
     * @return
     */
    private String getPgIdVersionFromEntity(final Entity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    /**
     * @param entity
     * @return
     */
    private int getPgIdNumFromEntity(final Entity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    @Override
    void setPropertiesInEntity(final Entity entity) {
        super.setPropertiesInEntity(entity);
        this.setPgIdInEntity(entity);
        this.setLoverIdInEntity(entity);
    }

    /**
     * @param entity
     */
    private void setLoverIdInEntity(final Entity entity) {
        final String id = this.getLoverId();
        entity.setProperty(LOVER_ID_PROPERTY, id);
    }

    /**
     * @return
     */
    private String getLoverId() {
        return this.loverId;
    }

    /**
     * @param entity
     */
    private void setPgIdInEntity(final Entity entity) {
        final PageId id = this.getPgId();
        final int num = id.getNumber();
        entity.setProperty(PG_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PG_ID_VERSION_PROPERTY, version);
    }

    /**
     * @return
     */
    private PageId getPgId() {
        return this.pgId;
    }
}
