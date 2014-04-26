<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AuthorView"
%><%@ page import="java.util.Arrays"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css"><%
    
    final AuthorView view = new AuthorView();
    final String id = request.getParameter("id");
    view.setId(id);
	final String pParameter = request.getParameter("p");
	int authorPageNumber;
	try {
		authorPageNumber = Integer.parseInt(pParameter);
	} catch (NumberFormatException e) {
		authorPageNumber = 1;
	}
	view.setAuthorPageNumber(authorPageNumber);
    final String penName = view.getPenName();
    pageContext.setAttribute("penName", penName);
    
    %>
    <title>Dendrite - ${penName}</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final boolean isUserLoggedIn = AuthorView.isUserLoggedIn();
          
    double userFontSize = 1.0;
    String userFontType = "Sans-serif";
    String userFontColour = "Default";
    double userSpacing = 1.5;
    
    if (isUserLoggedIn == true) {
        final String authorLink = AuthorView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = AuthorView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        userFontSize = AuthorView.getUserFontSize();
        userFontType = AuthorView.getUserFontType();
        userFontColour = AuthorView.getUserFontColour();
        userSpacing = AuthorView.getUserSpacing();
        	
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
      <h2>${penName}</h2><%
    
    final String myUserId = AuthorView.getMyUserId();
    if (id.equals(myUserId) == true) {
    
    	%>
      <div><a class="${fontColourClassName}" href="/preferences.jsp">My
        preferences</a></div><%
    
    }
    
    final List<String> titles = view.getTitles();
    final List<String> summaries = view.getSummaries();
    final List<String> pageIds = view.getPageIds();
    final List<String> authorNames = view.getAuthorNames();
    String prevTitle = null;
    
    for (int i = 0; i < titles.size(); i++) {
    	String currTitle = titles.get(i);
    	if (currTitle == null)
    		currTitle = "";
    	final boolean isDifferentTitle = (currTitle.equals(prevTitle) == false);
    	if (isDifferentTitle == true) {
    		final String title = currTitle;
    		pageContext.setAttribute("title", title);
    		
    		%>
      <h3>${title}</h3><%
	
			prevTitle = currTitle;
    	}
    	String summary = summaries.get(i);
    	if (summary == null)
    		summary = "";
    	pageContext.setAttribute("summary", summary);
    	String pageId = pageIds.get(i);
    	pageContext.setAttribute("pageId", pageId);
    	final String authorName = authorNames.get(i);
    	final boolean isSameAuthorName = (authorName.equals(penName));
    	%>
      <div class="item">
        <div class="itemContent"><a class="${fontColourClassName}"
            href="/read.jsp?p=${pageId}">${summary}</a><%
	
		if (isSameAuthorName == false) {
			pageContext.setAttribute("authorName", authorName);
			
			%> (credited as <i>${authorName}</i>)<%
			
		}
	
	%></div>
        <div class="itemNumber">${pageId}</div>
      </div><%
    }
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	pageContext.setAttribute("id", id);
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
      <div><a class="${fontColourClassName}"
          href="/author.jsp?id=${id}&p=${prev}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	pageContext.setAttribute("id", id);
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
      <div><a class="${fontColourClassName}"
          href="/author.jsp?id=${id}&p=${next}">Next</a></div><%
    	
    }
	
	%>
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
</html>