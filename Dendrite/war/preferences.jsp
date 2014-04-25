<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.PreferencesView"
%><%@ page import="java.util.Arrays"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - My Preferences</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final PreferencesView view = new PreferencesView();
          
    double userFontSize = 1.0;
    String userFontType = "Sans-serif";
    String userFontColour = "Default";
    
    final boolean isUserLoggedIn = PreferencesView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = PreferencesView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = PreferencesView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        userFontSize = PreferencesView.getUserFontSize();
        userFontType = PreferencesView.getUserFontType();
        userFontColour = PreferencesView.getUserFontColour();
        
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
        }
      
      %>">
      <h2>My Preferences</h2>
      <form action="updatePreferences" method="post">
      	<p>
          <label for="newPenName">Pen name</label>
          <br />
          <input type="text" name="newPenName" id="newPenName"
              value="${userName}"></input>
        </p>
        <h3>Display</h3>
        <p>
          <label for="fontSize">Font size</label>
          <br />
          <select name="fontSize" id="fontSize"><%
          
          final String[] fontSizeOptions = { "Huge", "Large", "Medium", "Small" };
          final double[] fontSizeValues = { 2.0, 1.5, 1.0, 0.8 };
          
          for (int i = 0; i < fontSizeValues.length; i++) {
        	  pageContext.setAttribute("fontSizeOption", fontSizeOptions[i]);
        	  
        	  %>
        	  <option<%
        	  
        	  if (fontSizeValues[i] == userFontSize) {
        		  %> selected="selected"<%
        	  }
        	  %>>${fontSizeOption}</option><%
        	  
          }
          
          %>
          </select>
        </p>
        <p>
          <label for="fontType">Font type</label>
          <br />
          <select name="fontType" id="fontType"><%
          
          final String[] fontTypeOptions = { "Serif", "Sans-serif", "Monospace",
        		      "Cursive", "Fantasy" };
          
          for (int i = 0; i < fontTypeOptions.length; i++) {
        	  pageContext.setAttribute("fontTypeOption", fontTypeOptions[i]);
        	  
        	  %>
            <option<%
            
            if (fontTypeOptions[i].equals(userFontType)) {
            	%> selected="selected"<%
            }
            
            %>>${fontTypeOption}</option><%
          }
          
          %>
          </select>
        </p>
        <p>
          <label for="fontColour">Font colour</label>
          <br />
          <select name="fontColour" id="fontColour"><%
          
          final String[] fontColourOptions = { "Default", "Charcoal", "Black",
        		      "Grey", "Blue", "Green", "Red" };
          
          for (int i = 0; i < fontColourOptions.length; i++) {
        	  pageContext.setAttribute("fontColourOption", fontColourOptions[i]);
        	  
        	  %>
            <option<%
            
            if (fontColourOptions[i].equals(userFontColour)) {
            	%> selected="selected"<%
            }
            
            %>>${fontColourOption}</option><%
          }
          
          %>
          </select>
        </p>
        <p>
          <label for="spacing">Line spacing</label>
          <br />
          <select name="spacing" id="spacing">
            <option>Huge</option>
            <option>Large</option>
            <option>Medium</option>
            <option>Small</option>
          </select>
        </p>
        <p>
          <label for="alignment">Text alignment</label>
          <br />
          <select name="alignment" id="alignment">
            <option>Left</option>
            <option>Right</option>
            <option>Center</option>
            <option>Justify</option>
          </select>
        </p>
        <p>
          <label for="theme">Theme</label>
          <br />
          <select name="theme" id="theme">
            <option>Light</option>
            <option>Dark</option>
            <option>Sepia</option>
            <option>Lovely</option>
          </select>
        </p>
        <button type="submit">Update</button>
      </form>
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