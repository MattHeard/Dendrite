/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class FollowNotification extends Notification {

    static final String FOLLOWER_ID_PROPERTY = "followerId";

    private String followerId;

    public FollowNotification() {
    }

    public FollowNotification(final DatastoreEntity entity) {
        readPropertiesFromEntity(entity);
    }

    @Override
    public List<HyperlinkedStr> getHyperlinkedMsg() {
        final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

        final HyperlinkedStr nameChunk = new HyperlinkedStr();
        nameChunk.str = getFollowerName();
        nameChunk.url = "/author?id=" + getFollowerId();
        msg.add(nameChunk);

        final HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " is now following you.";
        msg.add(unlinkedChunk);

        return msg;
    }

    @Override
    public String getMsg() {
        final String follower = getFollowerName();
        return follower + " is now following you.";
    }

    public void setFollowerId(final String followerId) {
        this.followerId = followerId;
    }

    private String getFollowerId() {
        return followerId;
    }

    private String getFollowerIdFromEntity(final DatastoreEntity entity) {
        final String id = (String) entity.getProperty(FOLLOWER_ID_PROPERTY);
        return id;
    }

    private String getFollowerName() {
        final String id = getFollowerId();
        final User follower = new User();
        follower.setId(id);
        follower.read();
        return follower.getDefaultPenName();
    }

    private void readFollowerIdFromEntity(final DatastoreEntity entity) {
        final String id = getFollowerIdFromEntity(entity);
        setFollowerId(id);
    }

    private void setFollowerIdInEntity(final DatastoreEntity entity) {
        final String id = getFollowerId();
        entity.setProperty(FOLLOWER_ID_PROPERTY, id);
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        super.readPropertiesFromEntity(entity);
        readFollowerIdFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        super.setPropertiesInEntity(entity);
        setFollowerIdInEntity(entity);
        setTypeInEntity(entity);
    }
}
