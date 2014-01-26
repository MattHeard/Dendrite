package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserTest {
	private static final String CHANGED_DEFAULT_PEN_NAME = "Bob Saget";
	private static final String DUMMY_DEFAULT_PEN_NAME = "John Smith";
	private static final String DUMMY_ID = "1234567890";
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private void createDummyUser() {
		final User user = new User();
		user.setId(DUMMY_ID);
		user.setDefaultPenName(DUMMY_DEFAULT_PEN_NAME);
		user.create();
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
		final User user = new User();
		user.setId(DUMMY_ID);
		user.setDefaultPenName(DUMMY_DEFAULT_PEN_NAME);

		user.create();

		final boolean isUserInStore = user.isInStore();
		final String message = "The user was not in the store.";
		assertTrue(message, isUserInStore);
	}

	@Test
	public final void testDelete() {
		createDummyUser();
		
		final User user = new User();
		user.setId(DUMMY_ID);
		user.delete();
		
		final boolean isUserInStore = user.isInStore();
		final String message = "The user should not be in the store.";
		assertFalse(message, isUserInStore);
	}
	
	@Test
	public final void testRead() {
		createDummyUser();
		final User user = new User();
		user.setId(DUMMY_ID);
		user.read();

		final String expected = DUMMY_DEFAULT_PEN_NAME;
		final String actual = user.getDefaultPenName();
		final String message = "The default pen name was not correct.";
		assertEquals(message, expected, actual);
	}

	@Test
	public final void testUpdate() {
		createDummyUser();

		final User userBeforeUpdate = new User();
		userBeforeUpdate.setId(DUMMY_ID);
		userBeforeUpdate.read();

		userBeforeUpdate.setDefaultPenName(CHANGED_DEFAULT_PEN_NAME);
		userBeforeUpdate.update();

		final User userAfterUpdate = new User();
		userAfterUpdate.setId(DUMMY_ID);
		userAfterUpdate.read();
		final String expected = CHANGED_DEFAULT_PEN_NAME;
		final String actual = userAfterUpdate.getDefaultPenName();
		final String message = "The changed default pen name was not correct.";
		assertEquals(message, expected, actual);
	}

}
