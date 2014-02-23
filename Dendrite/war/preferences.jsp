<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.PreferencesView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - My Preferences</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final PreferencesView view = new PreferencesView();
    final boolean isUserLoggedIn = PreferencesView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = PreferencesView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = PreferencesView.getMyUserName();
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
      <h2>My Preferences</h2>
      <form action="updatePreferences" method="post">
        <label for="newPenName">Pen name</label>
        <br />
        <input type="text" name="newPenName" id="newPenName"
            value="${userName}"></input>
        <br />
        <button type="submit">Update</button>
      </form>
    </div>
    </div>
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp">About</a></span>
      <span class="footer"><a href="/terms.jsp">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp">Contact</a></span>
    </div>
</html>