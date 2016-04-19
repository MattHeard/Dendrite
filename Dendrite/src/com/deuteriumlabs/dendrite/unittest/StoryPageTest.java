package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class StoryPageTest {
    final static String NOT_WRITTEN = "This page has not been written yet.";

    @Test
    public void testDefaultConstructor() {
        final StoryPage storyPage = new StoryPage();
        assertNull(storyPage.getBeginning());
        assertNull(storyPage.getAncestry());
        assertNull(storyPage.getAuthorId());
        assertNull(storyPage.getAuthorName());
        assertNull(storyPage.getBeginning());
        assertNull(storyPage.getFormerlyLovingUsers());
        assertNull(storyPage.getId());
        assertNull(storyPage.getLovingUsers());
        assertNull(storyPage.getParent());
        assertTrue(storyPage.getTags().isEmpty());
        assertEquals(storyPage.getLongSummary(), NOT_WRITTEN);
    }

    // TODO(Matt Heard): Implement with mock Entity
    @Test
    public void testConstructorWithEntity() {
        fail("Not implemented yet");
    }

    @Test
    public void testCreateWithNoParent() {
        fail("Not implemented yet");
    }

    @Test
    public void testCreateWithParent() {
        fail("Not implemented yet");
    }
}