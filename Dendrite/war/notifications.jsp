<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
/*
 * The NotificationsView contains the view logic for the pages describing the
 * users of Dendrite. This JSP page should not perform any calculation itself
 * but instead merely call functions from the ContactView. This ensures that the
 * HTML and the Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.NotificationsView"
%><%

final NotificationsView view = new NotificationsView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top.jspf"

%>
    <ul id="notificationList"><%

while (view.hasAnotherNotification() == true) {
    long id = view.getCurrNotificationId();
    String text = view.getCurrNotificationText();
    view.prepareNextNotification();
    
    %>
      <li class="notificationItem"
          id="notification<%= id %>"><%= text %>
        <img id="notificationDelete<%= id %>"
            class="notificationDelete"
            src="/icons/close_notification.png" /></li><%
    
}
    
    %>
    </ul>
<%@include file="bottom.jspf" %>