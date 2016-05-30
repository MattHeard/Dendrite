/* © 2013-2015 Deuterium Labs Limited */
/**
 *
 */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class PgRewriteNotification extends Notification {

    public static final String REWRITE_AUTHOR_ID_PROPERTY = "rewriteAuthorId";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";

    private static int getPgIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private static String getPgIdVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private static String getRewriteAuthorIdFromEntity(
            final DatastoreEntity entity) {
        final String propertyName = REWRITE_AUTHOR_ID_PROPERTY;
        final String id = (String) entity.getProperty(propertyName);
        return id;
    }

    private PageId pgId;
    private String rewriteAuthorId;

    public PgRewriteNotification() {
    }

    public PgRewriteNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = getRewriteAuthorName();
        if (getRewriteAuthorId() != null) {
            nameChunk.url = "/author?id=" + getRewriteAuthorId();
        }
        msg.add(nameChunk);

        HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " rewrote one of your pages and wrote page ";
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
        final String name = getRewriteAuthorName();
        final PageId pgId = getPgId();
        final String msg;
        msg = name + " rewrote one of your pages and wrote page " + pgId + ".";
        return msg;
    }

    /**
     * @param id
     */
    public void setPgId(final PageId id) {
        pgId = id;
    }

    /**
     * @param id
     */
    public void setRewriteAuthorId(final String id) {
        rewriteAuthorId = id;
    }

    /**
     * @return
     */
    private PageId getPgId() {
        return pgId;
    }

    /**
     * @return
     */
    private String getRewriteAuthorId() {
        return rewriteAuthorId;
    }

    /**
     * @return
     */
    private String getRewriteAuthorName() {
        final String id = getRewriteAuthorId();
        if (id != null) {
            final User author = new User();
            author.setId(id);
            author.read();
            return author.getDefaultPenName();
        } else {
            return "Someone";
        }
    }

    private void readPgIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        setPgId(id);
    }

    private void readRewriteAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getRewriteAuthorIdFromEntity(entity);
        setRewriteAuthorId(id);
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

    private void setRewriteAuthorIdInEntity(final DatastoreEntity entity) {
        final String id = getRewriteAuthorId();
        entity.setProperty(REWRITE_AUTHOR_ID_PROPERTY, id);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readPgIdFromEntity(entity);
        readRewriteAuthorIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPgIdInEntity(entity);
        setRewriteAuthorIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
