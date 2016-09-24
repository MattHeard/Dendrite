/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class FollowerRewriteNotification extends Notification {
    private static final String AUTHOR_ID_PROPERTY = "authorId";
    private static final String AUTHOR_NAME_PROPERTY = "authorName";
    private static final String PAGE_ID_NUM_PROPERTY = "pageIdNum";
    private static final String PAGE_ID_VERSION_PROPERTY = "pageIdVersion";

    private static String getAuthorIdFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(AUTHOR_ID_PROPERTY);
    }

    private static String getAuthorNameFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(AUTHOR_NAME_PROPERTY);
    }

    private static int getPageIdNumFromEntity(final DatastoreEntity entity) {
        return ((Long) entity.getProperty(PAGE_ID_NUM_PROPERTY)).intValue();
    }

    private static String getPageIdVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(PAGE_ID_VERSION_PROPERTY);
    }

    private String authorId;
    private String authorName;
    private PageId pageId;

    public FollowerRewriteNotification() {
    }

    public FollowerRewriteNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = authorName;
        nameChunk.url = "/author?id=" + authorId;
        msg.add(nameChunk);

        HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " rewrote a page at page ";
        msg.add(unlinkedChunk);

        final HyperlinkedStr pageIdChunk = new HyperlinkedStr();
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
        return authorName + " rewrote a page at page " + pageId + ".";
    }

    public void setAuthorId(final String id) {
        authorId = id;
    }

    public void setAuthorName(final String name) {
        authorName = name;
    }

    public void setPageId(final PageId id) {
        pageId = id;
    }

    private void readAuthorIdFromEntity(final DatastoreEntity entity) {
        setAuthorId(getAuthorIdFromEntity(entity));
    }

    private void readAuthorNameFromEntity(final DatastoreEntity entity) {
        setAuthorName(getAuthorNameFromEntity(entity));
    }

    private void readPageIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPageIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPageIdVersionFromEntity(entity);
        id.setVersion(version);
        setPageId(id);
    }

    private void setAuthorIdInEntity(final DatastoreEntity entity) {
        entity.setProperty(AUTHOR_ID_PROPERTY, authorId);
    }

    private void setAuthorNameInEntity(final DatastoreEntity entity) {
        entity.setProperty(AUTHOR_NAME_PROPERTY, authorName);
    }

    private void setPageIdInEntity(final DatastoreEntity entity) {
        entity.setProperty(PAGE_ID_NUM_PROPERTY, pageId.getNumber());
        entity.setProperty(PAGE_ID_VERSION_PROPERTY, pageId.getVersion());
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readAuthorIdFromEntity(entity);
        readAuthorNameFromEntity(entity);
        readPageIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setAuthorNameInEntity(entity);
        setAuthorIdInEntity(entity);
        setPageIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
