<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.PreferencesView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - My Preferences");
final View view = new PreferencesView();

%><%@include file="top.jspf"

%>
        <h2>My Preferences</h2>
        <form action="updatePreferences" method="post">
          <h3>Details</h3>
      	  <p>
            <label for="newPenName" class="prefLabel">Pen name</label>
            <input type="text" name="newPenName" id="newPenName" class="prefInput"
                value="${fn:escapeXml(userName)}"></input>
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

        %>>${fn:escapeXml(fontSizeOption)}</option><%
        	  
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
            
        %>>${fn:escapeXml(fontTypeOption)}</option><%

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
            
        %>>${fn:escapeXml(fontColourOption)}</option><%

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

        %>>${fn:escapeXml(spacingOption)}</option><%
            
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
            
        %>>${fn:escapeXml(alignmentOption)}</option><%

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
            
        %>>${fn:escapeXml(themeOption)}</option><%

    }
          
    %>
            </select>
          </p>
          <button type="submit">Update</button>
        </form>
<%@include file="bottom.jspf" %>