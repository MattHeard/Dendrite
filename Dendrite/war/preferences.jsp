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
        <h1>My Preferences</h1>
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
              <label
                  for="fontSize${fn:escapeXml(fontSizeOption)}"
                  >
                <input
                    type="radio"
                    name="fontSize"
                    value="${fn:escapeXml(fontSizeOption)}"
                    id="fontSize${fn:escapeXml(fontSizeOption)}"<%
                  
        if (fontSizeValues[i] == PreferencesView.getUserFontSize()) {
            
            %>
                    checked="checked"<%
            
        }
                  
        %> /><span>${fn:escapeXml(fontSizeOption)}</span></label>
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
              <label
                  for="fontType${fn:escapeXml(fontTypeOption)}"
                  class="${fn:escapeXml(fontTypeStyle)}" >
                <input
                    type="radio"
                    name="fontType"
                    value="${fn:escapeXml(fontTypeOption)}"
                    id="fontType${fn:escapeXml(fontTypeOption)}"<%
                  
        if (fontTypeOptions[i].equals(PreferencesView.getUserFontType())) {
            
            %>
                    checked="checked"<%
                      
        }
                  
        %> /><span>${fn:escapeXml(fontTypeOption)}</span></label>
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
             <label
                 for="fontColour${fn:escapeXml(fontColourOption)}" >
               <input
                   type="radio"
                   name="fontColour"
                   value="${fn:escapeXml(fontColourOption)}"
                   id="fontColour${fn:escapeXml(fontColourOption)}"<%
                 
        if (fontColourOptions[i].equals(PreferencesView.getUserFontColour())) {
        
            %>
                   checked="checked"<%
            
        }
                 
        %> /><span>${fn:escapeXml(fontColourOption)}</span></label>
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
              <label for="spacing${fn:escapeXml(spacingOption)}" >
                <input
                    type="radio"
                    name="spacing"
                    value="${fn:escapeXml(spacingOption)}"
                    id="spacing${fn:escapeXml(spacingOption)}"<%
                  
        if (spacingValues[i] == PreferencesView.getUserSpacing()) {
            
            %>
                    checked="checked"<%
                     
        }
                  
        %> /><span>${fn:escapeXml(spacingOption)}</span></label>
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
    	      <label for="alignment${fn:escapeXml(alignmentOption)}" >
                <input type="radio" name="alignment"
                    value="${fn:escapeXml(alignmentOption)}"
                    id="alignment${fn:escapeXml(alignmentOption)}"<%
        
        if (alignmentOptions[i].equals(PreferencesView.getUserAlignment())) {
            
            %>
                    checked="checked"<%
       
        }
                  
        %> /><span>${fn:escapeXml(alignmentOption)}</span></label>
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
    	      <label for="theme${fn:escapeXml(themeOption)}">
                <input type="radio" name="theme"
                    value="${fn:escapeXml(themeOption)}"
                    id="theme${fn:escapeXml(themeOption)}"<%
                  
        if (themeOptions[i].equals(PreferencesView.getUserTheme())) {
        
            %>
                    checked="checked"<%
                  
        }
        
        %> /><span>${fn:escapeXml(themeOption)}</span></label>
    	    </li><%
    }
            
    %>
          </ul>
          <p id="darkWarning" 
              class="warning"><b>WARNING</b>: Black font will be almost
            impossible to read on the dark theme.</p>
          <hr />
          <h3>Avatar</h3>
          <div id="avatar_selection"><%
          
    // See http://stackoverflow.com/questions/3896156/how-do-i-style-radio-buttons-with-images-laughing-smiley-for-good-sad-smiley
    
    final int NUM_AVATAR_OPTIONS = 30;
    for (int i = 0; i < NUM_AVATAR_OPTIONS; i++) {
    	pageContext.setAttribute("avatarNum", i);
    	
    	%>
            <input type="radio"
                name="avatar"
                id="avatar_radio_${fn:escapeXml(avatarNum)}"
                value="${fn:escapeXml(avatarNum)}"
                class="avatar_input_hidden"<%
                
        final int userAvatarId = view.getAvatarId();
        final boolean isSelected = (userAvatarId == i);
        if (isSelected == true) {
        	
        	%>
                checked="checked"<%
                
        }
        
        final String avatarDesc = PreferencesView.getAvatarDesc(i);
        pageContext.setAttribute("avatarDesc", avatarDesc);
                
        %> />
            <label for="avatar_radio_${fn:escapeXml(avatarNum)}">
              <img src="/static/img/avatar/2014-09-06-0/small/${fn:escapeXml(avatarNum)}.png"
                  alt="${fn:escapeXml(avatarDesc)}"
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