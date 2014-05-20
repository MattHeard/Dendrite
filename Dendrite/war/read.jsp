<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ReadView"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText.Format"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

final ReadView view = new ReadView();
final String pageId = request.getParameter("p");
view.setPageId(pageId);

final boolean isPageInStore = view.isPageInStore();
if (isPageInStore == true) {
    final String title = view.getTitle();
    pageContext.setAttribute("webPageTitle", "Dendrite - " + title);
} else {
	pageContext.setAttribute("webPageTitle", "Dendrite");
}

%><%@include file="top_with_format_bar.jspf"

%><%
    
    if (isPageInStore == true) {
    	final boolean isBeginning = view.isBeginning();
    	if (isBeginning == true) {
    		
    		%>
      <div id="storyTitle"><h2>${fn:escapeXml(title)}</h2></div><%
    		
    	}
    	final String pageNumber = view.getPageNumber();
    	pageContext.setAttribute("pageNumber", pageNumber);
    	
    	%>
      <div id="rewriteLink"><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}"
          href="/rewrite.jsp?p=${fn:escapeXml(pageNumber)}">Rewrite</a></div>
      <div class="clear"></div><%
    	
    	//final String text = view.getPageText();
        
        // Display formatted content
        // First, break it into paragraphs.
        final List<String> paragraphs = view.getParagraphs();
        for (final String paragraph : paragraphs) {
        	
        	%><p class="text">
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
    
    	final int numberOfOptions = view.getNumberOfOptions();
    	for (int i = 0; i < numberOfOptions; i++) {
    		final String optionLink = view.getOptionLink(i);
    		pageContext.setAttribute("optionLink", optionLink);
    		final String optionText = view.getOptionText(i);
    		pageContext.setAttribute("optionText", optionText);
    		
    		%>
      <a class="optionLink ${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}"
              href="${fn:escapeXml(optionLink)}"><div class="option"><%
    
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
  
    %></div></a><%

    	}
    	String authorName = view.getAuthorName();
    	if (authorName == null || authorName == "")
    		authorName = "???";
    	pageContext.setAttribute("authorName", authorName);
    	
    	%>
      <div class="clear"></div>
      <p id="credit">This page was written by <%
    
        final boolean isAuthorAnonymous = view.isAuthorAnonymous();
    	if (isAuthorAnonymous == false) {
    		final String authorId = view.getAuthorId();
    		pageContext.setAttribute("authorId", authorId);
    		
    		%><a class="${fontColourClassName} ${themeClassName}" href="/author.jsp?id=${authorId}"><%
    		
    	}
    
        %>${authorName}<%
    
    	if (isAuthorAnonymous == false) {
    		
    	    %></a><%
    		
    	}
    
        %>. </p><%
        
        if (isAuthorAnonymous == false) {
            if (view.isAvatarAvailable() == true) {
            	final int avatarId = view.getAuthorAvatarId();
            	pageContext.setAttribute("avatarId", avatarId);
        
                %>
      <img id="avatar" src="avatars/${avatarId}.png" /><%
        		
            }
        }
        
        %>
      <div class="clear"></div><%
    
        if (isBeginning == false) {
        	final String first = view.getFirstUrl();
        	pageContext.setAttribute("first", first);
    
        	%>
      <p><a class="${fontColourClassName} ${themeClassName}" href="${first}">Return to the first
          page of this story.</a></p><%
    
        }
	
    } else {
	
	    %>
      <div><p>This page doesn't appear to be written yet.</p></div><%
	
    }
	
	%>
<%@include file="bottom.jspf" %>