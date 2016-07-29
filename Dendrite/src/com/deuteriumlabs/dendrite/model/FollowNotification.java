/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

public class FollowNotification extends Notification {

    private static final String FOLLOWER_ID_PROPERTY = "followerId";

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
        nameChunk.url = "/author?id=" + followerId;
        msg.add(nameChunk);

        final HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
        unlinkedChunk.str = " is now following you.";
        msg.add(unlinkedChunk);

        return msg;
    }

    @Override
    public String getMsg() {
        return getFollowerName() + " is now following you.";
    }

    public void setFollowerId(final String followerId) {
        this.followerId = followerId;
    }

    private String getFollowerIdFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(FOLLOWER_ID_PROPERTY);
    }

    private String getFollowerName() {
        final User follower = new User();
        follower.setId(followerId);
        follower.read();
        return follower.getDefaultPenName();
    }

    private void readFollowerIdFromEntity(final DatastoreEntity entity) {
        setFollowerId(getFollowerIdFromEntity(entity));
    }

    private void setFollowerIdInEntity(final DatastoreEntity entity) {
        entity.setProperty(FOLLOWER_ID_PROPERTY, followerId);
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
