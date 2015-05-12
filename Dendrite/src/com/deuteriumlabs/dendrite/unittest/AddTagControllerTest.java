package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.controller.AddTagController;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class AddTagControllerTest {

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
	public final void testAddTag() {
		final AddTagController controller = new AddTagController();
		final PageId pgId = new PageId();
		pgId.setNumber(1);
		pgId.setVersion("a");
        final StoryPage pg = new StoryPage();
        pg.setId(pgId);
        pg.create();
		controller.setPgId(pgId);
		controller.setTag("test");
		boolean isAdded = controller.addTag();
		assertTrue(isAdded);
		
		isAdded = controller.addTag();
		assertFalse(isAdded);
		
        pg.delete();
	}

	@Test
	public final void testSetPgId() {
		final AddTagController controller = new AddTagController();
		final PageId pgId = new PageId();
		pgId.setNumber(1);
		pgId.setVersion("a");
		controller.setPgId(pgId);
		final PageId expected = pgId;
		final PageId actual = controller.getPgId();
		assertEquals(expected, actual);
	}

	@Test
	public final void testSetTag() {
		fail("Not yet implemented"); // TODO
	}

}
