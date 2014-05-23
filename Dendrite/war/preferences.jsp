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
          <ul class="radio_list">
            <h4>Font size</h4><%
          
    final String[] fontSizeOptions = { "Huge", "Large", "Medium", "Small" };
    final double[] fontSizeValues = { 2.0, 1.5, 1.0, 0.8 };

    for (int i = 0; i < fontSizeValues.length; i++) {
        pageContext.setAttribute("fontSizeOption", fontSizeOptions[i]);
          
        %>
            <li>
              <input
                  type="radio"
                  name="fontSize"
                  value="${fn:escapeXml(fontSizeOption)}"
                  id="fontSize${fn:escapeXml(fontSizeOption)}"<%
                  
        if (fontSizeValues[i] == userFontSize) {
        	
            %>
        	      checked="checked"<%
        	
        }
                  
        %> />
              <label
                  for="fontSize${fn:escapeXml(fontSizeOption)}"
                  >${fn:escapeXml(fontSizeOption)}</label>
            </li><%
            
    }
            
    %>
          </ul>
          <ul class="radio_list">
            <h4>Font type</h4><%
    
    final String[] fontTypeOptions = { "Serif", "Sans-serif", "Monospace",
            "Cursive", "Fantasy" };
    
    for (int i = 0; i < fontTypeOptions.length; i++) {
        pageContext.setAttribute("fontTypeOption", fontTypeOptions[i]);
        
    	%>
            <li>
              <input
                  type="radio"
                  name="fontType"
                  value="${fn:escapeXml(fontTypeOption)}"
                  id="fontType${fn:escapeXml(fontTypeOption)}"<%
                  
        if (fontTypeOptions[i].equals(userFontType)) {
        	
            %>
                  checked="checked"<%
                	  
        }
                  
        %> />
              <label
                  for="fontType${fn:escapeXml(fontTypeOption)}"
                  >${fn:escapeXml(fontTypeOption)}</label>
            </li><%
    	
    }
          
    %>
          </ul>
          <ul class="radio_list">
            <h4>Font colour</h4><%
            
    final String[] fontColourOptions = { "Default", "Charcoal", "Black", "Grey",
            "Blue", "Green", "Red" };
                  
    for (int i = 0; i < fontColourOptions.length; i++) {
        pageContext.setAttribute("fontColourOption", fontColourOptions[i]);
        
        %>
           <li>
             <input
                 type="radio"
                 name="fontColour"
                 value="${fn:escapeXml(fontColourOption)}"
                 id="fontType${fn:escapeXml(fontColourOption)}"<%
                 
        if (fontColourOptions[i].equals(userFontColour)) {
        
            %>
                 checked="checked"<%
            
        }
                 
        %> />
             <label
                 for="fontType${fn:escapeXml(fontColourOption)}"
                 >${fn:escapeXml(fontColourOption)}</label>
           </li><%
    }
    
    %>
          </ul>
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
          <hr />
          <h3>Avatar</h3>
          <div id="avatar_selection"><%
          
    // See http://stackoverflow.com/questions/3896156/how-do-i-style-radio-buttons-with-images-laughing-smiley-for-good-sad-smiley
    
    final int NUM_AVATAR_OPTIONS = 19;
    for (int i = 0; i < NUM_AVATAR_OPTIONS; i++) {
    	pageContext.setAttribute("avatarNum", i);
    	
    	%>
            <input type="radio"
                name="avatar"
                id="avatar_radio_${avatarNum}"
                value="${avatarNum}"
                class="avatar_input_hidden" />
            <label for="avatar_radio_${avatarNum}">
              <img src="avatars/${avatarNum}.png"
                  class="avatar_radio_button" />
            </label><%
    	
    }
    
    %>
          </div>
          <button type="submit">Update</button>
        </form>
<%@include file="bottom.jspf" %>