package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryPageTest {
    private static final String DUMMY_NEW_TEXT = "Dummy new text";
    private static final String DUMMY_TEXT = "Dummy text";
	private static final String DUMMY_TAG = "horror";

    private static void createDummyStoryPage() {
        final PageId id = getDummyPageId();
        final StoryPage page = new StoryPage();
        page.setId(id);
        page.setText(DUMMY_TEXT);
        page.create();
    }

    private static PageId getDummyPageId() {
        final PageId id = new PageId();
        final int number = 1;
        id.setNumber(number);
        final String version = "a";
        id.setVersion(version);
        return id;
    }

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
    public final void testCreate() {
        final StoryPage page = new StoryPage();
        final PageId id = getDummyPageId();
        page.setId(id);
        page.setText(DUMMY_TEXT);

        page.create();

        final boolean isPageInStore = page.isInStore();
        final String message = "The story page was not in the store.";
        assertTrue(message, isPageInStore);
    }

    @Test
    public final void testDelete() {
        createDummyStoryPage();

        final StoryPage page = new StoryPage();
        final PageId id = getDummyPageId();
        page.setId(id);

        page.delete();

        final boolean isPageInStore = page.isInStore();
        final String message;
        message = "The story page should not have been in the store.";
        assertFalse(message, isPageInStore);
    }
    
    @Test
    public final void testAddTag() {
    	final PageId id = getDummyPageId();
		StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.setText(DUMMY_TEXT);
		pg.create();
    	
    	pg = new StoryPage();
    	pg.setId(id);
    	pg.read();
    	
    	List<String> expected = new ArrayList<String>();
    	List<String> actual = pg.getTags();
    	String msg = "There should be an empty tags list.";
    	assertEquals(msg, expected, actual);
    	
    	pg.addTag(DUMMY_TAG);
    	pg.update();
    	
    	pg = new StoryPage();
    	pg.setId(id);
    	pg.read();
    	
    	expected = new ArrayList<String>();
    	expected.add(DUMMY_TAG);
    	actual = pg.getTags();
    	msg = "There should be one tag.";
    	assertEquals(msg, expected, actual);
    }
    
    @Test
    public final void testRemoveTag() {
    	final PageId id = getDummyPageId();
		StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.setText(DUMMY_TEXT);
    	pg.addTag(DUMMY_TAG);
		pg.create();
    	
    	pg = new StoryPage();
    	pg.setId(id);
    	pg.read();
    	
    	List<String> expected = new ArrayList<String>();
    	expected.add(DUMMY_TAG);
    	List<String> actual = pg.getTags();
    	String msg = "There should be one tag.";
    	assertEquals(msg, expected, actual);
    	
    	pg.removeTag(DUMMY_TAG);
    	pg.update();
    	
    	pg = new StoryPage();
    	pg.setId(id);
    	pg.read();
    	
    	expected = new ArrayList<String>();
    	actual = pg.getTags();
    	msg = "There should be an empty tags list.";
    	assertEquals(msg, expected, actual);
    }

    @Test
    public final void testRead() {
        createDummyStoryPage();

        final StoryPage page = new StoryPage();
        final PageId id = getDummyPageId();
        page.setId(id);
        page.read();

        final String expected = DUMMY_TEXT;
        final Text text = page.getText();
        final String actual = text.getValue();
        final String message = "The page text was not correct.";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public final void testUpdate() {
        createDummyStoryPage();
        final StoryPage pageBeforeUpdate = new StoryPage();
        final PageId id = getDummyPageId();
        pageBeforeUpdate.setId(id);
        pageBeforeUpdate.read();
        pageBeforeUpdate.setText(DUMMY_NEW_TEXT);

        pageBeforeUpdate.update();
        final StoryPage pageAfterUpdate = new StoryPage();
        pageAfterUpdate.setId(id);
        pageAfterUpdate.read();

        final String expected = DUMMY_NEW_TEXT;
        final Text text = pageAfterUpdate.getText();
        final String actual = text.getValue();
        final String message = "The page text was not updated correctly.";
        assertEquals(message, expected, actual);
    }
}
