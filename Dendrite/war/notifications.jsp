<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%/*
 * The NotificationsView contains the view logic for the pages describing the
 * users of Dendrite. This JSP page should not perform any calculation itself
 * but instead merely call functions from the ContactView. This ensures that the
 * HTML and the Java components are cleanly separated.
 */%><%@ page import="com.deuteriumlabs.dendrite.model.Notification"
%><%@ page import="com.deuteriumlabs.dendrite.view.HyperlinkedStr"
%><%@ page import="com.deuteriumlabs.dendrite.view.NotificationsView"
%><%

final NotificationsView view = new NotificationsView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf" %><%

final boolean hasAnyNotifications = view.hasAnotherNotification();
if (hasAnyNotifications == false) {%>
    <p class="notice">Oh, hello! It looks like you have no new
        notifications.</p><%
    	} else {
    %>
    <ul id="notificationList"><%
    	final List<Notification> notifications = view.getNotifications();
        for (final Notification notification : notifications) {
        	view.prepareNotification(notification);
    %>
      <li class="notificationItem"
          id="notification${fn:escapeXml(id)}"><%
      	final List<HyperlinkedStr> msg = notification.getHyperlinkedMsg();
              for (final HyperlinkedStr chunk : msg) {
              	view.prepareHyperlinkedStr(chunk);
              	
              	if (chunk.url != null) {
      %><a href='${fn:escapeXml(url)}'><%
        		
        	}
        	
        	%>${fn:escapeXml(str)}</a><%
        	
            if (chunk.url != null) {
                
                %></a><%
                
            }
        }
          
        %>
        <img id="notificationDelete${fn:escapeXml(id)}"
            class="notificationDelete"
            src="/icons/close_notification.png" /></li><%
    	
    }

    %>
    </ul><%

}

%>
<%@include file="bottom.jspf" %>