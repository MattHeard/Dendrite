package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Represents the visitor to the website. <code>User</code> instances provide
 * two main purposes: storing user preferences and crediting authors. The
 * website is designed to be as usable as possible by anonymous, logged-out
 * visitors, but logging in makes it possible to store user preferences between
 * visits and makes it possible to credit multiple story pages to a single
 * author.
 */
public class User extends Model {
	private static final String ALIGNMENT_PROPERTY = "alignment";
	private static User cachedUser;
	private static final String DEFAULT_ALIGNMENT = "Justify";
	private static final String DEFAULT_FONT_COLOUR = "Default";
	private static final double DEFAULT_FONT_SIZE = 1.0;
	private static final String DEFAULT_FONT_TYPE = "Sans-serif";
	private static final String DEFAULT_PEN_NAME_PROPERTY = "defaultPenName";
	private static final double DEFAULT_SPACING = 1.5;
	private static final String DEFAULT_THEME = "Light";
	private static final String FONT_COLOUR_PROPERTY = "fontColour";
	private static final String FONT_SIZE_PROPERTY = "fontSize";
	private static final String FONT_TYPE_PROPERTY = "fontType";
	private static final String ID_PROPERTY = "id";
	private static final String KIND_NAME = "User";
	private static final String SPACING_PROPERTY = "spacing";
	private static final String THEME_PROPERTY = "theme";
	private static final String UNKNOWN_PEN_NAME = "???";
	private static final int DEFAULT_AVATAR_ID = 0;
	private static final String AVATAR_ID_PROPERTY = "avatarId";
	private static final String FOLLOWERS_PROPERTY = "followers";
	private static final String FORMER_FOLLOWERS_PROPERTY = "formerFollowers";

	private static String getAlignmentFromEntity(final Entity entity) {
		String alignment = (String) entity.getProperty(ALIGNMENT_PROPERTY);
		if (alignment != null)
			return alignment;
		else
			return DEFAULT_ALIGNMENT;
	}

	private static User getCachedUser() {
		return cachedUser;
	}

	/**
	 * Returns the default pen name from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the default pen name
	 * @return The default pen name
	 */
	private static String getDefaultPenNameFromEntity(final Entity entity) {
		return (String) entity.getProperty(DEFAULT_PEN_NAME_PROPERTY);
	}

	private static String getFontColourFromEntity(final Entity entity) {
		String fontColour = (String) entity.getProperty(FONT_COLOUR_PROPERTY);
		if (fontColour != null)
			return fontColour;
		else
			return DEFAULT_FONT_COLOUR;
	}

	private static double getFontSizeFromEntity(final Entity entity) {
		Double fontSize = (Double) entity.getProperty(FONT_SIZE_PROPERTY);
		if (fontSize != null)
			return fontSize;
		else
			return DEFAULT_FONT_SIZE;
	}

	private static String getFontTypeFromEntity(final Entity entity) {
		String fontType = (String) entity.getProperty(FONT_TYPE_PROPERTY);
		if (fontType != null)
			return fontType;
		else
			return DEFAULT_FONT_TYPE;
	}

	/**
	 * Builds a filter to restrict a query to a particular user ID.
	 * 
	 * @param id
	 *            The ID to filter for
	 * @return The filter to apply to the query
	 */
	private static Filter getIdFilter(final String id) {
		final String propertyName = ID_PROPERTY;
		final FilterOperator operator = FilterOperator.EQUAL;
		final String value = id;
		return new FilterPredicate(propertyName, operator, value);
	}

	/**
	 * Returns the user ID from the given entity.
	 * 
	 * @param entity
	 *            The entity containing the user ID
	 * @return The user ID
	 */
	private static String getIdFromEntity(final Entity entity) {
		return (String) entity.getProperty(ID_PROPERTY);
	}

	/**
	 * Returns the <code>User</code> representing the logged-in visitor.
	 * 
	 * @return the <code>User</code> representing the logged-in visitor
	 */
	public static User getMyUser() {
		final User myUser = new User();
		final String myUserId = getMyUserId();
		if (myUserId != null) {
			final User cachedUser = getCachedUser();
			if (cachedUser != null) {
				final String cachedUserId = cachedUser.getId();
				if (cachedUserId.equals(myUserId)) {
					return cachedUser;
				}
			}
			myUser.setId(myUserId);
			final boolean isInStore = myUser.isInStore();
			if (isInStore == true) {
				myUser.read();
			} else {
				myUser.create();
			}
			setCachedUser(myUser);
			return myUser;
		} else {
			return null;
		}
	}

