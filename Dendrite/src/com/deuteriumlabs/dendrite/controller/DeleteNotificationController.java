/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.Notification;
import com.deuteriumlabs.dendrite.model.User;

public class DeleteNotificationController {
    private String notificationId;

    public boolean deleteNotification() {
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

    public boolean isMyUserTheRecipient() {
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

    public void setNotificationId(final String notificationId) {
        this.notificationId = notificationId;
    }
}
