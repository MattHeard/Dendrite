<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Dendrite</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final ContentsView view = new ContentsView();
    final boolean isUserLoggedIn = ContentsView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
    	
    	final String authorLink = view.getAuthorLink();
    	pageContext.setAttribute("authorLink", authorLink);
    	final String userName = view.getUserName();
    	pageContext.setAttribute("userName", userName);
    	final String logoutLink = view.getLogoutLink();
    	pageContext.setAttribute("logoutLink", logoutLink);
    
        %>
    Welcome back, <a href="${authorLink}">${userName}</a>. (<a href="${logoutLink}">Logout</a>)<%
    
    } else {
    	
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
    <a href="${loginLink}">Login or register</a><%
	
    }
    
	%>
  </body>
</html>