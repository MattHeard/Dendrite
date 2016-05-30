/* © 2013-2015 Deuterium Labs Limited */
/**
 *
 */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

/**
 *
 */
public class PgLovedNotification extends Notification {

    public static final String LOVER_ID_PROPERTY = "loverId";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";

    private String loverId;
    private PageId pgId;

    public PgLovedNotification() {
    }

    /**
     * @param entity
     */
    public PgLovedNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = getLoverName();
        nameChunk.url = "/author?id=" + getLoverId();
        msg.add(nameChunk);

        HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " loves page ";
        msg.add(unlinkedChunk);

        final HyperlinkedStr pgIdChunk = new HyperlinkedStr();
        final PageId pgId = getPgId();
        pgIdChunk.str = pgId.toString();
        pgIdChunk.url = "/read?p=" + pgId;
        msg.add(pgIdChunk);

        unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = ".";
        msg.add(unlinkedChunk);

        return msg;
    }

    @Override
    public String getMsg() {
        final String lover = getLoverName();
        final PageId pgId = getPgId();
        return lover + " loves page " + pgId + ".";
    }

    /**
     * @param loverId
     *            The user who now loves the page.
     */
    public void setLoverId(final String loverId) {
        this.loverId = loverId;
    }

    /**
     * @param pgId
     *            The page which has been loved.
     */
    public void setPgId(final PageId pgId) {
        this.pgId = pgId;
    }

    /**
     * @param entity
     * @return
     */
    private String getLoverFromEntity(final DatastoreEntity entity) {
        final String id = (String) entity.getProperty(LOVER_ID_PROPERTY);
        return id;
    }

    /**
     * @return
     */
    private String getLoverId() {
        return loverId;
    }

    /**
     * @return
     */
    private String getLoverName() {
        final String id = getLoverId();
        final User lover = new User();
        lover.setId(id);
        lover.read();
        return lover.getDefaultPenName();
    }

    /**
     * @return
     */
    private PageId getPgId() {
        return pgId;
    }

    /**
     * @param entity
     * @return
     */
    private int getPgIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    /**
     * @param entity
     * @return
     */
    private String getPgIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    /**
     * @param entity
     */
    private void readLoverIdFromEntity(final DatastoreEntity entity) {
        final String id = getLoverFromEntity(entity);
        setLoverId(id);
    }

    /**
     * @param entity
     */
    private void readPgIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        setPgId(id);
    }

    /**
     * @param entity
     */
    private void setLoverIdInEntity(final DatastoreEntity entity) {
        final String id = getLoverId();
        entity.setProperty(LOVER_ID_PROPERTY, id);
    }

    /**
     * @param entity
     */
    private void setPgIdInEntity(final DatastoreEntity entity) {
        final PageId id = getPgId();
        final int num = id.getNumber();
        entity.setProperty(PG_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PG_ID_VERSION_PROPERTY, version);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readPgIdFromEntity(entity);
        readLoverIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPgIdInEntity(entity);
        setLoverIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
