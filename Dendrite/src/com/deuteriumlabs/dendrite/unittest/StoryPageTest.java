package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.StoryPage;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryPageTest {
    final static String IMPLEMENT_ME = "Not implemented yet";
    final static String NOT_WRITTEN = "This page has not been written yet.";
    
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }


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
    
    // TODO Set up a test database and create a StoryPage
    @Ignore(IMPLEMENT_ME)
    @Test
    public void testConstructorWithEntity() {
        fail(IMPLEMENT_ME);
    }

    // TODO Set up a test database and create a StoryPage
    @Ignore(IMPLEMENT_ME)
    @Test
    public void testCreate() {
        fail(IMPLEMENT_ME);
    }

    // TODO Set up a test database and query a created StoryPage
    @Ignore(IMPLEMENT_ME)
    @Test
    public void testGetAncestry() {
        fail(IMPLEMENT_ME);
    }
}