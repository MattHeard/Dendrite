/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.view.HyperlinkedStr;
import com.google.appengine.api.datastore.Entity;

public class FolloweeNewNotification extends Notification {

	public static final String NEW_AUTHOR_ID_PROPERTY = "newAuthorId";

	private static final String NEW_AUTHOR_NAME_PROPERTY = "newAuthorName";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TITLE_PROPERTY = "title";
	private static final String TYPE = "FolloweeNewNotification";

	private static String getAuthorIdFromEntity(final DatastoreEntity entity) {
		String propertyName = NEW_AUTHOR_ID_PROPERTY;
		final String id = (String) entity.getProperty(propertyName);
		return id;
	}

	private static String getAuthorNameFromEntity(final DatastoreEntity entity) {
		String propertyName = NEW_AUTHOR_NAME_PROPERTY;
		final String name = (String) entity.getProperty(propertyName);
		return name;
	}

	private static int getPgIdNumFromEntity(final DatastoreEntity entity) {
		final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
		return num.intValue();
	}

	private static String getPgIdVersionFromEntity(final DatastoreEntity entity) {
		return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	private static String getTitleFromEntity(final DatastoreEntity entity) {
		String propertyName = TITLE_PROPERTY;
		final String title = (String) entity.getProperty(propertyName);
		return title;
	}

	public static String getType() {
		return TYPE;
	}

	private static void setTypeInEntity(final DatastoreEntity entity) {
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

	public FolloweeNewNotification(final DatastoreEntity entity) {
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

	@Override
	public List<HyperlinkedStr> getHyperlinkedMsg() {
		final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

		final HyperlinkedStr nameChunk = new HyperlinkedStr();
		nameChunk.str = this.getAuthorName();
		nameChunk.url = "/author?id=" + this.getAuthorId();
		msg.add(nameChunk);

		HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
		final String title = this.getTitle();
		unlinkedChunk.str = " started the story '" + title + "' at page ";
		msg.add(unlinkedChunk);

		final HyperlinkedStr pgIdChunk = new HyperlinkedStr();
		final PageId pgId = this.getPgId();
		pgIdChunk.str = pgId.toString();
		pgIdChunk.url = "/read?p=" + pgId;
		msg.add(pgIdChunk);

		unlinkedChunk = new HyperlinkedStr();
		unlinkedChunk.str = ".";
		msg.add(unlinkedChunk);

		return msg;
	}

	private PageId getPgId() {
		return this.pgId;
	}

	private String getTitle() {
		return this.title;
	}

	private void readAuthorIdFromEntity(final DatastoreEntity entity) {
		final String id = getAuthorIdFromEntity(entity);
		this.setAuthorId(id);
	}

	private void readAuthorNameFromEntity(final DatastoreEntity entity) {
		final String name = getAuthorNameFromEntity(entity);
		this.setAuthorName(name);
	}

	private void readPgIdFromEntity(final DatastoreEntity entity) {
		final PageId id = new PageId();
		final int number = getPgIdNumFromEntity(entity);
		id.setNumber(number);
		final String version = getPgIdVersionFromEntity(entity);
		id.setVersion(version);
		this.setPgId(id);
	}

	@Override
	void readPropertiesFromEntity(final DatastoreEntity entity) {
		super.readPropertiesFromEntity(entity);
		this.readAuthorIdFromEntity(entity);
		this.readAuthorNameFromEntity(entity);
		this.readPgIdFromEntity(entity);
		this.readTitleFromEntity(entity);
	}

	private void readTitleFromEntity(final DatastoreEntity entity) {
		final String title = getTitleFromEntity(entity);
		this.setTitle(title);
	}

	public void setAuthorId(final String id) {
		this.authorId = id;
	}

	private void setAuthorIdInEntity(final DatastoreEntity entity) {
		final String id = this.getAuthorId();
		entity.setProperty(NEW_AUTHOR_ID_PROPERTY, id);
	}

	public void setAuthorName(final String name) {
		this.authorName = name;
	}

	private void setAuthorNameInEntity(final DatastoreEntity entity) {
		final String name = this.getAuthorName();
		entity.setProperty(NEW_AUTHOR_NAME_PROPERTY, name);
	}

	public void setPgId(final PageId id) {
		this.pgId = id;
	}

	private void setPgIdInEntity(final DatastoreEntity entity) {
		final PageId id = this.getPgId();
		final int num = id.getNumber();
		entity.setProperty(PG_ID_NUM_PROPERTY, num);
		final String version = id.getVersion();
		entity.setProperty(PG_ID_VERSION_PROPERTY, version);
	}

	@Override
	void setPropertiesInEntity(final DatastoreEntity entity) {
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

	private void setTitleInEntity(final DatastoreEntity entity) {
		final String title = this.getTitle();
		entity.setProperty(TITLE_PROPERTY, title);
	}
}
