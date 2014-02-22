<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <title>Dendrite</title>
  </head>
  <body>
    <h1><a href="/">Dendrite</a></h1><%
    	final ContentsView view = new ContentsView();
    	final String pParameter = request.getParameter("p");
    	int contentsPageNumber;
    	try {
    		contentsPageNumber = Integer.parseInt(pParameter);
    	} catch (NumberFormatException e) {
    		contentsPageNumber = 1;
    	}
    	view.setContentsPageNumber(contentsPageNumber);
        final boolean isUserLoggedIn = ContentsView.isUserLoggedIn();
        if (isUserLoggedIn == true) {
        	final String authorLink = ContentsView.getAuthorLink();
        	pageContext.setAttribute("authorLink", authorLink);
        	final String userName = ContentsView.getMyUserName();
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
	
    final List<String> links = view.getLinks();
    final int length = links.size();
	final List<String> titles = view.getTitles();
    final List<String> pageNumbers = view.getPageNumbers();
    for (int i = 0; i < length; i++) {
    	final String link = links.get(i);
    	pageContext.setAttribute("link", link);
    	final String title = titles.get(i);
    	pageContext.setAttribute("title", title);
    	final String pageNumber = pageNumbers.get(i);
    	pageContext.setAttribute("pageNumber", pageNumber);
    	%>
    <div><a href="${link}">${title}</a> - ${pageNumber}</div><%
    }
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
    <div><a href="/index.jsp?p=${prev}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
    <div><a href="/index.jsp?p=${next}">Next</a></div><%
    	
    }
	
	%>
    <div><a href="/new.jsp">Start a new story</a></div>
    <div><a href="/about.jsp">About</a></div>
    <div><a href="/terms.jsp">Terms of use</a></div>
    <div><a href="/privacy.jsp">Privacy</a></div>
    <div><a href="/contact.jsp">Contact</a></div>
  </body>
</html>