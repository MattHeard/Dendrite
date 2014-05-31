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
%><%@ page import="com.deuteriumlabs.dendrite.view.AboutView"
%><%

final AboutView view = new AboutView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top.jspf"

%>
        <div id="tabs">
          <a href="about.jsp">About</a>
          <a class="selected_tab">Contributors</a>
          <a id="more" href="about_more.jsp">⋮</a>
        </div>
        <div class="clear"></div>
        <ul>
          <li><b>Leah Hamilton</b>, helped with everything</li>
          <li><b>Ryan Hamilton</b>, design</li>
        </ul>
<%@include file="bottom.jspf" %>