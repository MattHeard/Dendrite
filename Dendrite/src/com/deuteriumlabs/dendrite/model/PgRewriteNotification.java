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

    private static int getPageIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private static String getPageIdVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private static String getRewriteAuthorIdFromEntity(
            final DatastoreEntity entity) {
        final String propertyName = REWRITE_AUTHOR_ID_PROPERTY;
        final String id = (String) entity.getProperty(propertyName);
        return id;
    }

    private PageId pageId;
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
        final String name = getRewriteAuthorName();
        final PageId pageId = getPageId();
        final String msg;
        msg = name + " rewrote one of your pages and wrote page " + pageId + ".";
        return msg;
    }

    /**
     * @param id
     */
    public void setPageId(final PageId id) {
        pageId = id;
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
    private PageId getPageId() {
        return pageId;
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

    private void readPageIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPageIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPageIdVersionFromEntity(entity);
        id.setVersion(version);
        setPageId(id);
    }

    private void readRewriteAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getRewriteAuthorIdFromEntity(entity);
        setRewriteAuthorId(id);
    }

    /**
     * @param entity
     */
    private void setPageIdInEntity(final DatastoreEntity entity) {
        final PageId id = getPageId();
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
        readPageIdFromEntity(entity);
        readRewriteAuthorIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPageIdInEntity(entity);
        setRewriteAuthorIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
