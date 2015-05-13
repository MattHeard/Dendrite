/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.controller.DeleteNotificationController;
import com.deuteriumlabs.dendrite.model.Notification;
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

	@Test
	public final void testDeleteNotification() {
		final Notification notification = new Notification();
		notification.setRecipientId("1234");
		notification.create();
		notification.read();
		final long notificationId = notification.getId();
		final DeleteNotificationController controller;
		controller = new DeleteNotificationController();
		controller.setNotificationId(Long.toString(notificationId));
		final boolean isDeleted = controller.deleteNotification();
		assertTrue(isDeleted);
	}

//	@Test
//	public final void testIsMyUserTheRecipient() {
//		fail("Not yet implemented"); // TODO
//	}
}
