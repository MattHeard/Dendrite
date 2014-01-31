<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AuthorView"
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><%
    
    final AuthorView view = new AuthorView();
    final String id = request.getParameter("id");
    view.setId(id);
    final String penName = view.getPenName();
    pageContext.setAttribute("penName", penName);
    
    %>
    <title>Dendrite - ${penName}</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final boolean isUserLoggedIn = AuthorView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = AuthorView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = AuthorView.getMyUserName();
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
    <h2>${penName}</h2>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>