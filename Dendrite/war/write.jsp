<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.WriteView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dendrite - Write</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    
    final WriteView view = new WriteView();
	final String from = request.getParameter("from");
	view.setFrom(from);
	final String linkIndex = request.getParameter("linkIndex");
	view.setLinkIndex(linkIndex);
    final String error = request.getParameter("error");
    view.setError(error);
    
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
    <a href="${loginLink}">Login or register</a><%
    
    }
    final boolean isValidOption = view.isValidOption();
   	if (isValidOption == true) {
   		final String optionText = view.getOptionText();
   		pageContext.setAttribute("optionText", optionText);
   		
    %>
    <div>${optionText}</div>
    <form action="submitWrite" method="post"><%
    
	    pageContext.setAttribute("from", from);
	    pageContext.setAttribute("linkIndex", linkIndex);
    
    %>
      <input type="hidden" name="from" value="${from}" />
      <input type="hidden" name="linkIndex" value="${linkIndex}" />
      <label for="content">Story</label>
      <br />
      <textarea id="content" name="content"></textarea><%
      
	final boolean isThereABlankContentError = view.isThereABlankContentError();
	if (isThereABlankContentError == true) {
		
		%>
      <i>Must not be blank</i><%
		
	}
      
      %>
      <br />
      <label for="option0">Options</label>
      <br /><%
      
		for (int i = 0; i < 5; i++) {
			pageContext.setAttribute("optionNumber", i);
        	
        	%>
      <input id="option${optionNumber}" name="option${optionNumber}"
          type="text"></input>
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
      
	final boolean isThereABlankAuthorError = view.isThereABlankAuthorError();
	if (isThereABlankAuthorError == true) {
		
		%>
      <i>Must not be blank</i><%
		
	}
      
      %>
      <br />
      <button type="submit">Submit</button>
    </form><%
      
	} else {
   			
   	  %>
    Oh. What happened? This doesn't seem to be the right page.<%
   	
   	}
    
    %>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>