package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryOptionTest {

	private static final String CHANGED_TEXT = "Changed text";
	private static final int DUMMY_LINK_INDEX = 0;
	private static final String DUMMY_TEXT = "Dummy text";
	
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

	private void createDummyStoryOption() {
		final StoryOption option = new StoryOption();
		final PageId source = getDummyPageId();
		option.setSource(source);
		option.setListIndex(DUMMY_LINK_INDEX);
		option.setText(DUMMY_TEXT);
		option.create();
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
		final StoryOption option = new StoryOption();
		final PageId id = getDummyPageId();
		option.setSource(id);
		option.setListIndex(DUMMY_LINK_INDEX);
		option.setText(DUMMY_TEXT);
		
		option.create();
		
		final boolean isOptionInStore = option.isInStore();
		final String message = "The story option was not in the store.";
		assertTrue(message, isOptionInStore);
	}
	
	@Test
	public final void testDelete() {
		createDummyStoryOption();
		
		final StoryOption option = new StoryOption();
		final PageId source = getDummyPageId();
		option.setSource(source);
		option.setListIndex(DUMMY_LINK_INDEX);
		option.delete();
		
		final boolean isOptionInStore = option.isInStore();
		final String message = "The story option should not be in the store.";
		assertFalse(message, isOptionInStore);
	}
	
	@Test
	public final void testRead() {
		createDummyStoryOption();
		
		final StoryOption option = new StoryOption();
		final PageId source = getDummyPageId();
		option.setSource(source);
		option.setListIndex(DUMMY_LINK_INDEX);
		option.read();
		
		final String expected = DUMMY_TEXT;
		final String actual = option.getText();
		final String message = "The option text was not correct.";
		assertEquals(message, expected, actual);
	}

	@Test
	public final void testUpdate() {
		createDummyStoryOption();
		
		final StoryOption optionBeforeUpdate = new StoryOption();
		final PageId source = getDummyPageId();
		optionBeforeUpdate.setSource(source);
		optionBeforeUpdate.setListIndex(DUMMY_LINK_INDEX);
		optionBeforeUpdate.read();
		
		optionBeforeUpdate.setText(CHANGED_TEXT);
		optionBeforeUpdate.update();
		
		final StoryOption optionAfterUpdate = new StoryOption();
		optionAfterUpdate.setSource(source);
		optionAfterUpdate.setListIndex(DUMMY_LINK_INDEX);
		optionAfterUpdate.read();
		final String expected = CHANGED_TEXT;
		final String actual = optionAfterUpdate.getText();
		final String message = "The changed option text was not correct.";
		assertEquals(message, expected, actual);
	}

}
