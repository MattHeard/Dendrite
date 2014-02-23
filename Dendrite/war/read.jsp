<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ReadView"
%><%@ page import="java.util.List"
%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css"><%
    
    final ReadView view = new ReadView();
    final String pageId = request.getParameter("p");
    view.setPageId(pageId);
    
    final boolean isPageInStore = view.isPageInStore();
    if (isPageInStore == true) {
    	final String title = view.getTitle();
    	pageContext.setAttribute("title", title);
    	
    	%>
    <title>Dendrite - ${title}</title><%
    	
    } else {
    	
    	%>
    <title>Dendrite</title><%
    
    }
    
    %>
  </head>
  <body>
    <div id="nonFooter">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final boolean isUserLoggedIn = ReadView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = ReadView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = ReadView.getMyUserName();
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
    <div id="main"><%
    
    if (isPageInStore == true) {
    	final boolean isBeginning = view.isBeginning();
    	if (isBeginning == true) {
    		
    		%>
      <div id="storyTitle"><h2>${title}</h2></div><%
    		
    	}
    	final String pageNumber = view.getPageNumber();
    	pageContext.setAttribute("pageNumber", pageNumber);
    	
    	%>
      <div id="editLink"><a href="/edit.jsp?p=${pageNumber}">Edit</a></div><%
    	
    	final String text = view.getPageText();
    	pageContext.setAttribute("text", text);
    
	    %>
      <div id="text">${text}</div><%
    
    	final int numberOfOptions = view.getNumberOfOptions();
    	for (int i = 0; i < numberOfOptions; i++) {
    		final String optionLink = view.getOptionLink(i);
    		pageContext.setAttribute("optionLink", optionLink);
    		final String optionText = view.getOptionText(i);
    		pageContext.setAttribute("optionText", optionText);
    		
    		%>
      <div class="option"><a href="${optionLink}"<%
    %>>${optionText}</a></div><%

    	}
    	String authorName = view.getAuthorName();
    	if (authorName == null || authorName == "")
    		authorName = "???";
    	pageContext.setAttribute("authorName", authorName);
    	
    	%>
      <div id="credit">This page was written by <%
    
        final boolean isAuthorAnonymous = view.isAuthorAnonymous();
    	if (isAuthorAnonymous == false) {
    		final String authorId = view.getAuthorId();
    		pageContext.setAttribute("authorId", authorId);
    		
    		%><a href="/author.jsp?id=${authorId}"><%
    		
    	}
    
        %>${authorName}<%
    
    	if (isAuthorAnonymous == false) {
    		
    	    %></a><%
    		
    	}
    
        %>.</div><%
    
        if (isBeginning == false) {
        	final String first = view.getFirstUrl();
        	pageContext.setAttribute("first", first);
    
        	%>
      <div><a href="${first}">Return to the first page of this story.</a></div><%
    
        }
	
    } else {
	
	    %>
      <div>This page doesn't appear to be written yet.</div><%
	
    }
	
	%>
    </div>
    </div>
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp">About</a></span>
      <span class="footer"><a href="/terms.jsp">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp">Contact</a></span>
    </div>
  </body>
</html>