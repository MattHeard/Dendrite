package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryBeginningTest {

	private static final String CHANGED_TITLE = "Changed title";
	private static final int DUMMY_PAGE_NUMBER = 1;
	private static final String DUMMY_TITLE = "Dummy title";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private void createDummyStoryBeginning() {
		final StoryBeginning beginning = new StoryBeginning();
		beginning.setPageNumber(DUMMY_PAGE_NUMBER);
		beginning.setTitle(DUMMY_TITLE);
		beginning.create();
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
		final StoryBeginning beginning = new StoryBeginning();
		beginning.setPageNumber(DUMMY_PAGE_NUMBER);
		beginning.setTitle(DUMMY_TITLE);
		
		beginning.create();
		
		final boolean isBeginningInStore = beginning.isInStore();
		final String message = "The story beginning was not in the store.";
		assertTrue(message, isBeginningInStore);
	}
	
	@Test
	public final void testDelete() {
		createDummyStoryBeginning();
		
		final StoryBeginning beginning = new StoryBeginning();
		beginning.setPageNumber(DUMMY_PAGE_NUMBER);
		beginning.delete();
		
		final boolean isBeginningInStore = beginning.isInStore();
		final String message;
		message = "The story beginning should not be in the store.";
		assertFalse(message, isBeginningInStore);
	}
	
	@Test
	public final void testRead() {
		createDummyStoryBeginning();
		final StoryBeginning beginning = new StoryBeginning();
		beginning.setPageNumber(DUMMY_PAGE_NUMBER);
		beginning.read();
		
		final String expected = DUMMY_TITLE;
		final String actual = beginning.getTitle();
		final String message = "The story title was not correct.";
		assertEquals(message, expected, actual);
	}

	@Test
	public final void testUpdate() {
		createDummyStoryBeginning();
		
		final StoryBeginning beginningBeforeUpdate = new StoryBeginning();
		beginningBeforeUpdate.setPageNumber(DUMMY_PAGE_NUMBER);
		beginningBeforeUpdate.read();
		
		beginningBeforeUpdate.setTitle(CHANGED_TITLE);
		beginningBeforeUpdate.update();
		
		final StoryBeginning beginningAfterUpdate = new StoryBeginning();
		beginningAfterUpdate.setPageNumber(DUMMY_PAGE_NUMBER);
		beginningAfterUpdate.read();
		final String expected = CHANGED_TITLE;
		final String actual = beginningAfterUpdate.getTitle();
		final String message = "The changed story title was not correct.";
		assertEquals(message, expected, actual);
	}

}
