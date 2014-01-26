package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserTest {
	private static final String DUMMY_ID = "1234567890";
	private static final String DUMMY_DEFAULT_PEN_NAME = "John Smith";
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
		final User user = new User();
		user.setId(DUMMY_ID);
		user.setDefaultPenName(DUMMY_DEFAULT_PEN_NAME);
		
		user.create();
		
		final boolean isUserInStore = user.isInStore();
		final String message = "The user was not in the store.";
		assertTrue(message, isUserInStore);
	}

}
