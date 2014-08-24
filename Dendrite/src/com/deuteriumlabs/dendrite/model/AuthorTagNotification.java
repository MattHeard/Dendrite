package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.view.HyperlinkedStr;
import com.google.appengine.api.datastore.Entity;

public class AuthorTagNotification extends Notification {

	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TAG_PROPERTY = "tag";
	private static final String TAGGER_ID_PROPERTY = "taggerId";
	private static final String TAGGER_NAME_PROPERTY = "taggerName";
	private static final String TYPE = "AuthorTagNotification";

	public static String getType() {
		return TYPE;
	}

	private static void setTypeInEntity(final Entity entity) {
		entity.setProperty(Notification.getTypePropertyName(), getType());
	}

	private PageId pgId;
	private String tag;
	private String taggerId;
	private String taggerName;

	public AuthorTagNotification() {
	}

	public AuthorTagNotification(final Entity entity) {
		this.readPropertiesFromEntity(entity);
	}

	@Override
	public String getMsg() {
		final PageId pgId = this.getPgId();
		final String tag = this.getTag();
		final String taggerId = this.getTaggerId();
		final String msg;
		if (taggerId != null) {
			final String name = this.getTaggerName();
			msg = name + " tagged your page " + pgId + " as " + tag + ".";
		} else {
			msg = "Someone tagged your page " + pgId + " as " + tag + ".";
		}
		return msg;
	}

	@Override
	public List<HyperlinkedStr> getHyperlinkedMsg() {
		final PageId pgId = this.getPgId();
		final String tag = this.getTag();
		final String taggerId = this.getTaggerId();
		final List<HyperlinkedStr> msg = new ArrayList<HyperlinkedStr>();

		if (taggerId != null) {
			final HyperlinkedStr nameChunk = new HyperlinkedStr();
			nameChunk.str = this.getTaggerName();
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

	private PageId getPgId() {
		return this.pgId;
	}

	private int getPgIdNumFromEntity(final Entity entity) {
		return ((Long) entity.getProperty(PG_ID_NUM_PROPERTY)).intValue();
	}

	private String getPgIdVersionFromEntity(final Entity entity) {
		return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	private String getTag() {
		return this.tag;
	}

	private String getTagFromEntity(final Entity entity) {
		return ((String) entity.getProperty(TAG_PROPERTY)).toUpperCase();
	}

	private String getTaggerId() {
		return this.taggerId;
	}

	private String getTaggerIdFromEntity(final Entity entity) {
		return (String) entity.getProperty(TAGGER_ID_PROPERTY);
	}

	private String getTaggerName() {
		return this.taggerName;
	}

	private String getTaggerNameFromEntity(final Entity entity) {
		return (String) entity.getProperty(TAGGER_NAME_PROPERTY);
	}

	private void readPgIdFromEntity(final Entity entity) {
		final PageId id = new PageId();
		id.setNumber(getPgIdNumFromEntity(entity));
		id.setVersion(getPgIdVersionFromEntity(entity));
		this.setPgId(id);
	}

	@Override
	void readPropertiesFromEntity(final Entity entity) {
		super.readPropertiesFromEntity(entity);
		this.readPgIdFromEntity(entity);
		this.readTagFromEntity(entity);
		this.readTaggerIdFromEntity(entity);
		this.readTaggerNameFromEntity(entity);
	}

	private void readTagFromEntity(final Entity entity) {
		this.setTag(getTagFromEntity(entity));
	}

	private void readTaggerIdFromEntity(final Entity entity) {
		this.setTaggerId(getTaggerIdFromEntity(entity));
	}

	private void readTaggerNameFromEntity(final Entity entity) {
		this.setTaggerName(getTaggerNameFromEntity(entity));
	}

	public void setPgId(final PageId id) {
		this.pgId = id;
	}

	private void setPgIdInEntity(final Entity entity) {
		final PageId id = this.getPgId();
		entity.setProperty(PG_ID_NUM_PROPERTY, id.getNumber());
		entity.setProperty(PG_ID_VERSION_PROPERTY, id.getVersion());
	}

	@Override
	void setPropertiesInEntity(final Entity entity) {
		super.setPropertiesInEntity(entity);
		this.setPgIdInEntity(entity);
		this.setTagInEntity(entity);
		if (this.getTaggerId() != null) {
			this.setTaggerIdInEntity(entity);
			this.setTaggerNameInEntity(entity);
		}
		setTypeInEntity(entity);
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}

	public void setTaggerId(final String id) {
		this.taggerId = id;
	}

	private void setTaggerIdInEntity(final Entity entity) {
		entity.setProperty(TAGGER_ID_PROPERTY, this.getTaggerId());
	}

	public void setTaggerName(final String name) {
		this.taggerName = name;
	}

	private void setTaggerNameInEntity(final Entity entity) {
		entity.setProperty(TAGGER_NAME_PROPERTY, this.getTaggerName());
	}

	private void setTagInEntity(final Entity entity) {
		entity.setProperty(TAG_PROPERTY, this.getTag());
	}
}
