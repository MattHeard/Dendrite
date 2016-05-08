package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class UserTest {
    @Test
    public void testGetAvatarDesc() {
        final String description = User.getAvatarDesc(0);
        assertNotEquals(0, description.length());
    }
    
    @Test
    public void testGetDefaultAlignment() {
        final String alignment = User.getDefaultAlignment();
        assertNotEquals(0, alignment.length());
    }
    
    @Test
    public void testDefaultAvatarId() {
        assertEquals(0, User.getDefaultAvatarId());
    }
    
    @Test
    public void testGetDefaultFontColour() {
        final String colour = User.getDefaultFontColour();
        assertNotEquals(0, colour.length());
    }
    
    @Test
    public void testGetDefaultFontType() {
        final String fontType = User.getDefaultFontType();
        assertNotEquals(0, fontType.length());
    }
    
    @Test
    public void testDefaultSpacing() {
        assertEquals(1.5, User.getDefaultSpacing(), 0.1);
    }
    
    @Test
    public void testGetDefaultTheme() {
        final String theme = User.getDefaultTheme();
        assertNotEquals(0, theme.length());
    }
    
    @Test
    public void testDefaultConstructor() {
        final User user = new User();
        assertNotEquals(0, user.getDefaultPenName().length());
        assertEquals(1.0, user.getFontSize(), 0.1);
        assertNotEquals(0, user.getFontType().length());
        assertNotEquals(0, user.getFontColour().length());
        assertEquals(1.5, user.getSpacing(), 0.1);
        assertNotEquals(0, user.getAlignment().length());
        assertNotEquals(0, user.getTheme().length());
        assertEquals(0, user.getAvatarId());
    }
}