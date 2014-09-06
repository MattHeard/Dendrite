<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
/*
 * The AboutView contains the view logic for the pages describing the users of
 * Dendrite. This JSP page should not perform any calculation itself but instead
 * merely call functions from the AboutView. This ensures that the HTML and the
 * Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.PatchesView"
%><%

final PatchesView view = new PatchesView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%>
        <ul class="tabs">
          <li><a href="about">About</a></li>
          <li><a href="contributors">Contributors</a></li>
          <li><a href="faq">FAQ</a></li>
          <li class="selected">Patches</li>
          <li id="more"><a href="about_more">⋮</a></li>
        </ul>
        <div class="clear"></div>
        <p>Welcome, you adventurous devil! Anything and everything could (and
          probably will) change at any second. We're moving too fast to know
          what's going on, but once we're more stable, we're going to publish
          descriptions of all of our updates here. Bug fixes, new features,
          minor tweaks. We'll put a little notice here so you can see what's
          new. But not yet. Soon...</p>
<%@include file="bottom.jspf" %>