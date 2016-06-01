/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class FollowerWriteNotification extends Notification {

    private static final String AUTHOR_ID_PROPERTY = "authorId";
    private static final String AUTHOR_NAME_PROPERTY = "authorName";
    private static final String PG_ID_NUM_PROPERTY = "pageIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pageIdVersion";

    private static String getAuthorIdFromEntity(final DatastoreEntity entity) {
        final String propertyName = AUTHOR_ID_PROPERTY;
        final String id = (String) entity.getProperty(propertyName);
        return id;
    }

    private static String getAuthorNameFromEntity(
            final DatastoreEntity entity) {
        final String propertyName = AUTHOR_NAME_PROPERTY;
        final String name = (String) entity.getProperty(propertyName);
        return name;
    }

    private static int getPageIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private static String getPageIdVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private String authorId;
    private String authorName;
    private PageId pageId;

    public FollowerWriteNotification() {
    }

    public FollowerWriteNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = getAuthorName();
        nameChunk.url = "/author?id=" + getAuthorId();
        msg.add(nameChunk);

        HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " continued a story by writing page ";
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
        final String name = getAuthorName();
        final PageId pageId = getPageId();
        return name + " continued a story by writing page " + pageId + ".";
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

    private String getAuthorId() {
        return authorId;
    }

    private String getAuthorName() {
        return authorName;
    }

    private PageId getPageId() {
        return pageId;
    }

    private void readAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getAuthorIdFromEntity(entity);
        setAuthorId(id);
    }

    private void readAuthorNameFromEntity(final DatastoreEntity entity) {
        final String name = getAuthorNameFromEntity(entity);
        setAuthorName(name);
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
        final String id = getAuthorId();
        entity.setProperty(AUTHOR_ID_PROPERTY, id);
    }

    private void setAuthorNameInEntity(final DatastoreEntity entity) {
        final String name = getAuthorName();
        entity.setProperty(AUTHOR_NAME_PROPERTY, name);
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
