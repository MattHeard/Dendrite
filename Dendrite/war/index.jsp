<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"
%><%@ page import="java.util.Arrays"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    	final ContentsView view = new ContentsView();
    	final String pParameter = request.getParameter("p");
    	int contentsPageNumber;
    	try {
    		contentsPageNumber = Integer.parseInt(pParameter);
    	} catch (NumberFormatException e) {
    		contentsPageNumber = 1;
    	}
    	view.setContentsPageNumber(contentsPageNumber);
    	
      double userFontSize = 1.0;
      String userFontType = "Sans-serif";
      String userFontColour = "Default";
      double userSpacing = 1.5;
      
        final boolean isUserLoggedIn = ContentsView.isUserLoggedIn();
        if (isUserLoggedIn == true) {
        	final String authorLink = ContentsView.getAuthorLink();
        	pageContext.setAttribute("authorLink", authorLink);
        	final String userName = ContentsView.getMyUserName();
        	pageContext.setAttribute("userName", userName);
        	final String logoutLink = view.getLogoutLink();
        	pageContext.setAttribute("logoutLink", logoutLink);
          userFontSize = ContentsView.getUserFontSize();
          userFontType = ContentsView.getUserFontType();
          userFontColour = ContentsView.getUserFontColour();
          userSpacing = ContentsView.getUserSpacing();
        	
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
    </div>
    <div id="main" class="modifiableText<%
      
        if (userFontSize != 1.0) {
          String sizeClassName = "size";
          if (userFontSize == 2) {
            sizeClassName += "Huge";
          } else if (userFontSize == 1.5) {
            sizeClassName += "Large";
          } else if (userFontSize == 0.8) {
            sizeClassName += "Small";
          } else {
            sizeClassName += "Medium";
          }
          pageContext.setAttribute("sizeClassName", sizeClassName);
          
          %> ${sizeClassName}<%
          
        }
    
		    if ("Sans-serif".equals(userFontType) == false) {
		      String fontTypeClassName = "fontType";
		      if ("Serif".equals(userFontType)) {
		        fontTypeClassName += "Serif";
		      } else if ("Monospace".equals(userFontType)) {
		        fontTypeClassName += "Monospace";
		      } else if ("Cursive".equals(userFontType)) {
		        fontTypeClassName += "Cursive";
		      } else if ("Fantasy".equals(userFontType)) {
		        fontTypeClassName += "Fantasy";
		      }
		      pageContext.setAttribute("fontTypeClassName", fontTypeClassName);
		      
		      %> ${fontTypeClassName}<%
		      
		    }
	        
        if ("Default".equals(userFontColour) == false) {
          String fontColourClassName = "fontColour";
          final String[] fontColourOptions = { "Default", "Charcoal", "Black",
                    "Grey", "Blue", "Green", "Red" };
          final List<String> list = Arrays.asList(fontColourOptions);
          if (list.contains(userFontColour)) {
            fontColourClassName += userFontColour;
          } else {
            fontColourClassName += "Default";
          }
          pageContext.setAttribute("fontColourClassName", fontColourClassName);
          
          %> ${fontColourClassName}<%
        } else {
        	pageContext.setAttribute("fontColourClassName", "");
        }
        
        if (userSpacing != 1.5) {
          String spacingClassName = "spacing";
          if (userSpacing == 3.0) {
            spacingClassName += "Huge";
          } else if (userSpacing == 2.0) {
            spacingClassName += "Large";
          } else if (userSpacing == 1.0) {
            spacingClassName += "Small";
          } else {
            spacingClassName += "Medium";
          }
          pageContext.setAttribute("spacingClassName", spacingClassName);
          
          %> ${spacingClassName}<%
            
        }
      
      %>">
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
      <div class="item">
        <div class="itemContent"><a class="${fontColourClassName}" href="${link}">${title}</a></div>
        <div class="itemNumber">${pageNumber}</div>
      </div><%
    }
    
    %>
      <p><%
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
        <div><a class="${fontColourClassName}" href="/index.jsp?p=${prev}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
        <div><a class="${fontColourClassName}" href="/index.jsp?p=${next}">Next</a></div><%
    	
    }
	
	%>
      </p>
      <p id="newStory"><a class="${fontColourClassName}" href="/new.jsp">Start a new story</a></p>
    </div>
    </div>
    <div id="footerBar">
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp">About</a></span>
      <span class="footer"><a href="/terms.jsp">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp">Contact</a></span>
    </div>
    </div>
  </body>
</html>