	/**
	 * Returns the Google App Engine user ID representing the logged-in visitor.
	 * 
	 * @return the Google App Engine user ID representing the logged-in visitor
	 */
	private static String getMyUserId() {
		final UserService userService = UserServiceFactory.getUserService();
		final com.google.appengine.api.users.User appEngineUser;
		appEngineUser = userService.getCurrentUser();
		if (isMyUserLoggedIn() == true)
			return appEngineUser.getUserId();
		else
			return null;
	}

	private static double getSpacingFromEntity(final Entity entity) {
		Double spacing = (Double) entity.getProperty(SPACING_PROPERTY);
		if (spacing != null)
			return spacing;
		else
			return DEFAULT_SPACING;
	}

	private static String getThemeFromEntity(final Entity entity) {
		String theme = (String) entity.getProperty(THEME_PROPERTY);
		if (theme != null)
			return theme;
		else
			return DEFAULT_THEME;
	}

	/**
	 * Returns whether the visitor is logged in or not.
	 * 
	 * @return <code>true</code> if the visitor is logged in, <code>false</code>
	 *         otherwise
	 */
	public static boolean isMyUserLoggedIn() {
		final UserService userService = UserServiceFactory.getUserService();
		final com.google.appengine.api.users.User appEngineUser;
		appEngineUser = userService.getCurrentUser();
		return (appEngineUser != null);
	}

	private static void setCachedUser(final User user) {
		cachedUser = user;
	}

	private String alignment;
	private String defaultPenName;
	private String fontColour;
	private double fontSize;
	private String fontType;
	private String id;
	private double spacing;
	private String theme;
	private int avatarId;
	private List<String> followers;
	private List<String> formerFollowers;

	/**
	 * Default constructor, which sets an initial default pen name in case the
	 * user has not set a default pen name.
	 */
	public User() {
		this.setDefaultPenName(UNKNOWN_PEN_NAME);
		this.setFontSize(DEFAULT_FONT_SIZE);
		this.setFontType(DEFAULT_FONT_TYPE);
		this.setFontColour(DEFAULT_FONT_COLOUR);
		this.setSpacing(DEFAULT_SPACING);
		this.setAlignment(DEFAULT_ALIGNMENT);
		this.setTheme(DEFAULT_THEME);
		this.setAvatarId(DEFAULT_AVATAR_ID);
	}

	public String getAlignment() {
		return this.alignment;
	}

	/**
	 * Returns the default pen name of this user.
	 * 
	 * @return The default pen name of this user
	 */
	public String getDefaultPenName() {
		return this.defaultPenName;
	}

	public String getFontColour() {
		return this.fontColour;
	}

	public double getFontSize() {
		return this.fontSize;
	}

	public String getFontType() {
		return this.fontType;
	}

