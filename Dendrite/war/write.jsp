<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.WriteView"
%><%@ page import="java.util.Arrays"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - Write</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final WriteView view = new WriteView();
	final String from = request.getParameter("from");
	view.setFrom(from);
	final String linkIndex = request.getParameter("linkIndex");
	view.setLinkIndex(linkIndex);
    final String error = request.getParameter("error");
    view.setError(error);
    
    final boolean isUserLoggedIn = WriteView.isUserLoggedIn();
    
    double userFontSize = 1.0;
    String userFontType = "Sans-serif";
    String userFontColour = "Default";
    double userSpacing = 1.5;
    String userAlignment = "Justify";
    
    if (isUserLoggedIn == true) {
        final String authorLink = WriteView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = WriteView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        userFontSize = WriteView.getUserFontSize();
        userFontType = WriteView.getUserFontType();
        userFontColour = WriteView.getUserFontColour();
        userSpacing = WriteView.getUserSpacing();
        userAlignment = WriteView.getUserAlignment();
        
    %>
      <div id="logout">Welcome back, <a href="${authorLink}">${userName}</a>.
          (<a href="${logoutLink}">Logout</a>)</div><%
    
    } else {
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
      <div id="login"><a href="${loginLink}">Login or register</a></div><%
    
    }
    final boolean isValidOption = view.isValidOption();
   	if (isValidOption == true) {
   		final String optionText = view.getOptionText();
   		pageContext.setAttribute("optionText", optionText);
   		
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
        
        if ("Justify".equals(userAlignment) == false) {
          String alignmentClassName = "alignment";
          final String[] alignmentOptions = { "Left", "Right", "Center",
              "Justify" };
          final List<String> list = Arrays.asList(alignmentOptions);
          if (list.contains(userAlignment)) {
            alignmentClassName += userAlignment;
          } else {
            alignmentClassName += "Default";
          }
          pageContext.setAttribute("alignmentClassName", alignmentClassName);
          
          %> ${alignmentClassName}<%
        }
      
      %>">
      <h3>${optionText}</h3>
      <form action="submitWrite" method="post"><%
    
	    pageContext.setAttribute("from", from);
	    pageContext.setAttribute("linkIndex", linkIndex);
    
    %>
        <input type="hidden" name="from" value="${from}" />
        <input type="hidden" name="linkIndex" value="${linkIndex}" />
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