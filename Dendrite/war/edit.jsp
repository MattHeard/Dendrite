<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.EditView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - Edit</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final EditView view = new EditView();
	final String pageNumber = request.getParameter("p");
	view.setPageNumber(pageNumber);
    final String error = request.getParameter("error");
    view.setError(error);
    
    final boolean isUserLoggedIn = EditView.isUserLoggedIn();
    double userFontSize = 1.0;
    String userFontType = "Sans-serif";
    if (isUserLoggedIn == true) {
        final String authorLink = EditView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = EditView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        userFontSize = EditView.getUserFontSize();
        userFontType = EditView.getUserFontType();
        
    %>
      <div id="logout">Welcome back, <a href="${authorLink}">${userName}</a>.
          (<a href="${logoutLink}">Logout</a>)</div><%
    
    } else {
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
      <div id="login"><a href="${loginLink}">Login or register</a></div><%
    
    }
    final boolean isExistingPage = view.isExistingPage();
    if (isExistingPage == true) {
    	pageContext.setAttribute("pageNumber", pageNumber);
    	
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
      
      %>">
      <h2>Edit a page</h2>
      <form action="submitEdit" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber}" />
        <label for="content">Story</label>
        <br />
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
      </form><%
    	
    } else {
    	
    	%>
    Oh. What happened? This doesn't seem to be the right page.<%
    	
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
  </body>
</html>