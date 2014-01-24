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
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PageId getDummyPageId() {
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
		
		page.create();
		
		final boolean isPageInStore = page.isInStore();
		final String message = "The story page was not in the store.";
		assertTrue(message, isPageInStore);
	}

}
