<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ReadView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dendrite</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final ReadView view = new ReadView();
    final String pageId = request.getParameter("p");
    view.setPageId(pageId);
    final boolean isUserLoggedIn = ReadView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = ReadView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = ReadView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        
    %>
    <div>Welcome back, <a href="${authorLink}">${userName}</a>.
      (<a href="${logoutLink}">Logout</a>)
    </div><%
    
    } else {
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
    <a href="${loginLink}">Login or register</a><%
	
    }
    final boolean isPageInStore = view.isPageInStore();
    if (isPageInStore == true) {
    	
    	final String text = view.getPageText();
    	pageContext.setAttribute("text", text);
    
	%>
	<div>${text}</div><%
	
    } else {
	
	%>
    <div>This page doesn't appear to be written yet.</div><%
	
    }
	
	%>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>