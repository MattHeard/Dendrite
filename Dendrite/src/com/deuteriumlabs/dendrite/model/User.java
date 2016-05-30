/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class User extends Model {
    public static final String UNKNOWN_PEN_NAME = "???";
    private static final String ALIGNMENT_PROPERTY = "alignment";
    private static final String AVATAR_ID_PROPERTY = "avatarId";
    private static final Map<Integer, String> avatarDescs;
    private static User cachedUser;
    private static final String DEFAULT_ALIGNMENT = "Justify";
    private static final int DEFAULT_AVATAR_ID = 0;
    private static final String DEFAULT_FONT_COLOUR = "Default";
    private static final double DEFAULT_FONT_SIZE = 1.0;
    private static final String DEFAULT_FONT_TYPE = "Sans-serif";
    private static final String DEFAULT_PEN_NAME_PROPERTY = "defaultPenName";
    private static final double DEFAULT_SPACING = 1.5;
    private static final String DEFAULT_THEME = "Light";
    private static final String FOLLOWERS_PROPERTY = "followers";
    private static final String FONT_COLOUR_PROPERTY = "fontColour";
    private static final String FONT_SIZE_PROPERTY = "fontSize";
    private static final String FONT_TYPE_PROPERTY = "fontType";
    private static final String FORMER_FOLLOWERS_PROPERTY = "formerFollowers";
    private static final String ID_PROPERTY = "id";
    private static final String KIND_NAME = "User";
    private static final String SPACING_PROPERTY = "spacing";

    private static final String THEME_PROPERTY = "theme";

    static {
        final Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "A silhouette of a man.");
        map.put(1, "A brunette woman smiling confidently.");
        map.put(2, "A green male superhero with a black mask.");
        map.put(3, "A golden lion roaring.");
        map.put(4, "A smiling blonde woman with coiffed hair.");
        map.put(5, "A person wearing a blue fighter pilot cap.");
        map.put(6, "A golden mountain lion roaring.");
        map.put(7, "A woman with black hair and a red hat.");
        map.put(8, "A blonde-haired male superhero with a black eye mask.");
        map.put(9, "A black panther.");
        map.put(10, "A blonde woman in a red dress looking surprised.");
        map.put(11, "A detective with a black trench coat and black eye mask.");
        map.put(12, "A wolf howling in front of the moon.");
        map.put(13,
                "A blonde woman with short hair in western-style clothing.");
        map.put(14,
                "An older man with black hair and glasses looking shocked.");
        map.put(15, "A green reptilian monster.");
        map.put(16, "A blonde woman looking afraid.");
        map.put(17, "A small goblin-like man in a green hat.");
        map.put(18, "A black-haired male superhero looking determined.");
        map.put(19, "A blonde woman with a red hair bow looking shocked.");
        map.put(20,
                "A male superhero with a red face mask and a small moustache.");
        map.put(21, "A blonde haired boy in a red sweater.");
        map.put(22, "A policeman.");
        map.put(23, "A female superhero in a red hood and face mask.");
        map.put(24, "A man in a green hood and cape.");
        map.put(25, "A male superhero with a star on his forehead.");
        map.put(26, "A brunette woman in a blue and yellow shirt.");
        map.put(27, "A blonde person in a yellow shirt.");
        map.put(28, "A grinning man in a red cape and tall red hat.");
        map.put(29, "A black-haired, elegant woman.");
        avatarDescs = Collections.unmodifiableMap(map);
    }

    public static String getAvatarDesc(final int id) {
        final String desc = avatarDescs.get(id);
        if (desc != null) {
            return desc;
        } else {
            return "A small picture of a person.";
        }
    }

    public static String getDefaultAlignment() {
        return DEFAULT_ALIGNMENT;
    }

    public static int getDefaultAvatarId() {
        return DEFAULT_AVATAR_ID;
    }

    public static String getDefaultFontColour() {
        return DEFAULT_FONT_COLOUR;
    }

    public static double getDefaultFontSize() {
        return DEFAULT_FONT_SIZE;
    }

    public static String getDefaultFontType() {
        return DEFAULT_FONT_TYPE;
    }

    public static double getDefaultSpacing() {
        return DEFAULT_SPACING;
    }

    public static String getDefaultTheme() {
        return DEFAULT_THEME;
    }

    public static User getMyUser() {
        final User myUser = new User();
        final String myUserId = getMyUserId();
        if (myUserId != null) {
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
            cachedUser = myUser;
            return myUser;
        } else {
            return null;
        }
    }

    public static String getMyUserId() {
        final UserService userService = UserServiceFactory.getUserService();
        final com.google.appengine.api.users.User appEngineUser;
        appEngineUser = userService.getCurrentUser();
        if (isMyUserLoggedIn() == true) {
            return appEngineUser.getUserId();
        } else {
            return null;
        }
    }

    public static boolean isMyUserLoggedIn() {
        final UserService userService = UserServiceFactory.getUserService();
        final com.google.appengine.api.users.User appEngineUser;
        appEngineUser = userService.getCurrentUser();
        return (appEngineUser != null);
    }

    private static String getAlignmentFromEntity(final DatastoreEntity entity) {
        final String alignment = (String) entity
                .getProperty(ALIGNMENT_PROPERTY);
        if (alignment != null) {
            return alignment;
        } else {
            return DEFAULT_ALIGNMENT;
        }
    }

    private static String getDefaultPenNameFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(DEFAULT_PEN_NAME_PROPERTY);
    }

    private static String getFontColourFromEntity(
            final DatastoreEntity entity) {
        final String fontColour = (String) entity
                .getProperty(FONT_COLOUR_PROPERTY);
        if (fontColour != null) {
            return fontColour;
        } else {
            return DEFAULT_FONT_COLOUR;
        }
    }

    private static double getFontSizeFromEntity(final DatastoreEntity entity) {
        final Double fontSize = (Double) entity.getProperty(FONT_SIZE_PROPERTY);
        if (fontSize != null) {
            return fontSize;
        } else {
            return DEFAULT_FONT_SIZE;
        }
    }

    private static String getFontTypeFromEntity(final DatastoreEntity entity) {
        final String fontType = (String) entity.getProperty(FONT_TYPE_PROPERTY);
        if (fontType != null) {
            return fontType;
        } else {
            return DEFAULT_FONT_TYPE;
        }
    }

    private static Filter getIdFilter(final String id) {
        final String propertyName = ID_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final String value = id;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static String getIdFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(ID_PROPERTY);
    }

    private static double getSpacingFromEntity(final DatastoreEntity entity) {
        final Double spacing = (Double) entity.getProperty(SPACING_PROPERTY);
        if (spacing != null) {
            return spacing;
        } else {
            return DEFAULT_SPACING;
        }
    }

    private static String getThemeFromEntity(final DatastoreEntity entity) {
        final String theme = (String) entity.getProperty(THEME_PROPERTY);
        if (theme != null) {
            return theme;
        } else {
            return DEFAULT_THEME;
        }
    }

    private String alignment;
    private int avatarId;
    private String defaultPenName;
    private List<String> followers;
    private String fontColour;
    private double fontSize;
    private String fontType;
    private List<String> formerFollowers;
    private String id;
    private double spacing;
    private String theme;

    public User() {
        setDefaultPenName(UNKNOWN_PEN_NAME);
        setFontSize(DEFAULT_FONT_SIZE);
        setFontType(DEFAULT_FONT_TYPE);
        setFontColour(DEFAULT_FONT_COLOUR);
        setSpacing(DEFAULT_SPACING);
        setAlignment(DEFAULT_ALIGNMENT);
        setTheme(DEFAULT_THEME);
        setAvatarId(DEFAULT_AVATAR_ID);
    }

    public String getAlignment() {
        return alignment;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getDefaultPenName() {
        return defaultPenName;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public String getFontColour() {
        return fontColour;
    }

    public double getFontSize() {
        return fontSize;
    }

    public String getFontType() {
        return fontType;
    }

    public List<String> getFormerFollowers() {
        return formerFollowers;
    }

    public String getId() {
        return id;
    }

    public double getSpacing() {
        return spacing;
    }

    public String getTheme() {
        return theme;
    }

    public boolean isAvatarAvailable() {
        final int avatarId = getAvatarId();
        return (avatarId > -1);
    }

    public boolean isFontSizeSet() {
        final double fontSize = getFontSize();
        return (fontSize != DEFAULT_FONT_SIZE);
    }

    public void setAlignment(final String alignment) {
        this.alignment = alignment;
    }

    public void setAvatarId(final int avatarId) {
        this.avatarId = (avatarId > -1 ? avatarId : 0);
    }

    public void setDefaultPenName(final String defaultPenName) {
        this.defaultPenName = defaultPenName;
    }

    public void setFollowers(final List<String> followers) {
        this.followers = followers;
    }

    public void setFontColour(final String fontColour) {
        this.fontColour = fontColour;
    }

    public void setFontSize(final double fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontType(final String fontType) {
        this.fontType = fontType;
    }

    public void setFormerFollowers(final List<String> formerFollowers) {
        this.formerFollowers = formerFollowers;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setSpacing(final double spacing) {
        this.spacing = spacing;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    private int getAvatarIdFromEntity(final DatastoreEntity entity) {
        final Long avatarId = (Long) entity.getProperty(AVATAR_ID_PROPERTY);
        if (avatarId != null) {
            return avatarId.intValue();
        } else {
            return DEFAULT_AVATAR_ID;
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getFollowersFromEntity(final DatastoreEntity entity) {
        return (List<String>) entity.getProperty(FOLLOWERS_PROPERTY);
    }

    @SuppressWarnings("unchecked")
    private List<String> getFormerFollowersFromEntity(
            final DatastoreEntity entity) {
        return (List<String>) entity.getProperty(FORMER_FOLLOWERS_PROPERTY);
    }

    private void readAlignmentFromEntity(final DatastoreEntity entity) {
        final String alignment = getAlignmentFromEntity(entity);
        setAlignment(alignment);
    }

    private void readAvatarIdFromEntity(final DatastoreEntity entity) {
        final int avatarId = getAvatarIdFromEntity(entity);
        setAvatarId(avatarId);
    }

    private void readDefaultPenNameFromEntity(final DatastoreEntity entity) {
        final String defaultPenName = getDefaultPenNameFromEntity(entity);
        setDefaultPenName(defaultPenName);
    }

    private void readFollowersFromEntity(final DatastoreEntity entity) {
        List<String> followers = getFollowersFromEntity(entity);
        if (followers == null) {
            followers = new ArrayList<String>();
        }
        setFollowers(followers);
    }

    private void readFontColourFromEntity(final DatastoreEntity entity) {
        final String fontColour = getFontColourFromEntity(entity);
        setFontColour(fontColour);
    }

    private void readFontSizeFromEntity(final DatastoreEntity entity) {
        final double fontSize = getFontSizeFromEntity(entity);
        setFontSize(fontSize);
    }

    private void readFontTypeFromEntity(final DatastoreEntity entity) {
        final String fontType = getFontTypeFromEntity(entity);
        setFontType(fontType);
    }

    private void readFormerFollowersFromEntity(final DatastoreEntity entity) {
        List<String> formerFollowers = getFormerFollowersFromEntity(entity);
        if (formerFollowers == null) {
            formerFollowers = new ArrayList<String>();
        }
        setFormerFollowers(formerFollowers);
    }

    private void readIdFromEntity(final DatastoreEntity entity) {
        final String id = getIdFromEntity(entity);
        setId(id);
    }

    private void readSpacingFromEntity(final DatastoreEntity entity) {
        final double spacing = getSpacingFromEntity(entity);
        setSpacing(spacing);
    }

    private void readThemeFromEntity(final DatastoreEntity entity) {
        final String theme = getThemeFromEntity(entity);
        setTheme(theme);
    }

    private void setAlignmentInEntity(final DatastoreEntity entity) {
        final String alignment = getAlignment();
        entity.setProperty(ALIGNMENT_PROPERTY, alignment);
    }

    private void setAvatarIdInEntity(final DatastoreEntity entity) {
        final int avatarId = getAvatarId();
        entity.setProperty(AVATAR_ID_PROPERTY, avatarId);
    }

    private void setDefaultPenNameInEntity(final DatastoreEntity entity) {
        final String defaultPenName = getDefaultPenName();
        entity.setProperty(DEFAULT_PEN_NAME_PROPERTY, defaultPenName);
    }

    private void setFollowersInEntity(final DatastoreEntity entity) {
        final List<String> followers = getFollowers();
        entity.setProperty(FOLLOWERS_PROPERTY, followers);
    }

    private void setFontColourInEntity(final DatastoreEntity entity) {
        final String fontColour = getFontColour();
        entity.setProperty(FONT_COLOUR_PROPERTY, fontColour);
    }

    private void setFontSizeInEntity(final DatastoreEntity entity) {
        final double fontSize = getFontSize();
        entity.setProperty(FONT_SIZE_PROPERTY, fontSize);
    }

    private void setFontTypeInEntity(final DatastoreEntity entity) {
        final String fontType = getFontType();
        entity.setProperty(FONT_TYPE_PROPERTY, fontType);
    }

    private void setFormerFollowersInEntity(final DatastoreEntity entity) {
        final List<String> formerFollowers = getFormerFollowers();
        entity.setProperty(FORMER_FOLLOWERS_PROPERTY, formerFollowers);
    }

    private void setIdInEntity(final DatastoreEntity entity) {
        final String id = getId();
        entity.setProperty(ID_PROPERTY, id);
    }

    private void setSpacingInEntity(final DatastoreEntity entity) {
        final double spacing = getSpacing();
        entity.setProperty(SPACING_PROPERTY, spacing);
    }

    private void setThemeInEntity(final DatastoreEntity entity) {
        final String theme = getTheme();
        entity.setProperty(THEME_PROPERTY, theme);
    }

    @Override
    String getKindName() {
        return KIND_NAME;
    }

    @Override
    DatastoreQuery getMatchingQuery() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final String id = getId();
        final Filter idFilter = getIdFilter(id);
        query.setFilter(idFilter);
        return query;
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        readIdFromEntity(entity);
        readDefaultPenNameFromEntity(entity);
        readFontSizeFromEntity(entity);
        readFontTypeFromEntity(entity);
        readFontColourFromEntity(entity);
        readSpacingFromEntity(entity);
        readAlignmentFromEntity(entity);
        readThemeFromEntity(entity);
        readAvatarIdFromEntity(entity);
        readFollowersFromEntity(entity);
        readFormerFollowersFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        setIdInEntity(entity);
        setDefaultPenNameInEntity(entity);
        setFontSizeInEntity(entity);
        setFontTypeInEntity(entity);
        setFontColourInEntity(entity);
        setSpacingInEntity(entity);
        setAlignmentInEntity(entity);
        setThemeInEntity(entity);
        setAvatarIdInEntity(entity);
        setFollowersInEntity(entity);
        setFormerFollowersInEntity(entity);
    }
}
