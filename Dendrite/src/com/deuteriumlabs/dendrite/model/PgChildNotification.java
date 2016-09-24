/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class PgChildNotification extends Notification {

    private static final String CHILD_AUTHOR_ID_PROPERTY = "childAuthorId";
    private static final String PAGE_ID_NUM_PROPERTY = "pageIdNum";
    private static final String PAGE_ID_VERSION_PROPERTY = "pageIdVersion";

    private String childAuthorId;
    private PageId pageId;

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
        final String name = getChildAuthorName();
        final PageId pageId = getPageId();
        final String msg;
        msg = name + " extended one of your pages and wrote page " + pageId + ".";
        return msg;
    }

    public void setChildAuthorId(final String id) {
        childAuthorId = id;
    }

    public void setPageId(final PageId id) {
        pageId = id;
    }

    private String getChildAuthorId() {
        return childAuthorId;
    }

    private String getChildAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = (String) entity.getProperty(CHILD_AUTHOR_ID_PROPERTY);
        return id;
    }

    private String getChildAuthorName() {
        final String id = getChildAuthorId();
        if (id != null) {
            final User author = new User();
            author.setId(id);
            author.read();
            final String name = author.getDefaultPenName();
            return name;
        } else {
            return "???";
        }
    }

    private PageId getPageId() {
        return pageId;
    }

    private int getPageIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PAGE_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private String getPageIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(PAGE_ID_VERSION_PROPERTY);
    }

    private void readChildAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getChildAuthorIdFromEntity(entity);
        setChildAuthorId(id);
    }

    private void readPageIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPageIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPageIdVersionFromEntity(entity);
        id.setVersion(version);
        setPageId(id);
    }

    private void setChildAuthorIdInEntity(final DatastoreEntity entity) {
        final String id = getChildAuthorId();
        entity.setProperty(CHILD_AUTHOR_ID_PROPERTY, id);
    }

    private void setPageIdInEntity(final DatastoreEntity entity) {
        final PageId id = getPageId();
        final int num = id.getNumber();
        entity.setProperty(PAGE_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PAGE_ID_VERSION_PROPERTY, version);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readPageIdFromEntity(entity);
        readChildAuthorIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPageIdInEntity(entity);
        setChildAuthorIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
