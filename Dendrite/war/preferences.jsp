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
  <body<%
  
final PreferencesView view = new PreferencesView();

// Set up default display settings.
double userFontSize = 1.0;
String userFontType = "Sans-serif";
String userFontColour = "Default";
double userSpacing = 1.5;
String userAlignment = "Justify";
String userTheme = "Light";

final boolean isUserLoggedIn = PreferencesView.isUserLoggedIn();

if (isUserLoggedIn == true) {

    // Replace default display settings with user preferences.
    final String authorLink = PreferencesView.getAuthorLink();
    pageContext.setAttribute("authorLink", authorLink);
    final String userName = PreferencesView.getMyUserName();
	pageContext.setAttribute("userName", userName);
	final String logoutLink = view.getLogoutLink();
	pageContext.setAttribute("logoutLink", logoutLink);
	userFontSize = PreferencesView.getUserFontSize();
	userFontType = PreferencesView.getUserFontType();
	userFontColour = PreferencesView.getUserFontColour();
	userSpacing = PreferencesView.getUserSpacing();
	userAlignment = PreferencesView.getUserAlignment();
    userTheme = PreferencesView.getUserTheme();
}

if (userTheme.equals("Light") == false && userTheme != null) {
	String className = "theme";
	final String[] options = { "Light", "Dark", "Sepia", "Lovely" };
	final List<String> list = Arrays.asList(options);
	if (list.contains(userTheme)) {
	    className += userTheme;
	} else {
	    className += "Light";
	}
    pageContext.setAttribute("themeClassName", className);

    %> class="${themeClassName}"<%

} else {
    pageContext.setAttribute("themeClassName", "");
}
  
%>>
    <div id="nonFooter">
    <div id="headerBar" class="${themeClassName}">
    <div id="header">
      <div id="logo"><a href="/" class="${themeClassName}"><img id="logoImage" src="logo.png" /></a></div><%

if (isUserLoggedIn == true) {
	
    %>
      <div id="logout">Welcome back, <a href="${authorLink}" class="${themeClassName}">${userName}</a>.
        (<a href="${logoutLink}" class="${themeClassName}">Logout</a>)
      </div><%
    
} else {
    final String loginLink = view.getLoginLink();
    pageContext.setAttribute("loginLink", loginLink);
    
    %>
      <div id="login"><a href="${loginLink}" class="${themeClassName}">Login or register</a></div><%
    
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
      <h2>My Preferences</h2>
      <form action="updatePreferences" method="post">
        <h3>Details</h3>
      	<p>
          <label for="newPenName" class="prefLabel">Pen name</label>
          <input type="text" name="newPenName" id="newPenName" class="prefInput"
              value="${userName}"></input>
        </p>
        <hr />
        <h3>Display</h3>
        <p>
          <label for="fontSize" class="prefLabel">Font size</label>
          <select name="fontSize" id="fontSize" class="prefInput"><%
          
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
          <label for="fontType" class="prefLabel">Font type</label>
          <select name="fontType" id="fontType" class="prefInput"><%
          
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
          <label for="fontColour" class="prefLabel">Font colour</label>
          <select name="fontColour" id="fontColour" class="prefInput"><%
          
    final String[] fontColourOptions = { "Default", "Charcoal", "Black", "Grey",
            "Blue", "Green", "Red" };
          
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
          <label for="spacing" class="prefLabel">Line spacing</label>
          <select name="spacing" id="spacing" class="prefInput"><%
          
    final String[] spacingOptions = { "Huge", "Large", "Medium", "Small" };
    final double[] spacingValues = { 3.0, 2.0, 1.5, 1.0 };
          
    for (int i = 0; i < spacingOptions.length; i++) {
        pageContext.setAttribute("spacingOption", spacingOptions[i]);
            
        %>
            <option<%
            
        if (spacingValues[i] == userSpacing) {

            %> selected="selected"<%

        }

        %>>${spacingOption}</option><%
            
    }
          
    %>
          </select>
        </p>
        <p>
          <label for="alignment" class="prefLabel">Text alignment</label>
          <select name="alignment" id="alignment" class="prefInput"><%
          
    final String[] alignmentOptions = { "Left", "Right", "Center", "Justify" };
          
    for (int i = 0; i < alignmentOptions.length; i++) {
        pageContext.setAttribute("alignmentOption", alignmentOptions[i]);
            
        %>
            <option<%
            
        if (alignmentOptions[i].equals(userAlignment)) {

            %> selected="selected"<%

        }
            
        %>>${alignmentOption}</option><%

    }
          
    %>
          </select>
        </p>
        <p>
          <label for="theme" class="prefLabel">Theme</label>
          <select name="theme" id="theme" class="prefInput"><%
          
    final String[] themeOptions = { "Light", "Dark", "Sepia", "Lovely" };
    for (int i = 0; i < themeOptions.length; i++) {
        pageContext.setAttribute("themeOption", themeOptions[i]);
        	  
        %>
            <option<%
            
        if (themeOptions[i].equals(userTheme)) {

            %> selected="selected"<%

        }
            
        %>>${themeOption}</option><%

    }
          
    %>
          </select>
        </p>
        <button type="submit">Update</button>
      </form>
    </div>
    </div>
    <div id="footerBar" class="${themeClassName}">
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp" class="${themeClassName}">About</a></span>
      <span class="footer"><a href="/terms.jsp" class="${themeClassName}">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp" class="${themeClassName}">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp" class="${themeClassName}">Contact</a></span>
    </div>
    </div>
  </body>
</html>