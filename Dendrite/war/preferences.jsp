<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.PreferencesView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - My Preferences");
final PreferencesView view = new PreferencesView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%><%

if (PreferencesView.isUserLoggedIn()) {

%>
        <h2>My Preferences</h2>
        <form action="updatePreferences" method="post">
          <h3>Details</h3>
      	  <p>
            <label for="newPenName" class="prefLabel">Pen name</label>
            <input type="text" name="newPenName" id="newPenName" class="prefInput"
                value="${fn:escapeXml(userName)}"></input>
            <%

final boolean isNewPenNameBlank = view.isNewPenNameBlank();
if (isNewPenNameBlank == true) {

    %><i>The pen name must not be blank.</i><%

}

%>
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
                  
        if (fontSizeValues[i] == PreferencesView.getUserFontSize()) {
        	
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
          <hr class="minor_separator" />
          <ul class="radio_list">
            <h4>Font type</h4><%
    
    final String[] fontTypeStyles = { "serifTypeface", "sansSerifTypeface",
            		"monospaceTypeface", "cursiveTypeface", "fantasyTypeface" };
    final String[] fontTypeOptions = { "Serif", "Sans-serif", "Monospace",
            "Cursive", "Fantasy" };
    
    for (int i = 0; i < fontTypeOptions.length; i++) {
        pageContext.setAttribute("fontTypeStyle", fontTypeStyles[i]);
        pageContext.setAttribute("fontTypeOption", fontTypeOptions[i]);
        
    	%>
            <li>
              <input
                  type="radio"
                  name="fontType"
                  value="${fn:escapeXml(fontTypeOption)}"
                  id="fontType${fn:escapeXml(fontTypeOption)}"<%
                  
        if (fontTypeOptions[i].equals(PreferencesView.getUserFontType())) {
        	
            %>
                  checked="checked"<%
                	  
        }
                  
        %> />
              <label
                  for="fontType${fn:escapeXml(fontTypeOption)}"
                  class="${fn:escapeXml(fontTypeStyle)}"
                  >${fn:escapeXml(fontTypeOption)}</label>
            </li><%
    	
    }
          
    %>
          </ul>
          <hr class="minor_separator" />
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
                 id="fontColour${fn:escapeXml(fontColourOption)}"<%
                 
        if (fontColourOptions[i].equals(PreferencesView.getUserFontColour())) {
        
            %>
                 checked="checked"<%
            
        }
                 
        %> />
             <label
                 for="fontColour${fn:escapeXml(fontColourOption)}"
                 >${fn:escapeXml(fontColourOption)}</label>
           </li><%
    }
    
    %>
          </ul>
          <hr class="minor_separator" />
          <ul class="radio_list">
            <h4>Line spacing</h4><%
            
    final String[] spacingOptions = { "Huge", "Large", "Medium", "Small" };
    final double[] spacingValues = { 3.0, 2.0, 1.5, 1.0 };
    
    for (int i = 0; i < spacingOptions.length; i++) {
    	pageContext.setAttribute("spacingOption", spacingOptions[i]);
    	
    	%>
            <li>
              <input
                  type="radio"
                  name="spacing"
                  value="${fn:escapeXml(spacingOption)}"
                  id="spacing${fn:escapeXml(spacingOption)}"<%
                  
        if (spacingValues[i] == PreferencesView.getUserSpacing()) {
        	
        	%>
                  checked="checked"<%
        			 
        }
                  
        %> />
              <label
                  for="spacing${fn:escapeXml(spacingOption)}"
                  >${fn:escapeXml(spacingOption)}</label>
            </li><%
    }
            
    %>
          </ul>
          <hr class="minor_separator" />
          <ul class="radio_list">
            <h4>Text alignment</h4><%
    
    final String[] alignmentOptions = { "Left", "Right", "Center", "Justify" };
            
    for (int i = 0; i < alignmentOptions.length; i++) {
    	pageContext.setAttribute("alignmentOption", alignmentOptions[i]);
    	
    	%>
    	    <li>
    	      <input
    	          type="radio"
    	          name="alignment"
    	          value="${fn:escapeXml(alignmentOption)}"
    	          id="alignment${fn:escapeXml(alignmentOption)}"<%
    	
        if (alignmentOptions[i].equals(PreferencesView.getUserAlignment())) {
        	
        	%>
        	      checked="checked"<%
       
        }
    	          
    	%> />
    	      <label
    	          for="alignment${fn:escapeXml(alignmentOption)}"
    	          >${fn:escapeXml(alignmentOption)}</label>
    	    </li><%
    	
    }
            
    %>
          </ul>
          <hr class="minor_separator" />
          <ul class="radio_list">
            <h4>Theme</h4><%
            
    final String[] themeOptions = { "Light", "Dark", "Sepia", "Lovely" };
            
    for (int i = 0; i < themeOptions.length; i++) {
    	pageContext.setAttribute("themeOption", themeOptions[i]);
    	
    	%>
    	    <li>
    	      <input
    	          type="radio"
    	          name="theme"
    	          value="${fn:escapeXml(themeOption)}"
    	          id="theme${fn:escapeXml(themeOption)}"<%
    	          
    	if (themeOptions[i].equals(PreferencesView.getUserTheme())) {
    	
    		%>
                  checked="checked"<%
                  
    	}
    	
    	%> />
    	      <label
    	          for="theme${fn:escapeXml(themeOption)}"
    	          >${fn:escapeXml(themeOption)}</label>
    	    </li><%
    }
            
    %>
          </ul>
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
                class="avatar_input_hidden"<%
                
        final int userAvatarId = view.getAvatarId();
        final boolean isSelected = (userAvatarId == i);
        if (isSelected == true) {
        	
        	%>
                checked="checked"<%
                
        }
                
        %> />
            <label for="avatar_radio_${avatarNum}">
              <img src="avatars/${avatarNum}.png"
                  class="avatar_radio_button" />
            </label><%
    	
    }
    
    %>
          </div>
          <button type="submit">Update</button>
          <a href="/resetPreferences"><div class="button">Reset to
              defaults</div></a>
        </form><%
        
} else {
	
	%>
        <p class="notice">For now, you need to be logged in to change your
            preferences.</p><%
	
}
        
        %>
<%@include file="bottom.jspf" %>