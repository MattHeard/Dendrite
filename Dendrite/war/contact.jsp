<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContactView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - Contact us</title>
  </head>
  <body>
    <div id="header">
      <div id="logo"><h1><a href="/">Dendrite</a></h1></div><%
    
    final ContactView view = new ContactView();
    final boolean isUserLoggedIn = ContactView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = ContactView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = ContactView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        
    %>
      <div id="logout">Welcome back, <a href="${authorLink}">${userName}</a>.
          (<a href="${logoutLink}">Logout</a>)</div><%
    
    } else {
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
      <div id="login"><a href="${loginLink}">Login or register</a></div><%
    
    }
    
    %>
    </div>
    <div id="main">
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
    </div>
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp">About</a></span>
      <span class="footer"><a href="/terms.jsp">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp">Contact</a></span>
    </div>
  </body>
</html>