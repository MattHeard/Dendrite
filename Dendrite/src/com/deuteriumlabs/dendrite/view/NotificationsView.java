package com.deuteriumlabs.dendrite.view;

import java.util.List;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.Notification;

public class NotificationsView extends View {

    private int numNotificationsAlreadyDisplayed = 0;
    private List<Notification> notifications;

    @Override
    String getUrl() {
        return "/notifications";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite - Notifications");
    }
    
    public boolean hasAnotherNotification() {
        final int numAlreadyDisplayed;
        numAlreadyDisplayed = this.getNumNotificationsAlreadyDisplayed();
        final int numToDisplay = this.getNumNotificationsToDisplay();
        return (numAlreadyDisplayed < numToDisplay);
    }
    
    /**
     * @return
     */
    private int getNumNotificationsToDisplay() {
        final List<Notification> notifications = this.getNotifications();
        return notifications.size();
    }

    /**
     * @return
     */
    private List<Notification> getNotifications() {
        if (this.notifications == null) {
            this.readNotifications();
        }
        return this.notifications;
    }

    /**
     * 
     */
    private void readNotifications() {
        final List<Notification> notifications;
        final String userId = NotificationsView.getMyUserId();
        notifications = Notification.getNotificationsForUser(userId);
        this.setNotifications(notifications);
    }

    /**
     * @param notifications
     */
    private void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return
     */
    private int getNumNotificationsAlreadyDisplayed() {
        return this.numNotificationsAlreadyDisplayed;
    }

    public String getNextNotificationText() {
        final List<Notification> notifications = this.getNotifications();
        final int index = this.getNumNotificationsAlreadyDisplayed();
        this.incrementNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final String msg = notification.getMsg();
        return msg;
    }
    
    public void prepareNextNotification() {
        this.incrementNumNotificationsAlreadyDisplayed();
    }
    
    public long getCurrNotificationId() {
        final List<Notification> notifications = this.getNotifications();
        final int index = this.getNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final long id = notification.getId();
        return id;
    }

    public String getCurrNotificationText() {
        final List<Notification> notifications = this.getNotifications();
        final int index = this.getNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final String msg = notification.getMsg();
        return msg;
    }

    /**
     * 
     */
    private void incrementNumNotificationsAlreadyDisplayed() {
        int num = this.getNumNotificationsAlreadyDisplayed();
        num++;
        this.setNumNotificationsAlreadyDisplayed(num);
    }

    /**
     * @param num
     */
    private void setNumNotificationsAlreadyDisplayed(final int num) {
        this.numNotificationsAlreadyDisplayed = num;
    }
}
