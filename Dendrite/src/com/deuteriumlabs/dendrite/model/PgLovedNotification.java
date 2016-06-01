/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class PgLovedNotification extends Notification {

    public static final String LOVER_ID_PROPERTY = "loverId";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";

    private String loverId;
    private PageId pageId;

    public PgLovedNotification() { }

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

        final HyperlinkedStr pageIdChunk = new HyperlinkedStr();
        final PageId pageId = getPageId();
        pageIdChunk.str = pageId.toString();
        pageIdChunk.url = "/read?p=" + pageId;
        msg.add(pageIdChunk);

        unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = ".";
        msg.add(unlinkedChunk);

        return msg;
    }

    @Override
    public String getMsg() {
        final String lover = getLoverName();
        final PageId pageId = getPageId();
        return lover + " loves page " + pageId + ".";
    }

    public void setLoverId(final String loverId) {
        this.loverId = loverId;
    }

    public void setPageId(final PageId pageId) {
        this.pageId = pageId;
    }

    private String getLoverFromEntity(final DatastoreEntity entity) {
        final String id = (String) entity.getProperty(LOVER_ID_PROPERTY);
        return id;
    }

    private String getLoverId() {
        return loverId;
    }

    private String getLoverName() {
        final String id = getLoverId();
        final User lover = new User();
        lover.setId(id);
        lover.read();
        return lover.getDefaultPenName();
    }

    private PageId getPageId() {
        return pageId;
    }

    private int getPageIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private String getPageIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private void readLoverIdFromEntity(final DatastoreEntity entity) {
        final String id = getLoverFromEntity(entity);
        setLoverId(id);
    }

    private void readPageIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPageIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPageIdVersionFromEntity(entity);
        id.setVersion(version);
        setPageId(id);
    }

    private void setLoverIdInEntity(final DatastoreEntity entity) {
        final String id = getLoverId();
        entity.setProperty(LOVER_ID_PROPERTY, id);
    }

    private void setPageIdInEntity(final DatastoreEntity entity) {
        final PageId id = getPageId();
        final int num = id.getNumber();
        entity.setProperty(PG_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PG_ID_VERSION_PROPERTY, version);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readPageIdFromEntity(entity);
        readLoverIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPageIdInEntity(entity);
        setLoverIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
