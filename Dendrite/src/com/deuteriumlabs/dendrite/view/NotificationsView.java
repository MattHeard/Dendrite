/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.Notification;

public class NotificationsView extends View {

    private List<Notification> notifications;
    private int numNotificationsAlreadyDisplayed = 0;

    public long getCurrNotificationId() {
        final List<Notification> notifications = getNotifications();
        final int index = getNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final long id = notification.getId();
        return id;
    }

    public String getCurrNotificationText() {
        final List<Notification> notifications = getNotifications();
        final int index = getNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final String msg = notification.getMsg();
        return msg;
    }

    public List<List<HyperlinkedStr>> getHyperlinkedMsgs() {
        final List<Notification> notifications = getNotifications();
        final List<List<HyperlinkedStr>> msgs;
        msgs = new ArrayList<List<HyperlinkedStr>>();
        for (final Notification notification : notifications) {
            msgs.add(notification.getHyperlinkedMsg());
        }
        return msgs;
    }

    public String getNextNotificationText() {
        final List<Notification> notifications = getNotifications();
        final int index = getNumNotificationsAlreadyDisplayed();
        incrementNumNotificationsAlreadyDisplayed();
        final Notification notification = notifications.get(index);
        final String msg = notification.getMsg();
        return msg;
    }

    public List<Notification> getNotifications() {
        if (notifications == null) {
            readNotifications();
        }
        return notifications;
    }

    @Override
    public String getUrl() {
        return "/notifications";
    }

    public boolean hasAnotherNotification() {
        final int numAlreadyDisplayed;
        numAlreadyDisplayed = getNumNotificationsAlreadyDisplayed();
        final int numToDisplay = getNumNotificationsToDisplay();
        return (numAlreadyDisplayed < numToDisplay);
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite - Notifications");

        super.initialise();
    }

    public void prepareHyperlinkedStr(final HyperlinkedStr h) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("str", h.str);
        pageContext.setAttribute("url", h.url);
    }

    public void prepareNextNotification() {
        final PageContext pageContext = getPageContext();
        final long id = getCurrNotificationId();
        pageContext.setAttribute("id", id);
        final String text = getCurrNotificationText();
        pageContext.setAttribute("text", text);
        incrementNumNotificationsAlreadyDisplayed();
    }

    public void prepareNotification(final Notification notification) {
        final PageContext pageContext = getPageContext();
        final long id = notification.getId();
        pageContext.setAttribute("id", id);
    }

    private int getNumNotificationsAlreadyDisplayed() {
        return numNotificationsAlreadyDisplayed;
    }

    private int getNumNotificationsToDisplay() {
        final List<Notification> notifications = getNotifications();
        return notifications.size();
    }

    private void incrementNumNotificationsAlreadyDisplayed() {
        int num = getNumNotificationsAlreadyDisplayed();
        num++;
        setNumNotificationsAlreadyDisplayed(num);
    }

    private void readNotifications() {
        final List<Notification> notifications;
        final String userId = View.getMyUserId();
        notifications = Notification.getNotificationsForUser(userId);
        setNotifications(notifications);
    }

    private void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }

    private void setNumNotificationsAlreadyDisplayed(final int num) {
        numNotificationsAlreadyDisplayed = num;
    }

    @Override
    protected String getMetaDesc() {
        return "Get alerts about authors you are following and changes to your"
                + " pages.";
    }
}
