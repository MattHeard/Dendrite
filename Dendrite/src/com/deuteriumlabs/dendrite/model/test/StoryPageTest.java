package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryPageTest {
	private static final String DUMMY_TEXT = "Dummy text";
	private static final String DUMMY_NEW_TEXT = "Dummy new text";
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private static PageId getDummyPageId() {
		final PageId id = new PageId();
		final int number = 1;
		id.setNumber(number);
		final String version = "a";
		id.setVersion(version);
		return id;
	}

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
		final String actual = pageAfterUpdate.getText();
		final String message = "The page text was not updated correctly.";
		assertEquals(message, expected, actual);
	}
	
	@Test
	public final void testRead() {
		createDummyStoryPage();
		
		final StoryPage page = new StoryPage();
		final PageId id = getDummyPageId();
		page.setId(id);
		page.read();
		
		final String expected = DUMMY_TEXT;
		final String actual = page.getText();
		final String message = "The page text was not correct.";
		assertEquals(message, expected, actual);
	}

	private static void createDummyStoryPage() {
		final PageId id = getDummyPageId();
		final StoryPage page = new StoryPage();
		page.setId(id);
		page.setText(DUMMY_TEXT);
		page.create();
	}
}
