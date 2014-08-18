/**
 * 
 */
package com.deuteriumlabs.dendrite.model;

import com.google.appengine.api.datastore.Entity;


/**
 * 
 */
public class PgRewriteNotification extends Notification {

    private static final String PG_ID_NUM_PROPERTY = "pgIdNum";
	private static final String PG_ID_VERSION_PROPERTY = "pgIdVersion";
	public static final String REWRITE_AUTHOR_ID_PROPERTY = "rewriteAuthorId";
	
	private static int getPgIdNumFromEntity(final Entity entity) {
        final Long num = (Long) entity.getProperty(PG_ID_NUM_PROPERTY);
        return num.intValue();
	}
	
    private static String getPgIdVersionFromEntity(final Entity entity) {
        return (String) entity.getProperty(PG_ID_VERSION_PROPERTY);
	}
    
    private static String getRewriteAuthorIdFromEntity(final Entity entity) {
		String propertyName = REWRITE_AUTHOR_ID_PROPERTY;
		final String id = (String) entity.getProperty(propertyName);
		return id;
	}
    
    private PageId pgId;
	private String rewriteAuthorId;

    public PgRewriteNotification() {}

    public PgRewriteNotification(final Entity entity) {
    	this.readPropertiesFromEntity(entity);
	}

    @Override
    public String getMsg() {
        final String name = this.getRewriteAuthorName();
        final PageId pgId = this.getPgId();
        final String msg;
        msg = name + " rewrote one of your pages and wrote page " + pgId + ".";
		return msg;
    }

    /**
     * @return
     */
    private PageId getPgId() {
        return this.pgId;
    }

    /**
     * @return
     */
    private String getRewriteAuthorId() {
        return this.rewriteAuthorId;
    }

	/**
     * @return
     */
    private String getRewriteAuthorName() {
        final String id = this.getRewriteAuthorId();
        final User author = new User();
        author.setId(id);
        author.read();
        final String name = author.getDefaultPenName();
        return name;
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
	void readPropertiesFromEntity(final Entity entity) {
		super.readPropertiesFromEntity(entity);
		this.readPgIdFromEntity(entity);
		this.readRewriteAuthorIdFromEntity(entity);
	}

	private void readRewriteAuthorIdFromEntity(final Entity entity) {
		final String id = getRewriteAuthorIdFromEntity(entity);
		this.setRewriteAuthorId(id);
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
		this.setRewriteAuthorIdInEntity(entity);
	}

    /**
     * @param id
     */
    public void setRewriteAuthorId(final String id) {
        this.rewriteAuthorId = id;
    }

	private void setRewriteAuthorIdInEntity(final Entity entity) {
    	final String id = this.getRewriteAuthorId();
    	entity.setProperty(REWRITE_AUTHOR_ID_PROPERTY, id);
	}
}
