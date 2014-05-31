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
%><%

final ContactView view = new ContactView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top.jspf"

%>
    <h2>Contact us</h2>
      <form action="mailto:matt+dendrite@mattheard.net" method="get"
          enctype="text/plain">
        <label for="subject">Subject</label>
        <br />
        <input id="subject" name="subject"></input>
        <br />
        <label for="body">Message</label>
        <br />
        <textarea id="body" name="body"></textarea>
        <br />
        <button type="reset">Reset</button>
        <button type="submit">Send</button>
      </form>
<%@include file="bottom.jspf" %>