<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" 
%><%@ page import="com.deuteriumlabs.dendrite.view.WriteView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Dendrite</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final WriteView view = new WriteView();
	final String from = request.getParameter("from");
	view.setFrom(from);
	final String linkIndex = request.getParameter("linkIndex");
	view.setLinkIndex(linkIndex);
    final boolean isUserLoggedIn = WriteView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = WriteView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = WriteView.getMyUserName();
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
    <a href="${loginLink}">Login or register</a>
    <form><%
    
    }
    final boolean isNewStory = view.isNewStory();
   	if (isNewStory == true) {
    
    %>
      <label for="title">Title: </label>
      <input id="title" type="text"></input><%
    
   	} else {
   		final boolean isValidOption = view.isValidOption();
   		if (isValidOption == true) {
    
    %>
    <label>Valid option</label><%
    
   		} else {
   			
   			%>
    <label>Invalid option</label><%
   			
   		}
   	}
    
    %>
      <button type="submit">Submit</button>
    </form>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>