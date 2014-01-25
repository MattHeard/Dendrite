package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class StoryOptionTest {

	private static final int DUMMY_LINK_INDEX = 0;
	
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
		final StoryOption option = new StoryOption();
		final PageId id = getDummyPageId();
		option.setSource(id);
		option.setListIndex(DUMMY_LINK_INDEX);
		
		option.create();
		
		final boolean isOptionInStore = option.isInStore();
		final String message = "The story option was not in the store.";
		assertTrue(message, isOptionInStore);
	}

}
