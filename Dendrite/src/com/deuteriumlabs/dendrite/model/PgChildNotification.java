/* © 2013-2015 Deuterium Labs Limited */
/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.view.HyperlinkedStr;

/**
 * 
 */
public class PgChildNotification extends Notification {

	private static final String CHILD_AUTHOR_ID_PROPERTY = "childAuthorId";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";

	private String childAuthorId;
	private PageId pgId;

	public PgChildNotification() {
	}

	public PgChildNotification(final DatastoreEntity entity) {
		this.readPropertiesFromEntity(entity);
	}

	/**
	 * @return
	 */
	private String getChildAuthorId() {
		return this.childAuthorId;
	}

	private String getChildAuthorIdFromEntity(final DatastoreEntity entity) {
		final String id = (String) entity.getProperty(CHILD_AUTHOR_ID_PROPERTY);
		return id;
	}

	/**
	 * @return
	 */
	private String getChildAuthorName() {
		final String id = this.getChildAuthorId();
		final User author = new User();
		author.setId(id);
		author.read();
		final String name = author.getDefaultPenName();
		return name;
	}

	@Override
	public String getMsg() {
		final String name = this.getChildAuthorName();
		final PageId pgId = this.getPgId();
		final String msg;
		msg = name + " extended one of your pages and wrote page " + pgId + ".";
		return msg;
	}

	@Override
	public List<HyperlinkedStr> getHyperlinkedMsg() {
		final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

		final HyperlinkedStr nameChunk = new HyperlinkedStr();
		nameChunk.str = this.getChildAuthorName();
		nameChunk.url = "/author?id=" + this.getChildAuthorId();
		msg.add(nameChunk);

		HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
		unlinkedChunk.str = " extended one of your pages and wrote page ";
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

	/**
	 * @return
	 */
	private PageId getPgId() {
		return this.pgId;
	}

	private int getPgIdNumFromEntity(final DatastoreEntity entity) {
		final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
		return num.intValue();
	}

	private String getPgIdVersionFromEntity(final DatastoreEntity entity) {
		return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	private void readChildAuthorIdFromEntity(final DatastoreEntity entity) {
		final String id = getChildAuthorIdFromEntity(entity);
		this.setChildAuthorId(id);
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
		this.readPgIdFromEntity(entity);
		this.readChildAuthorIdFromEntity(entity);
	}

	/**
	 * @param id
	 */
	public void setChildAuthorId(final String id) {
		this.childAuthorId = id;
	}

	private void setChildAuthorIdInEntity(final DatastoreEntity entity) {
		final String id = this.getChildAuthorId();
		entity.setProperty(CHILD_AUTHOR_ID_PROPERTY, id);
	}

	/**
	 * @param id
	 */
	public void setPgId(final PageId id) {
		this.pgId = id;
	}

	/**
	 * @param entity
	 */
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
		this.setPgIdInEntity(entity);
		this.setChildAuthorIdInEntity(entity);
		this.setTypeInEntity(entity);
	}
}
