package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryPageTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    @Before
    public void setUp() {
      helper.setUp();
    }

    @After
    public void tearDown() {
      helper.tearDown();
    }

    @Test
    public void testCreate() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCreateNewEntity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetMatchingEntity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetKindName() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetMatchingQuery() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testReadPropertiesFromEntity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetPropertiesInEntity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testConvertNumberToVersion() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountAllPagesWrittenBy() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountAllPgs() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountFirstPgsMatchingTag() {
        assertEquals(0, StoryPage.countFirstPgsMatchingTag("test tag"));
    }

    @Test
    public void testCountLoversBetween() {
        assertEquals(0, StoryPage.countLoversBetween("greater", "less"));
    }

    @Test
    public void testCountVersions() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAllVersions() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetFirstPgsMatchingTag() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetPagesWrittenBy() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetParentOf() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetRandomVersion() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountSubtreeBetween() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testStoryPage() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testStoryPageDatastoreEntity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddTag() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAncestry() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAuthorId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAuthorName() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetBeginning() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetChance() {
        final StoryPage storyPage = new StoryPage();
        final PageId beginningId = new PageId("1a");
        storyPage.setBeginning(beginningId);
        final PageId pageId = new PageId("2a");
        storyPage.setId(pageId);
        storyPage.create();
        final ArrayList<String> noLovingUsers = new ArrayList<String>();
        storyPage.setLovingUsers(noLovingUsers);
        assertEquals("2/1", storyPage.getChance());
    }

    @Test
    public void testGetFormerlyLovingUsers() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetLongSummary() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetLovingUsers() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetNumLovingUsers() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetParent() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetSummary() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetTags() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetText() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsFirstPg() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsLovedBy() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveTag() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetAuthorId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetAuthorName() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetBeginning() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetFormerlyLovingUsers() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetLovingUsers() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetParent() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetTextString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetTextText() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testToString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsInStore() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRead() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented"); // TODO
    }

}
