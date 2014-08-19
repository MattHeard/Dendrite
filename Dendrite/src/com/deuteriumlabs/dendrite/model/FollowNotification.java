package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;

public class FollowNotification extends Notification {

	static final String FOLLOWER_ID_PROPERTY = "followerId";

	private static final String TYPE = "FollowNotification";
	
	private String followerId;

	public void setFollowerId(final String followerId) {
		this.followerId = followerId;
	}
	
	public FollowNotification() {}
	
	public FollowNotification(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	@Override
	public String getMsg() {
		final String follower = this.getFollowerName();
		return follower + " is now following you.";
	}

	private String getFollowerName() {
		final String id = this.getFollowerId();
		final User follower = new User();
		follower.setId(id);
		follower.read();
		return follower.getDefaultPenName();
	}

	private String getFollowerId() {
		return this.followerId;
	}

	@Override
	void readPropertiesFromEntity(final Entity entity) {
		super.readPropertiesFromEntity(entity);
		this.readFollowerIdFromEntity(entity);
	}

	private void readFollowerIdFromEntity(final Entity entity) {
		final String id = getFollowerIdFromEntity(entity);
		this.setFollowerId(id);
	}

	private String getFollowerIdFromEntity(final Entity entity) {
		final String id = (String) entity.getProperty(FOLLOWER_ID_PROPERTY);
		return id;
	}

	@Override
	void setPropertiesInEntity(final Entity entity) {
		super.setPropertiesInEntity(entity);
		this.setFollowerIdInEntity(entity);
		setTypeInEntity(entity);
	}

    private static void setTypeInEntity(final Entity entity) {
    	final String type = getType();
    	String propertyName = Notification.getTypePropertyName();
		entity.setProperty(propertyName, type);
	}

	private void setFollowerIdInEntity(final Entity entity) {
		final String id = this.getFollowerId();
		entity.setProperty(FOLLOWER_ID_PROPERTY, id);
	}
    
    public static String getType() {
    	return TYPE;
    }
}
