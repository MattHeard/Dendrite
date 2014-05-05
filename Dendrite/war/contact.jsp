<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContactView"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Contact us");
final View view = new ContactView();

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