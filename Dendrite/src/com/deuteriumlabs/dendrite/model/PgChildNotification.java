/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;


/**
 * 
 */
public class PgChildNotification extends Notification {

    public static final String CHILD_AUTHOR_ID_PROPERTY = "childAuthorId";
	private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	private static final String TYPE = "PgChildNotification";
	
	private String childAuthorId;
    private PageId pgId;
    
    public PgChildNotification() {}
    
    public PgChildNotification(final Entity entity) {
    	this.readPropertiesFromEntity(entity);
	}

	/**
     * @return
     */
    private String getChildAuthorId() {
        return this.childAuthorId;
    }

    private String getChildAuthorIdFromEntity(final Entity entity) {
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

    /**
     * @return
     */
    private PageId getPgId() {
        return this.pgId;
    }

    private int getPgIdNumFromEntity(final Entity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
	}

	private String getPgIdVersionFromEntity(final Entity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}

	private void readChildAuthorIdFromEntity(final Entity entity) {
		final String id = getChildAuthorIdFromEntity(entity);
		this.setChildAuthorId(id);
	}

	private void readPgIdFromEntity(final Entity entity) {
        final PageId id = new PageId();
        final int number = getPgIdNumFromEntity(entity);
        id.setNumber(number);
        final String version = getPgIdVersionFromEntity(entity);
        id.setVersion(version);
        this.setPgId(id);
	}

	@Override
	void readPropertiesFromEntity(Entity entity) {
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

	private void setChildAuthorIdInEntity(final Entity entity) {
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
    private void setPgIdInEntity(final Entity entity) {
        final PageId id = this.getPgId();
        final int num = id.getNumber();
        entity.setProperty(PG_ID_NUM_PROPERTY, num);
        final String version = id.getVersion();
        entity.setProperty(PG_ID_VERSION_PROPERTY, version);
    }

	@Override
	void setPropertiesInEntity(Entity entity) {
		super.setPropertiesInEntity(entity);
		this.setPgIdInEntity(entity);
		this.setChildAuthorIdInEntity(entity);
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
