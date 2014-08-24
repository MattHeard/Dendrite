package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.view.HyperlinkedStr;
import com.google.appengine.api.datastore.Entity;

public class FollowerRewriteNotification extends Notification {

	private static final String AUTHOR_ID_PROPERTY = "authorId";
	private static final String AUTHOR_NAME_PROPERTY = "authorName";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TYPE = "FollowerRewriteNotification";

	public static String getType() {
		return TYPE;
	}

	private static String getAuthorIdFromEntity(final Entity entity) {
		String propertyName = AUTHOR_ID_PROPERTY;
		final String id = (String) entity.getProperty(propertyName);
		return id;
	}

	private static String getAuthorNameFromEntity(final Entity entity) {
		String propertyName = AUTHOR_NAME_PROPERTY;
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

	private String authorId;
	private String authorName;
	private PageId pgId;

	public FollowerRewriteNotification() {
	}

	public FollowerRewriteNotification(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	private String getAuthorName() {
		return this.authorName;
	}

	@Override
	public String getMsg() {
		final String name = this.getAuthorName();
		final PageId pgId = this.getPgId();
		return name + " rewrote a page at page " + pgId + ".";
	}

	@Override
	public List<HyperlinkedStr> getHyperlinkedMsg() {
		final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

		final HyperlinkedStr nameChunk = new HyperlinkedStr();
		nameChunk.str = this.getAuthorName();
		nameChunk.url = "/author?id=" + this.getAuthorId();
		msg.add(nameChunk);

		HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
		unlinkedChunk.str = " rewrote a page at page ";
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
	}

	public void setAuthorId(final String id) {
		this.authorId = id;
	}

	public void setAuthorName(final String name) {
		this.authorName = name;
	}

	public void setPgId(final PageId id) {
		this.pgId = id;
	}

	@Override
	void setPropertiesInEntity(final Entity entity) {
		super.setPropertiesInEntity(entity);
		this.setAuthorNameInEntity(entity);
		this.setAuthorIdInEntity(entity);
		this.setPgIdInEntity(entity);
		setTypeInEntity(entity);
	}

	private static void setTypeInEntity(final Entity entity) {
		final String type = getType();
		String propertyName = Notification.getTypePropertyName();
		entity.setProperty(propertyName, type);
	}

	private void setAuthorIdInEntity(final Entity entity) {
		final String id = this.getAuthorId();
		entity.setProperty(AUTHOR_ID_PROPERTY, id);
	}

	private String getAuthorId() {
		return this.authorId;
	}

	private void setAuthorNameInEntity(final Entity entity) {
		final String name = this.getAuthorName();
		entity.setProperty(AUTHOR_NAME_PROPERTY, name);
	}

	private void setPgIdInEntity(final Entity entity) {
		final PageId id = this.getPgId();
		final int num = id.getNumber();
		entity.setProperty(PG_ID_NUM_PROPERTY, num);
		final String version = id.getVersion();
		entity.setProperty(PG_ID_VERSION_PROPERTY, version);
	}
}
