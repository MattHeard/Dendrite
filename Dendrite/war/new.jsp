<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.NewView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dendrite - New Story</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final NewView view = new NewView();
    final String error = request.getParameter("error");
    view.setError(error);

    final boolean isUserLoggedIn = NewView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = NewView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = NewView.getMyUserName();
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
    <form action="submitNewStory" method="post">
      <label for="title">Title</label>
      <br />
      <input id="title" name="title" type="text"></input><%
      
	final boolean isTitleBlank = view.isTitleBlank();
	final boolean isTitleTooLong = view.isTitleTooLong();
	if (isTitleBlank == true) {

		%>
      <i>Must not be blank</i><%
		
	} else if (isTitleTooLong == true) {
		
		%>
      <i>Must not be longer than 100 characters</i><%
		
	}
      
	%>
      <br />
      <label for="content">Story</label><br />
      <textarea id="content" name="content"></textarea><%
      
	final boolean isContentBlank = view.isContentBlank();
	final boolean isContentTooLong = view.isContentTooLong();
	if (isContentBlank == true) {
		
		%>
      <i>Must not be blank</i><%
		
	} else if (isContentTooLong == true) {
		
		%>
      <i>Must not be longer than 5000 characters</i><%
		
	}
      
	%>
      <br />
      <label for="option0">Options</label><%
      
	final boolean isAnOptionTooLong = view.isAnOptionTooLong();
	if (isAnOptionTooLong == true) {

		%>
      <i>Must not be longer than 80 characters</i><%
  		
  	}
  	
  	%>
      <br /><%

    for (int i = 0; i < 5; i++) {
        pageContext.setAttribute("optionNumber", i);
        	
    %>
      <input id="option${optionNumber}" name="option${optionNumber}"<%
      %> type="text"></input>
      <br /><%
        	
    }
    if (isUserLoggedIn == true) {
    	final String authorId = view.getMyUserId();
    	pageContext.setAttribute("authorId", authorId);
      
    %>
      <input name="authorId" type="hidden" value="${authorId}" /><%
      
    }
    
      %>
      <label for="authorName">Author</label>
      <br />
      <input id="authorName" name="authorName" type="text"<%
      
    if (isUserLoggedIn == true) {
      
      %> value="${userName}"<%
      
    } else {
		
      %> value="???"<%
		
	}
      
	%>></input><%
	
	final boolean isAuthorNameBlank = view.isAuthorNameBlank();
	final boolean isAuthorNameTooLong = view.isAuthorNameTooLong();
	if (isAuthorNameBlank == true) {

		%>
      <i>Must not be blank</i><%
		
	} else if (isAuthorNameTooLong == true) {
		
		%>
      <i>Must not be longer than 100 characters</i><%
		
	}
      
      %>
      <br />
      <button type="submit">Submit</button>
    </form>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>