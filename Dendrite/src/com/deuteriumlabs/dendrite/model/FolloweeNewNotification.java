package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;

public class FolloweeNewNotification extends Notification {

	public static final String NEW_AUTHOR_ID_PROPERTY = "newAuthorId";

	private static final String NEW_AUTHOR_NAME_PROPERTY = "newAuthorName";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TITLE_PROPERTY = "title";
	private static final String TYPE = "FolloweeNewNotification";

	private static String getAuthorIdFromEntity(final Entity entity) {
		String propertyName = NEW_AUTHOR_ID_PROPERTY;
		final String id = (String) entity.getProperty(propertyName);
		return id;
	}

	private static String getAuthorNameFromEntity(final Entity entity) {
		String propertyName = NEW_AUTHOR_NAME_PROPERTY;
		final String name = (String) entity.getProperty(propertyName);
		return name;
	}

	private static int getPgIdNumFromEntity(final Entity entity) {
		final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
		return num.intValue();
	}

	private static String getPgIdVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	private static String getTitleFromEntity(final Entity entity) {
		String propertyName = TITLE_PROPERTY;
		final String title = (String) entity.getProperty(propertyName);
		return title;
	}

	public static String getType() {
		return TYPE;
	}

	private static void setTypeInEntity(final Entity entity) {
		final String type = getType();
		String propertyName = Notification.getTypePropertyName();
		entity.setProperty(propertyName, type);
	}

	private String authorId;
	private String authorName;
	private PageId pgId;
	private String title;

	public FolloweeNewNotification() {
	}

	public FolloweeNewNotification(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	private String getAuthorId() {
		return this.authorId;
	}

	private String getAuthorName() {
		return this.authorName;
	}

	@Override
	public String getMsg() {
		final String name = this.getAuthorName();
		final String title = this.getTitle();
		final PageId pgId = this.getPgId();
		final String msg;
		msg = name + " started the story '" + title + "' at page " + pgId + ".";
		return msg;
	}

	private PageId getPgId() {
		return this.pgId;
	}

	private String getTitle() {
		return this.title;
	}

	private void readAuthorIdFromEntity(final Entity entity) {
		final String id = getAuthorIdFromEntity(entity);
		this.setAuthorId(id);
	}

	private void readAuthorNameFromEntity(final Entity entity) {
		final String name = getAuthorNameFromEntity(entity);
		this.setAuthorName(name);
	}

	private void readPgIdFromEntity(Entity entity) {
		final PageId id = new PageId();
		final int number = getPgIdNumFromEntity(entity);
		id.setNumber(number);
		final String version = getPgIdVersionFromEntity(entity);
		id.setVersion(version);
		this.setPgId(id);
	}

	@Override
	void readPropertiesFromEntity(final Entity entity) {
		super.readPropertiesFromEntity(entity);
		this.readAuthorIdFromEntity(entity);
		this.readAuthorNameFromEntity(entity);
		this.readPgIdFromEntity(entity);
		this.readTitleFromEntity(entity);
	}

	private void readTitleFromEntity(final Entity entity) {
		final String title = getTitleFromEntity(entity);
		this.setTitle(title);
	}

	public void setAuthorId(final String id) {
		this.authorId = id;
	}

	private void setAuthorIdInEntity(final Entity entity) {
		final String id = this.getAuthorId();
		entity.setProperty(NEW_AUTHOR_ID_PROPERTY, id);
	}

	public void setAuthorName(final String name) {
		this.authorName = name;
	}

	private void setAuthorNameInEntity(final Entity entity) {
		final String name = this.getAuthorName();
		entity.setProperty(NEW_AUTHOR_NAME_PROPERTY, name);
	}

	public void setPgId(final PageId id) {
		this.pgId = id;
	}

	private void setPgIdInEntity(final Entity entity) {
		final PageId id = this.getPgId();
		final int num = id.getNumber();
		entity.setProperty(PG_ID_NUM_PROPERTY, num);
		final String version = id.getVersion();
		entity.setProperty(PG_ID_VERSION_PROPERTY, version);
	}

	@Override
	void setPropertiesInEntity(final Entity entity) {
		super.setPropertiesInEntity(entity);
		this.setAuthorIdInEntity(entity);
		this.setAuthorNameInEntity(entity);
		this.setPgIdInEntity(entity);
		this.setTitleInEntity(entity);
		setTypeInEntity(entity);
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	private void setTitleInEntity(final Entity entity) {
		final String title = this.getTitle();
		entity.setProperty(TITLE_PROPERTY, title);
	}
}
