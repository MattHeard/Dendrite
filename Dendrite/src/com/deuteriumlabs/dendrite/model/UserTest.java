package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Test
    public void testCreate() {
        helper.setUp();
        final User user = new User();
        user.create();
        assertTrue(user.isInStore());
        helper.tearDown();
    }

    @Test
    public void testDelete() {
        helper.setUp();
        final User user = new User();
        user.create();
        user.delete();
        assertFalse(user.isInStore());
        helper.tearDown();
    }

    @Test
    public void testGetAlignment() {
        final User user = new User();
        final String defaultAlignment = "Justify";
        assertEquals(defaultAlignment, user.getAlignment());
    }

    @Test
    public void testGetAvatarDesc() {
        assertEquals("A silhouette of a man.", User.getAvatarDesc(0));
    }

    @Test
    public void testGetAvatarId() {
        final User user = new User();
        final int defaultAvatarId = 0;
        assertEquals(defaultAvatarId, user.getAvatarId());
    }

    @Test
    public void testGetDefaultAlignment() {
        assertEquals("Justify", User.getDefaultAlignment());
    }

    @Test
    public void testGetDefaultAvatarId() {
        assertEquals(0, User.getDefaultAvatarId());
    }

    @Test
    public void testGetDefaultFontColour() {
        assertEquals("Default", User.getDefaultFontColour());
    }

    @Test
    public void testGetDefaultFontSize() {
        assertEquals(1.0, User.getDefaultFontSize(), 0.1);
    }

    @Test
    public void testGetDefaultFontType() {
        assertEquals("Sans-serif", User.getDefaultFontType());
    }

    @Test
    public void testGetDefaultPenName() {
        final User user = new User();
        final String defaultDefaultPenName = "???";
        assertEquals(defaultDefaultPenName, user.getDefaultPenName());
    }

    @Test
    public void testGetDefaultSpacing() {
        assertEquals(1.5, User.getDefaultSpacing(), 0.1);
    }

    @Test
    public void testGetDefaultTheme() {
        assertEquals("Light", User.getDefaultTheme());
    }

    @Test
    public void testGetFollowers() {
        final User user = new User();
        final List<String> expectedFollowers = new ArrayList<String>();
        expectedFollowers.add("Test Follower");
        user.setFollowers(expectedFollowers);
        final List<String> actualFollowers = user.getFollowers();
        assertArrayEquals(expectedFollowers.toArray(),
                actualFollowers.toArray());
    }

    @Test
    public void testGetFontColour() {
        final User user = new User();
        final String defaultFontColour = "Default";
        assertEquals(defaultFontColour, user.getFontColour());
    }

    @Test
    public void testGetFontSize() {
        final User user = new User();
        final double defaultFontSize = 1.0;
        assertEquals(defaultFontSize, user.getFontSize(), 0.1);
    }

    @Test
    public void testGetFontType() {
        final User user = new User();
        final String defaultFontType = "Sans-serif";
        assertEquals(defaultFontType, user.getFontType());
    }

    @Test
    public void testGetFormerFollowers() {
        final User user = new User();
        final List<String> expectedFormerFollowers = new ArrayList<String>();
        expectedFormerFollowers.add("Test Follower");
        user.setFollowers(expectedFormerFollowers);
        final List<String> actualFormerFollowers = user.getFollowers();
        assertArrayEquals(expectedFormerFollowers.toArray(),
                actualFormerFollowers.toArray());
    }

    @Test
    public void testGetId() {
        final User user = new User();
        final String expectedId = "1";
        user.setId(expectedId);
        assertEquals(expectedId, user.getId());
    }

    @Test
    public void testGetKindName() {
        final User user = new User();
        assertEquals("User", user.getKindName());
    }

    @Test
    public void testGetMatchingQuery() {
        helper.setUp();
        final User user = new User();
        user.setId("1");
        assertEquals("User", user.getMatchingQuery().getKind());
        assertEquals("id = 1", user.getMatchingQuery().getFilter().toString());
        helper.tearDown();
    }

    @Test
    public void testGetSpacing() {
        final User user = new User();
        final double defaultSpacing = 1.5;
        assertEquals(defaultSpacing, user.getSpacing(), 0.1);
    }

    @Test
    public void testGetTheme() {
        final User user = new User();
        final String defaultTheme = "Light";
        assertEquals(defaultTheme, user.getTheme());
    }

    @Test
    public void testIsAvatarAvailable() {
        final User user = new User();
        assertTrue(user.isAvatarAvailable());
    }

    @Test
    public void testIsFontSizeSetWithDefaultSize() {
        final User user = new User();
        assertFalse(user.isFontSizeSet());
    }

    @Test
    public void testIsFontSizeSetWithNonDefaultSize() {
        final User user = new User();
        final double nonDefaultFontSize = 2.0;
        user.setFontSize(nonDefaultFontSize);
        assertTrue(user.isFontSizeSet());
    }

    @Test
    public void testIsInStoreAfterCreate() {
        helper.setUp();
        final User user = new User();
        user.create();
        assertTrue(user.isInStore());
        helper.tearDown();
    }

    @Test
    public void testIsInStoreAfterDelete() {
        helper.setUp();
        final User user = new User();
        user.create();
        user.delete();
        assertFalse(user.isInStore());
        helper.tearDown();
    }

    @Ignore
    @Test
    public void testIsMyUserLoggedIn() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRead() {
        helper.setUp();
        final User originalUser = new User();
        final String userId = "1";
        originalUser.setId(userId);
        final int expectedAvatarId = 1;
        originalUser.setAvatarId(expectedAvatarId);
        originalUser.create();
        final User retrievedUser = new User();
        retrievedUser.setId(userId);
        retrievedUser.read();
        assertEquals(expectedAvatarId, retrievedUser.getAvatarId());
        helper.tearDown();
    }

    @Test
    public void testReadPropertiesFromEntity() {
        final DatastoreEntity entity = Mockito.mock(DatastoreEntity.class);
        final User user = new User();
        user.readPropertiesFromEntity(entity);
        final String[] properties = { "id", "defaultPenName", "fontSize",
                "fontType", "fontColour", "spacing", "alignment", "theme",
                "avatarId", "followers", "formerFollowers" };
        for (final String property : properties) {
            Mockito.verify(entity, Mockito.times(1)).getProperty(property);
        }
    }

    @Test
    public void testSetAlignment() {
        final User user = new User();
        final String alignment = "test";
        user.setAlignment(alignment);
        assertEquals(alignment, user.getAlignment());
    }

    @Test
    public void testSetAvatarId() {
        final User user = new User();
        final int avatarId = 1;
        user.setAvatarId(avatarId);
        assertEquals(avatarId, user.getAvatarId());
    }

    @Test
    public void testSetDefaultPenName() {
        final User user = new User();
        final String defaultPenName = "test";
        user.setDefaultPenName(defaultPenName);
        assertEquals(defaultPenName, user.getDefaultPenName());
    }

    @Test
    public void testSetFollowers() {
        final User user = new User();
        final List<String> followers = new ArrayList<String>();
        user.setFollowers(followers);
        assertEquals(followers, user.getFollowers());
    }

    @Test
    public void testSetFontColour() {
        final User user = new User();
        final String fontColour = "test";
        user.setFontColour(fontColour);
        assertEquals(fontColour, user.getFontColour());
    }

    @Test
    public void testSetFontSize() {
        final User user = new User();
        final double fontSize = 2.0;
        user.setFontSize(fontSize);
        assertEquals(fontSize, user.getFontSize(), 0.1);
    }

    @Test
    public void testSetFontType() {
        final User user = new User();
        final String fontType = "test";
        user.setFontType(fontType);
        assertEquals(fontType, user.getFontType());
    }

    @Test
    public void testSetFormerFollowers() {
        final User user = new User();
        final List<String> formerFollowers = new ArrayList<String>();
        user.setFormerFollowers(formerFollowers);
        assertEquals(formerFollowers, user.getFormerFollowers());
    }

    @Test
    public void testSetId() {
        final User user = new User();
        final String id = "1";
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testSetPropertiesInEntity() {
        final DatastoreEntity entity = Mockito.mock(DatastoreEntity.class);
        final User user = new User();
        user.setPropertiesInEntity(entity);
        Mockito.verify(entity, Mockito.times(1)).setProperty("id", null);
        Mockito.verify(entity, Mockito.times(1)).setProperty("defaultPenName",
                "???");
        Mockito.verify(entity, Mockito.times(1)).setProperty("fontSize", 1.0);
        Mockito.verify(entity, Mockito.times(1)).setProperty("fontType",
                "Sans-serif");
        Mockito.verify(entity, Mockito.times(1)).setProperty("fontColour",
                "Default");
        Mockito.verify(entity, Mockito.times(1)).setProperty("spacing", 1.5);
        Mockito.verify(entity, Mockito.times(1)).setProperty("alignment",
                "Justify");
        Mockito.verify(entity, Mockito.times(1)).setProperty("theme", "Light");
        Mockito.verify(entity, Mockito.times(1)).setProperty("avatarId", 0);
        Mockito.verify(entity, Mockito.times(1)).setProperty("followers", null);
        Mockito.verify(entity, Mockito.times(1)).setProperty("formerFollowers",
                null);
    }

    @Test
    public void testSetSpacing() {
        final User user = new User();
        final double spacing = 2.0;
        user.setSpacing(spacing);
        assertEquals(spacing, user.getSpacing(), 0.1);
    }

    @Test
    public void testSetTheme() {
        final User user = new User();
        final String theme = "test";
        user.setTheme(theme);
        assertEquals(theme, user.getTheme());
    }

    @Test
    public void testUpdate() {
        helper.setUp();
        final User originalUser = new User();
        final String id = "1";
        originalUser.setId(id);
        final int originalAvatarId = 1;
        originalUser.setAvatarId(originalAvatarId);
        originalUser.create();
        originalUser.setAvatarId(2);
        originalUser.update();
        final User retrievedUser = new User();
        retrievedUser.setId(id);
        retrievedUser.read();
        assertEquals(2, retrievedUser.getAvatarId());
        helper.tearDown();
    }

    @Test
    public void testUser() {
        final User user = new User();
        assertEquals("???", user.getDefaultPenName());
        assertEquals(1.0, user.getFontSize(), 0.1);
        assertEquals("Sans-serif", user.getFontType());
        assertEquals("Default", user.getFontColour());
        assertEquals(1.5, user.getSpacing(), 0.1);
        assertEquals("Justify", user.getAlignment());
        assertEquals("Light", user.getTheme());
        assertEquals(0, user.getAvatarId());
    }
}
