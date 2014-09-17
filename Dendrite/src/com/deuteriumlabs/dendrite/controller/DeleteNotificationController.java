/**
 * 
 */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.Notification;
import com.deuteriumlabs.dendrite.model.User;

/**
 * 
 */
public class DeleteNotificationController {

	private String notificationId;

	/**
	 * @param notificationId
	 */
	public void setNotificationId(final String notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * @return
	 */
	public boolean deleteNotification() {
		final String notificationId = this.getNotificationId();
		final boolean isDeletionSuccessful;
		if (notificationId != null) {
			final Notification notification = new Notification();
			notification.setId(notificationId);
			notification.deleteById();
			isDeletionSuccessful = true;
		} else {
			isDeletionSuccessful = false;
		}
		return isDeletionSuccessful;
	}

	/**
	 * @return
	 */
	private String getNotificationId() {
		return this.notificationId;
	}

	public boolean isMyUserTheRecipient() {
		final String notificationId = this.getNotificationId();
		if (notificationId != null) {
			final Notification notification = new Notification();
			notification.setId(notificationId);
			notification.read();
			final String recipientId = notification.getRecipientId();
			final String myUserId = User.getMyUserId();
			return (recipientId.equals(myUserId));
		} else {
			return false;
		}
	}
}
