/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class AuthorTagNotification extends Notification {
    private static final String PG_ID_NUM_PROPERTY = "pageIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pageIdVersion";
    private static final String TAG_PROPERTY = "tag";
    private static final String TAGGER_ID_PROPERTY = "taggerId";
    private static final String TAGGER_NAME_PROPERTY = "taggerName";

    private PageId pageId;
    private String tag;
    private String taggerId;
    private String taggerName;

    public AuthorTagNotification() { }

    public AuthorTagNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        if (taggerId != null) {
            final HyperlinkedStr nameChunk = new HyperlinkedStr();
            nameChunk.str = taggerName;
            nameChunk.url = "/author?id=" + taggerId;
            msg.add(nameChunk);

            HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = " tagged your page ";
            msg.add(unlinkedChunk);

            final HyperlinkedStr pageIdChunk = new HyperlinkedStr();
            pageIdChunk.str = pageId.toString();
            pageIdChunk.url = "/read?p=" + pageId;
            msg.add(pageIdChunk);

            unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = " as " + tag + ".";
            msg.add(unlinkedChunk);
        } else {
            HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = "Someone tagged your page ";
            msg.add(unlinkedChunk);

            final HyperlinkedStr pageIdChunk = new HyperlinkedStr();
            pageIdChunk.str = pageId.toString();
            pageIdChunk.url = "/read?p=" + pageId;
            msg.add(pageIdChunk);

            unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = " as " + tag + ".";
            msg.add(unlinkedChunk);
        }

        return msg;
    }

    @Override
    public String getMsg() {
        final String msg;
        if (taggerId != null) {
            final String name = taggerName;
            msg = name + " tagged your page " + pageId + " as " + tag + ".";
        } else {
            msg = "Someone tagged your page " + pageId + " as " + tag + ".";
        }
        return msg;
    }

    public void setPageId(final PageId id) {
        pageId = id;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public void setTaggerId(final String id) {
        taggerId = id;
    }

    public void setTaggerName(final String name) {
        taggerName = name;
    }

    private int getPageIdNumFromEntity(final DatastoreEntity entity) {
        return ((Long) entity.getProperty(PG_ID_NUM_PROPERTY)).intValue();
    }

    private String getPageIdVersionFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
    }

    private String getTagFromEntity(final DatastoreEntity entity) {
        return ((String) entity.getProperty(TAG_PROPERTY)).toUpperCase();
    }

    private String getTaggerIdFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(TAGGER_ID_PROPERTY);
    }

    private String getTaggerNameFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(TAGGER_NAME_PROPERTY);
    }

    private void readPageIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        id.setNumber(getPageIdNumFromEntity(entity));
        id.setVersion(getPageIdVersionFromEntity(entity));
        setPageId(id);
    }

    private void readTagFromEntity(final DatastoreEntity entity) {
        setTag(getTagFromEntity(entity));
    }

    private void readTaggerIdFromEntity(final DatastoreEntity entity) {
        setTaggerId(getTaggerIdFromEntity(entity));
    }

    private void readTaggerNameFromEntity(final DatastoreEntity entity) {
        setTaggerName(getTaggerNameFromEntity(entity));
    }

    private void setPageIdInEntity(final DatastoreEntity entity) {
        final PageId id = pageId;
        entity.setProperty(PG_ID_NUM_PROPERTY, id.getNumber());
        entity.setProperty(PG_ID_VERSION_PROPERTY, id.getVersion());
    }

    private void setTaggerIdInEntity(final DatastoreEntity entity) {
        entity.setProperty(TAGGER_ID_PROPERTY, taggerId);
    }

    private void setTaggerNameInEntity(final DatastoreEntity entity) {
        entity.setProperty(TAGGER_NAME_PROPERTY, taggerName);
    }

    private void setTagInEntity(final DatastoreEntity entity) {
        entity.setProperty(TAG_PROPERTY, tag);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readPageIdFromEntity(entity);
        readTagFromEntity(entity);
        readTaggerIdFromEntity(entity);
        readTaggerNameFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPageIdInEntity(entity);
        setTagInEntity(entity);
        if (taggerId != null) {
            setTaggerIdInEntity(entity);
            setTaggerNameInEntity(entity);
        }
        setTypeInEntity(entity);
    }
}
