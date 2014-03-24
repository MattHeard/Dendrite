<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ReadView"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText.Format"
%><%@ page import="java.util.List"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="script.js"></script><%
    
    final ReadView view = new ReadView();
    final String pageId = request.getParameter("p");
    view.setPageId(pageId);
    
    final boolean isPageInStore = view.isPageInStore();
    if (isPageInStore == true) {
    	final String title = view.getTitle();
    	pageContext.setAttribute("title", title);
    	
    	%>
    <title>Dendrite - ${title}</title><%
    	
    } else {
    	
    	%>
    <title>Dendrite</title><%
    
    }
    
    %>
  </head>
  <body>
    <div id="nonFooter">
    <div id="headerBar">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final boolean isUserLoggedIn = ReadView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = ReadView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = ReadView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        
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
    <div id="formatBar">
    	<div id="formatMenu">
    	    <img class="formatIcon" id="sizeButton" src="icons/size.png"
    	    		title="SIZE" onClick="clickFormatButton('size');" />
    	    <img class="formatIcon" id="typeButton" src="icons/type.png"
    	    		title="TYPE" onClick="clickFormatButton('type');" />
    	    <img class="formatIcon" id="colourButton" src="icons/colour.png"
    	    		title="COLOUR" onClick="clickFormatButton('colour');" />
    	    <img class="formatIcon" id="alignmentButton"
    	    		src="icons/alignment.png" title="ALIGNMENT"
    	    		onClick="clickFormatButton('alignment');" />
    	    <img class="formatIcon" id="spacingButton" src="icons/spacing.png"
    	    		title="SPACING" onClick="clickFormatButton('spacing');" />
    	    <img class="formatIcon" id="themeButton" src="icons/theme.png"
    	    		title="THEME" onClick="clickFormatButton('theme');" />
    	</div>
    	<div id="sizePickerBar" class="formatPickerBar">
    		<select id="sizePicker" onchange="pickSize(this.value);">
    			<option value="2">Huge</option>
    			<option value="1.5">Large</option>
    			<option value="1" selected="selected">Medium</option>
    			<option value="0.8">Small</option>
    		</select>
    	</div>
     	<div id="typePickerBar" class="formatPickerBar">
          <select id="typePicker" onchange="pickType(this.value);">
            <option value="serif">Serif</option>
            <option value="sans-serif" selected="selected">Sans-serif</option>
            <option value="monospace">Monospace</option>
            <option value="cursive">Cursive</option>
            <option value="fantasy">Fantasy</option>
          </select>
    	</div>
    	<div id="colourPickerBar" class="formatPickerBar">
    		<select id="colourPicker" onchange="pickColour(this.value);">
	    		<option value="333">Off-Black</option>
	    		<option value="000" selected="selected">Black</option>
	    		<option value="999">Grey</option>
    			<option value="009">Blue</option>
    			<option value="090">Green</option>
    			<option value="900">Red</option>
    		</select>
    	</div>
    	<div id="alignmentPickerBar" class="formatPickerBar">
    		<select id="alignmentPicker" onchange="pickAlignment(this.value);">
	    		<option value="left">Left</option>
	    		<option value="right">Right</option>
	    		<option value="center">Center</option>
	    		<option value="justify" selected="selected">Justify</option>
    		</select>
    	</div>
    	<div id="spacingPickerBar" class="formatPickerBar">
    		<select id="spacingPicker" onchange="pickSpacing(this.value);">
    			<option value="3">Huge</option>
    			<option value="2">Large</option>
    			<option value="1.5" selected="selected">Medium</option>
    			<option value="1">Small</option>
    		</select>
    	</div>
    	<div id="themePickerBar" class="formatPickerBar">
    		<select id="themePicker" onchange="pickTheme(this.value);">
	    		<option value="light">Light</option>
	    		<option value="dark">Dark</option>
	    		<option value="sepia">Sepia</option>
	    		<option value="lovely">Lovely</option>
    		</select>
    	</div>
    </div>
    <script type="text/javascript">showFormatBar();</script> 
    <div id="main"><%
    
    if (isPageInStore == true) {
    	final boolean isBeginning = view.isBeginning();
    	if (isBeginning == true) {
    		
    		%>
      <div id="storyTitle" class="modifiableText"><h2>${title}</h2></div><%
    		
    	}
    	final String pageNumber = view.getPageNumber();
    	pageContext.setAttribute("pageNumber", pageNumber);
    	
    	%>
      <div id="editLink" class="modifiableText"><a
          href="/edit.jsp?p=${pageNumber}">Edit</a></div><%
    	
    	//final String text = view.getPageText();
        
        // Display formatted content
        // First, break it into paragraphs.
        final List<String> paragraphs = view.getParagraphs();
        for (final String paragraph : paragraphs) {
        	
        	%><p class="text modifiableText">
          <%
          
          	List<FormattedText> formattedTextChunks;
          	formattedTextChunks = FormattedText.extractFormattedText(paragraph);
          	for (final FormattedText chunk : formattedTextChunks) {
          		pageContext.setAttribute("chunk", chunk.getText());
          		Format format = chunk.getFormat();
          		switch (format) {
          		case BOLD:
          			%><b>${fn:escapeXml(chunk)}</b><%
          			break;
          		case BOLD_ITALIC:
          			%><b><i>${fn:escapeXml(chunk)}</i></b><%
          			break;
          		case ITALIC:
          			%><i>${fn:escapeXml(chunk)}</i><%
          			break;
          		case NONE:
          			%>${fn:escapeXml(chunk)}<%
          			break;
          		}
          	}
          
            %>
        </p><%
        	
        }
        
    	//pageContext.setAttribute("text", text);
    
	    %>
      <!-- <p id="text" class="modifiableText">${text}</p> --><%
    
    	final int numberOfOptions = view.getNumberOfOptions();
    	for (int i = 0; i < numberOfOptions; i++) {
    		final String optionLink = view.getOptionLink(i);
    		pageContext.setAttribute("optionLink", optionLink);
    		final String optionText = view.getOptionText(i);
    		pageContext.setAttribute("optionText", optionText);
    		
    		%>
      <div class="option modifiableText"><a href="${optionLink}"<%
    %>><%
    
  	List<FormattedText> formattedTextChunks;
  	formattedTextChunks = FormattedText.extractFormattedText(optionText);
  	for (final FormattedText chunk : formattedTextChunks) {
  		pageContext.setAttribute("chunk", chunk.getText());
  		Format format = chunk.getFormat();
  		switch (format) {
  		case BOLD:
  			%><b>${fn:escapeXml(chunk)}</b><%
  			break;
  		case BOLD_ITALIC:
  			%><b><i>${fn:escapeXml(chunk)}</i></b><%
  			break;
  		case ITALIC:
  			%><i>${fn:escapeXml(chunk)}</i><%
  			break;
  		case NONE:
  			%>${fn:escapeXml(chunk)}<%
  			break;
  		}
  	}
  
    %></a></div><%

    	}
    	String authorName = view.getAuthorName();
    	if (authorName == null || authorName == "")
    		authorName = "???";
    	pageContext.setAttribute("authorName", authorName);
    	
    	%>
      <div id="credit" class="modifiableText">This page was written by <%
    
        final boolean isAuthorAnonymous = view.isAuthorAnonymous();
    	if (isAuthorAnonymous == false) {
    		final String authorId = view.getAuthorId();
    		pageContext.setAttribute("authorId", authorId);
    		
    		%><a href="/author.jsp?id=${authorId}"><%
    		
    	}
    
        %>${authorName}<%
    
    	if (isAuthorAnonymous == false) {
    		
    	    %></a><%
    		
    	}
    
        %>.</div><%
    
        if (isBeginning == false) {
        	final String first = view.getFirstUrl();
        	pageContext.setAttribute("first", first);
    
        	%>
      <div class="modifiableText"><a href="${first}">Return to the first page of
          this story.</a></div><%
    
        }
	
    } else {
	
	    %>
      <div class="modifiableText">This page doesn't appear to be written
          yet.</div><%
	
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