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
public class PgChildNotification extends Notification {

    private static final String CHILD_AUTHOR_ID_PROPERTY = "childAuthorId";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";

    private String childAuthorId;
    private PageId pgId;

    public PgChildNotification() {
    }

    public PgChildNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = getChildAuthorName();
        nameChunk.url = "/author?id=" + getChildAuthorId();
        msg.add(nameChunk);

        HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " extended one of your pages and wrote page ";
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
        final String name = getChildAuthorName();
        final PageId pgId = getPgId();
        final String msg;
        msg = name + " extended one of your pages and wrote page " + pgId + ".";
        return msg;
    }

    /**
     * @param id
     */
    public void setChildAuthorId(final String id) {
        childAuthorId = id;
    }

    /**
     * @param id
     */
    public void setPgId(final PageId id) {
        pgId = id;
    }

    /**
     * @return
     */
    private String getChildAuthorId() {
        return childAuthorId;
    }

    private String getChildAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = (String) entity.getProperty(CHILD_AUTHOR_ID_PROPERTY);
        return id;
    }

    /**
     * @return
     */
    private String getChildAuthorName() {
        final String id = getChildAuthorId();
        final User author = new User();
        author.setId(id);
        author.read();
        final String name = author.getDefaultPenName();
        return name;
    }

    /**
     * @return
     */
    private PageId getPgId() {
        return pgId;
    }

    private int getPgIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private String getPgIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private void readChildAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getChildAuthorIdFromEntity(entity);
        setChildAuthorId(id);
    }

    private void readPgIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        setPgId(id);
    }

    private void setChildAuthorIdInEntity(final DatastoreEntity entity) {
        final String id = getChildAuthorId();
        entity.setProperty(CHILD_AUTHOR_ID_PROPERTY, id);
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
        readChildAuthorIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPgIdInEntity(entity);
        setChildAuthorIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
