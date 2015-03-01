<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
/*
 * The StatsView contains the view logic for displaying statistics about
 * Dendrite. This JSP page should not perform any calculation itself but
 * instead merely call functions from the StatsView. This ensures that the
 * HTML and the Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.StatsView"
%><%

final StatsView view = new StatsView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%>
<h1>Stats</h1><%

view.prepareNumPgs();

%>
<p>Number of pages: ${fn:escapeXml(numPgs)}</p>
<%@include file="bottom.jspf" %>