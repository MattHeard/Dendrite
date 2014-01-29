<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"
%><%@ page import="java.util.List"

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
    	final String authorLink = ContentsView.getAuthorLink();
    	pageContext.setAttribute("authorLink", authorLink);
    	final String userName = ContentsView.getUserName();
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
    
	%>
    <h2>Table of Contents</h2><%
	
	final List<String> titles = view.getTitles();
    final int length = titles.size();
    final List<String> pageNumbers = view.getPageNumbers();
    for (int i = 0; i < length; i++) {
    	final String title = titles.get(i);
    	pageContext.setAttribute("title", title);
    	final String pageNumber = pageNumbers.get(i);
    	pageContext.setAttribute("pageNumber", pageNumber);
    	%>
    <div>${title} - ${pageNumber}</div><%
    }
	
	%>
    <div><a href="/write.jsp?from=0">Start a new story</a></div>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>