	/**
	 * Returns the unique ID of this user.
	 * 
	 * @return The unique ID of this user
	 */
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deuteriumlabs.dendrite.model.Model#getKindName()
	 */
	@Override
	String getKindName() {
		return KIND_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deuteriumlabs.dendrite.model.Model#getMatchingQuery()
	 */
	@Override
	Query getMatchingQuery() {
		final Query query = new Query(KIND_NAME);
		final String id = this.getId();
		final Filter idFilter = getIdFilter(id);
		return query.setFilter(idFilter);
	}

	public double getSpacing() {
		return this.spacing;
	}

	public String getTheme() {
		return this.theme;
	}

	public boolean isFontSizeSet() {
		final double fontSize = this.getFontSize();
		return (fontSize != DEFAULT_FONT_SIZE);
	}

	private void readAlignmentFromEntity(final Entity entity) {
		final String alignment = getAlignmentFromEntity(entity);
		this.setAlignment(alignment);
	}

	/**
	 * Reads the value from the entity corresponding to the default pen name of
	 * this user.
	 * 
	 * @param entity
	 *            The entity storing the default pen name
	 */
	private void readDefaultPenNameFromEntity(final Entity entity) {
		final String defaultPenName = getDefaultPenNameFromEntity(entity);
		this.setDefaultPenName(defaultPenName);
	}

	private void readFontColourFromEntity(final Entity entity) {
		final String fontColour = getFontColourFromEntity(entity);
		this.setFontColour(fontColour);
	}

	private void readFontSizeFromEntity(final Entity entity) {
		final double fontSize = getFontSizeFromEntity(entity);
		this.setFontSize(fontSize);
	}

	private void readFontTypeFromEntity(final Entity entity) {
		final String fontType = getFontTypeFromEntity(entity);
		this.setFontType(fontType);
	}

	/**
	 * Reads the value from the entity corresponding to the unique ID of this
	 * user.
	 * 
	 * @param entity
	 *            The entity storing the user ID
	 */
	private void readIdFromEntity(final Entity entity) {
		final String id = getIdFromEntity(entity);
		this.setId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deuteriumlabs.dendrite.model.Model#readPropertiesFromEntity(com.google
	 * .appengine.api.datastore.Entity)
	 */
	@Override
	void readPropertiesFromEntity(final Entity entity) {
		this.readIdFromEntity(entity);
		this.readDefaultPenNameFromEntity(entity);
		this.readFontSizeFromEntity(entity);
		this.readFontTypeFromEntity(entity);
		this.readFontColourFromEntity(entity);
		this.readSpacingFromEntity(entity);
		this.readAlignmentFromEntity(entity);
		this.readThemeFromEntity(entity);
		this.readAvatarIdFromEntity(entity);
		this.readFollowersFromEntity(entity);
		this.readFormerFollowersFromEntity(entity);
	}

	private void readFormerFollowersFromEntity(final Entity entity) {
		List<String> formerFollowers = getFormerFollowersFromEntity(entity);
		if (formerFollowers == null) {
			formerFollowers = new ArrayList<String>();
		}
		this.setFormerFollowers(formerFollowers);
	}

	@SuppressWarnings("unchecked")
	private List<String> getFormerFollowersFromEntity(final Entity entity) {
		return (List<String>) entity.getProperty(FORMER_FOLLOWERS_PROPERTY);
	}

	private void readFollowersFromEntity(final Entity entity) {
		List<String> followers = getFollowersFromEntity(entity);
		if (followers == null) {
			followers = new ArrayList<String>();
		}
		this.setFollowers(followers);
	}

	@SuppressWarnings("unchecked")
	private List<String> getFollowersFromEntity(final Entity entity) {
		return (List<String>) entity.getProperty(FOLLOWERS_PROPERTY);
	}

	private void readAvatarIdFromEntity(final Entity entity) {
		final int avatarId = getAvatarIdFromEntity(entity);
		this.setAvatarId(avatarId);
	}

	private int getAvatarIdFromEntity(final Entity entity) {
		Long avatarId = (Long) entity.getProperty(AVATAR_ID_PROPERTY);
		if (avatarId != null)
			return avatarId.intValue();
		else
			return DEFAULT_AVATAR_ID;
	}

	private void readSpacingFromEntity(final Entity entity) {
		final double spacing = getSpacingFromEntity(entity);
		this.setSpacing(spacing);
	}

	private void readThemeFromEntity(final Entity entity) {
		final String theme = getThemeFromEntity(entity);
		this.setTheme(theme);
	}

	public void setAlignment(final String alignment) {
		this.alignment = alignment;
	}

	private void setAlignmentInEntity(final Entity entity) {
		final String alignment = this.getAlignment();
		entity.setProperty(ALIGNMENT_PROPERTY, alignment);
	}

	/**
	 * Sets the default pen name of this user.
	 * 
	 * @param defaultPenName
	 *            The default pen name of this user
	 */
	public void setDefaultPenName(final String defaultPenName) {
		this.defaultPenName = defaultPenName;
	}

	/**
	 * Sets the value in the entity corresponding to the default pen name of
	 * this user.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setDefaultPenNameInEntity(final Entity entity) {
		final String defaultPenName = this.getDefaultPenName();
		entity.setProperty(DEFAULT_PEN_NAME_PROPERTY, defaultPenName);
	}

	public void setFontColour(final String fontColour) {
		this.fontColour = fontColour;
	}

	private void setFontColourInEntity(final Entity entity) {
		final String fontColour = this.getFontColour();
		entity.setProperty(FONT_COLOUR_PROPERTY, fontColour);
	}

	public void setFontSize(final double fontSize) {
		this.fontSize = fontSize;
	}

	private void setFontSizeInEntity(final Entity entity) {
		final double fontSize = this.getFontSize();
		entity.setProperty(FONT_SIZE_PROPERTY, fontSize);
	}

	public void setFontType(final String fontType) {
		this.fontType = fontType;
	}

	private void setFontTypeInEntity(final Entity entity) {
		final String fontType = this.getFontType();
		entity.setProperty(FONT_TYPE_PROPERTY, fontType);
	}

	/**
	 * Sets the unique ID of this user.
	 * 
	 * @param id
	 *            The unique ID of this user
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Sets the value in the entity corresponding to the unique ID of this user.
	 * 
	 * @param entity
	 *            The entity in which the value is to be stored
	 */
	private void setIdInEntity(final Entity entity) {
		final String id = this.getId();
		entity.setProperty(ID_PROPERTY, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deuteriumlabs.dendrite.model.Model#setPropertiesInEntity(com.google
	 * .appengine.api.datastore.Entity)
	 */
	@Override
	void setPropertiesInEntity(final Entity entity) {
		this.setIdInEntity(entity);
		this.setDefaultPenNameInEntity(entity);
		this.setFontSizeInEntity(entity);
		this.setFontTypeInEntity(entity);
		this.setFontColourInEntity(entity);
		this.setSpacingInEntity(entity);
		this.setAlignmentInEntity(entity);
		this.setThemeInEntity(entity);
		this.setAvatarIdInEntity(entity);
		this.setFollowersInEntity(entity);
		this.setFormerFollowersInEntity(entity);
	}

	private void setFormerFollowersInEntity(final Entity entity) {
		final List<String> formerFollowers = this.getFormerFollowers();
		entity.setProperty(FORMER_FOLLOWERS_PROPERTY, formerFollowers);
	}

	private void setFollowersInEntity(final Entity entity) {
		final List<String> followers = this.getFollowers();
		entity.setProperty(FOLLOWERS_PROPERTY, followers);
	}

	private void setAvatarIdInEntity(final Entity entity) {
		final int avatarId = this.getAvatarId();
		entity.setProperty(AVATAR_ID_PROPERTY, avatarId);
	}

	public void setSpacing(final double spacing) {
		this.spacing = spacing;
	}

	private void setSpacingInEntity(final Entity entity) {
		final double spacing = this.getSpacing();
		entity.setProperty(SPACING_PROPERTY, spacing);
	}

	public void setTheme(final String theme) {
		this.theme = theme;
	}

	private void setThemeInEntity(final Entity entity) {
		final String theme = this.getTheme();
		entity.setProperty(THEME_PROPERTY, theme);
	}

	public boolean isAvatarAvailable() {
		final int avatarId = this.getAvatarId();
		return (avatarId > -1);
	}

	public int getAvatarId() {
		return this.avatarId;
	}

	public void setAvatarId(final int avatarId) {
		if (avatarId > -1) {
			this.avatarId = avatarId;
		} else {
			this.avatarId = 0;
		}
	}

	public static double getDefaultFontSize() {
		return DEFAULT_FONT_SIZE;
	}

	public static String getDefaultFontType() {
		return DEFAULT_FONT_TYPE;
	}

	public static String getDefaultFontColour() {
		return DEFAULT_FONT_COLOUR;
	}

	public static double getDefaultSpacing() {
		return DEFAULT_SPACING;
	}

	public static String getDefaultAlignment() {
		return DEFAULT_ALIGNMENT;
	}

	public static String getDefaultTheme() {
		return DEFAULT_THEME;
	}

	public static int getDefaultAvatarId() {
		return DEFAULT_AVATAR_ID;
	}

	public List<String> getFollowers() {
		return this.followers;
	}

	public List<String> getFormerFollowers() {
		return this.formerFollowers;
	}

	public void setFormerFollowers(final List<String> formerFollowers) {
		this.formerFollowers = formerFollowers;
	}

	public void setFollowers(final List<String> followers) {
		this.followers = followers;
	}
}
