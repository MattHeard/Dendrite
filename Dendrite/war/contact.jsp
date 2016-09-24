<% /* © 2013-2015 Deuterium Labs Limited */ %>
<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
/*
 * The ContactView contains the view logic for the pages describing the users of
 * Dendrite. This JSP page should not perform any calculation itself but instead
 * merely call functions from the ContactView. This ensures that the HTML and
 * the Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.ContactView"
%><%@ page import="com.deuteriumlabs.dendrite.model.User"
%><%

final User myUser = User.getMyUser();
final ContactView view = new ContactView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%>
    <iframe
        src="https://docs.google.com/forms/d/e/1FAIpQLScj5TYaqYYudMybmDyTiAj6c7jIcg36sIpUI2VHtH_fm9lc-Q/viewform?embedded=true"
        width="800"
        height="710"
        frameborder="0"
        marginheight="0"
        marginwidth="0">Loading...</iframe>
<%@include file="bottom.jspf" %>