/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.controller.DeleteNotificationController;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class DeleteNotificationControllerTest {

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
	public final void testSetNotificationId() {
		final DeleteNotificationController controller;
		controller = new DeleteNotificationController();
		final String notificationId = "1234";
		controller.setNotificationId(notificationId);
		final String expected = notificationId;
		final String actual = controller.getNotificationId();
		assertEquals(expected, actual);
	}

//	@Test
//	public final void testDeleteNotification() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testIsMyUserTheRecipient() {
//		fail("Not yet implemented"); // TODO
//	}
}
