/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class AuthorTagNotification extends Notification {
    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
    private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
    private static final String TAG_PROPERTY = "tag";
    private static final String TAGGER_ID_PROPERTY = "taggerId";
    private static final String TAGGER_NAME_PROPERTY = "taggerName";

    private PageId pgId;
    private String tag;
    private String taggerId;
    private String taggerName;

    public AuthorTagNotification() {
    }

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

            final HyperlinkedStr pgIdChunk = new HyperlinkedStr();
            pgIdChunk.str = pgId.toString();
            pgIdChunk.url = "/read?p=" + pgId;
            msg.add(pgIdChunk);

            unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = " as " + tag + ".";
            msg.add(unlinkedChunk);
        } else {
            HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
            unlinkedChunk.str = "Someone tagged your page ";
            msg.add(unlinkedChunk);

            final HyperlinkedStr pgIdChunk = new HyperlinkedStr();
            pgIdChunk.str = pgId.toString();
            pgIdChunk.url = "/read?p=" + pgId;
            msg.add(pgIdChunk);

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
            msg = name + " tagged your page " + pgId + " as " + tag + ".";
        } else {
            msg = "Someone tagged your page " + pgId + " as " + tag + ".";
        }
        return msg;
    }

    public void setPgId(final PageId id) {
        pgId = id;
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

    private int getPgIdNumFromEntity(final DatastoreEntity entity) {
        return ((Long) entity.getProperty(PG_ID_NUM_PROPERTY)).intValue();
    }

    private String getPgIdVersionFromEntity(final DatastoreEntity entity) {
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

    private void readPgIdFromEntity(final DatastoreEntity entity) {
        final PageId id = new PageId();
        id.setNumber(getPgIdNumFromEntity(entity));
        id.setVersion(getPgIdVersionFromEntity(entity));
        setPgId(id);
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

    private void setPgIdInEntity(final DatastoreEntity entity) {
        final PageId id = pgId;
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
        readPgIdFromEntity(entity);
        readTagFromEntity(entity);
        readTaggerIdFromEntity(entity);
        readTaggerNameFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setPgIdInEntity(entity);
        setTagInEntity(entity);
        if (taggerId != null) {
            setTaggerIdInEntity(entity);
            setTaggerNameInEntity(entity);
        }
        setTypeInEntity(entity);
    }
}
