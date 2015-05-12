/* © 2013-2015 Deuterium Labs Limited */
/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.view.HyperlinkedStr;
import com.google.appengine.api.datastore.Entity;

/**
 * 
 */
public class PgLovedNotification extends Notification {

	public static final String LOVER_ID_PROPERTY = "loverId";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TYPE = "PgLovedNotification";

	private String loverId;
	private PageId pgId;

	public PgLovedNotification() {
	}

	/**
	 * @param entity
	 */
	public PgLovedNotification(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	private String getLoverFromEntity(final Entity entity) {
		final String id = (String) entity.getProperty(LOVER_ID_PROPERTY);
		return id;
	}

	/**
	 * @return
	 */
	private String getLoverId() {
		return this.loverId;
	}

	/**
	 * @return
	 */
	private String getLoverName() {
		final String id = this.getLoverId();
		final User lover = new User();
		lover.setId(id);
		lover.read();
		return lover.getDefaultPenName();
	}

	@Override
	public String getMsg() {
		final String lover = this.getLoverName();
		final PageId pgId = this.getPgId();
		return lover + " loves page " + pgId + ".";
	}

	@Override
	public List<HyperlinkedStr> getHyperlinkedMsg() {
		final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

		final HyperlinkedStr nameChunk = new HyperlinkedStr();
		nameChunk.str = this.getLoverName();
		nameChunk.url = "/author?id=" + this.getLoverId();
		msg.add(nameChunk);

		HyperlinkedStr unlinkedChunk = new HyperlinkedStr();
		unlinkedChunk.str = " loves page ";
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

	/**
	 * @param entity
	 * @return
	 */
	private int getPgIdNumFromEntity(final Entity entity) {
		final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
		return num.intValue();
	}

	/**
	 * @param entity
	 * @return
	 */
	private String getPgIdVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	/**
	 * @param entity
	 */
	private void readLoverIdFromEntity(final Entity entity) {
		final String id = getLoverFromEntity(entity);
		this.setLoverId(id);
	}

	/**
	 * @param entity
	 */
	private void readPgIdFromEntity(final Entity entity) {
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
		this.readPgIdFromEntity(entity);
		this.readLoverIdFromEntity(entity);
	}

	/**
	 * @param loverId
	 *            The user who now loves the page.
	 */
	public void setLoverId(final String loverId) {
		this.loverId = loverId;
	}

	/**
	 * @param entity
	 */
	private void setLoverIdInEntity(final Entity entity) {
		final String id = this.getLoverId();
		entity.setProperty(LOVER_ID_PROPERTY, id);
	}

	/**
	 * @param pgId
	 *            The page which has been loved.
	 */
	public void setPgId(final PageId pgId) {
		this.pgId = pgId;
	}

	/**
	 * @param entity
	 */
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
		this.setPgIdInEntity(entity);
		this.setLoverIdInEntity(entity);
		setTypeInEntity(entity);
	}

	private static void setTypeInEntity(final Entity entity) {
		final String type = getType();
		String propertyName = Notification.getTypePropertyName();
		entity.setProperty(propertyName, type);
	}

	public static String getType() {
		return TYPE;
	}
}
