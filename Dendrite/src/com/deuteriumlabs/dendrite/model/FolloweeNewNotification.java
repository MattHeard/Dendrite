/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class FolloweeNewNotification extends Notification {

    public static final String NEW_AUTHOR_ID_PROPERTY = "newAuthorId";

    private static final String NEW_AUTHOR_NAME_PROPERTY = "newAuthorName";
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
    private static final String TITLE_PROPERTY = "title";

    private static String getAuthorIdFromEntity(final DatastoreEntity entity) {
        final String propertyName = NEW_AUTHOR_ID_PROPERTY;
        final String id = (String) entity.getProperty(propertyName);
        return id;
    }

    private static String getAuthorNameFromEntity(
            final DatastoreEntity entity) {
        final String propertyName = NEW_AUTHOR_NAME_PROPERTY;
        final String name = (String) entity.getProperty(propertyName);
        return name;
    }

    private static int getPgIdNumFromEntity(final DatastoreEntity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
    }

    private static String getPgIdVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private static String getTitleFromEntity(final DatastoreEntity entity) {
        final String propertyName = TITLE_PROPERTY;
        final String title = (String) entity.getProperty(propertyName);
        return title;
    }

    private String authorId;
    private String authorName;
    private PageId pgId;
    private String title;

    public FolloweeNewNotification() {
    }

    public FolloweeNewNotification(final DatastoreEntity entity) {
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
        final String title = getTitle();
        unlinkedChunk.str = " started the story '" + title + "' at page ";
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
        final String name = getAuthorName();
        final String title = getTitle();
        final PageId pgId = getPgId();
        final String msg;
        msg = name + " started the story '" + title + "' at page " + pgId + ".";
        return msg;
    }

    public void setAuthorId(final String id) {
        authorId = id;
    }

    public void setAuthorName(final String name) {
        authorName = name;
    }

    public void setPgId(final PageId id) {
        pgId = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    private String getAuthorId() {
        return authorId;
    }

    private String getAuthorName() {
        return authorName;
    }

    private PageId getPgId() {
        return pgId;
    }

    private String getTitle() {
        return title;
    }

    private void readAuthorIdFromEntity(final DatastoreEntity entity) {
        final String id = getAuthorIdFromEntity(entity);
        setAuthorId(id);
    }

    private void readAuthorNameFromEntity(final DatastoreEntity entity) {
        final String name = getAuthorNameFromEntity(entity);
        setAuthorName(name);
    }

    private void readPgIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        setPgId(id);
    }

    private void readTitleFromEntity(final DatastoreEntity entity) {
        final String title = getTitleFromEntity(entity);
        setTitle(title);
    }

    private void setAuthorIdInEntity(final DatastoreEntity entity) {
        final String id = getAuthorId();
        entity.setProperty(NEW_AUTHOR_ID_PROPERTY, id);
    }

    private void setAuthorNameInEntity(final DatastoreEntity entity) {
        final String name = getAuthorName();
        entity.setProperty(NEW_AUTHOR_NAME_PROPERTY, name);
    }

    private void setPgIdInEntity(final DatastoreEntity entity) {
        final PageId id = getPgId();
        final int num = id.getNumber();
        entity.setProperty(PG_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PG_ID_VERSION_PROPERTY, version);
    }

    private void setTitleInEntity(final DatastoreEntity entity) {
        final String title = getTitle();
        entity.setProperty(TITLE_PROPERTY, title);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readAuthorIdFromEntity(entity);
        readAuthorNameFromEntity(entity);
        readPgIdFromEntity(entity);
        readTitleFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setAuthorIdInEntity(entity);
        setAuthorNameInEntity(entity);
        setPgIdInEntity(entity);
        setTitleInEntity(entity);
        setTypeInEntity(entity);
    }
}
