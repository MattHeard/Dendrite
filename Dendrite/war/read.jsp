<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ReadView"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText"
%><%@ page import="com.deuteriumlabs.dendrite.view.FormattedText.Format"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

final ReadView view = new ReadView();
view.setPageContext(pageContext);
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
    		final String title = view.getTitle();
    		pageContext.setAttribute("title", title);

    		%>
      <div id="storyTitle"><h2>${fn:escapeXml(title)}</h2></div><%

    	}
    	final String pageNumber = view.getPageNumber();
    	pageContext.setAttribute("pageNumber", pageNumber);

    	%>
      <ul id="modifyMenu">
        <li id="rewriteLink"><a title="Rewrite this page"
            href="/rewrite?p=${fn:escapeXml(pageNumber)}">REWRITE</a></li>
        <li><a title="Alternative versions of this page"
            href="/alt?p=${fn:escapeXml(pageNumber)}">ALTERNATIVES</a></li>
      </ul>
      <div class="clear"></div>
      <div id="text_body"><%

    	//final String text = view.getPageText();

        // Display formatted content
        // First, break it into paragraphs.
        final List<String> paragraphs = view.getParagraphs();
        for (final String paragraph : paragraphs) {

        	%><p class="text"><%

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

            %></p><%

        }

      %>
      </div><%

    	final int numberOfOptions = view.getNumberOfOptions();
    	int numSkipped = 0;
    	for (int i = 0; i < numberOfOptions + numSkipped; i++) {
    		final String optionLink = view.getOptionLink(i);
    		pageContext.setAttribute("optionLink", optionLink);
    		final String optionText = view.getOptionText(i);
    		if (optionText != null && optionText.length() > 0) {
    		
    		%>
      <a class="optionLink"
          href="${fn:escapeXml(optionLink)}"><div class="option"><%
    
                List<FormattedText> formattedTextChunks
                         = FormattedText.extractFormattedText(optionText);
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
    
            } else {
            	numSkipped++;
            }
    	}
    	String authorName = view.getAuthorName();
    	if (authorName == null || authorName == "") {
    		authorName = "???";
    	}
    	pageContext.setAttribute("authorName", authorName);
    	
    	%>
      <div class="clear"></div>
      <p id="credit">This page was written by <%
    
        final boolean isAuthorAnonymous = view.isAuthorAnonymous();
    	if (isAuthorAnonymous == false) {
    		final String authorId = view.getAuthorId();
    		pageContext.setAttribute("authorId", authorId);
    		
    		%><a href="/author?id=${authorId}"><%
    		
    	}
    
        %>${authorName}<%
    
    	if (isAuthorAnonymous == false) {
    		
    	    %></a><%
    		
    	}
    
        %>. </p><%
        
        final int avatarId;
        if (isAuthorAnonymous == false && view.isAvatarAvailable() == true) {
           	avatarId = view.getAuthorAvatarId();
        } else {
        	avatarId = 0;
        }
        pageContext.setAttribute("avatarId", avatarId);
        
        %>
      <img id="avatar" src="avatars/${avatarId}.png" />
      <div class="clear"></div>
      <script>
        var ADD_TAG_URL_WITHOUT_TAG = "/addTag?p=<%= view.getPageId() %>&tag=";
        var REMOVE_TAG_URL_WITHOUT_TAG = "/removeTag?p=<%
            %><%= view.getPageId() %>&tag=";
        </script>
      <ul class="read tags"><%
      
        final List<String> tags = view.getTags();
        for (String tag : tags) {
        	view.prepareTag(tag);
        	
        	%>
        <li class="${tagClass}">${tagName}</li><%
        	
        }
      
        %>
      </ul>
      <ul class="tagControls">
        <li class="add">＋</li>
        <li class="cancel">Cancel</li>
        <li class="ok">OK</li>
        <li class="select">
          <select><%
          
final String[] tagNames = { "SCIFI", "WESTERN", "ROMANCE", "FANFIC", "ACTION",
    "ADVENTURE", "COMEDY", "CRIME", "DRAMA", "EROTIC", "FANTASY", "HISTORICAL",
    "HORROR", "INSPIRATIONAL", "MYSTERY", "POLITICAL", "RELIGIOUS",
    "THRILLER", "SPAM" };
for (final String tag : tagNames) {
	
	%>
            <option><%= tag %></option><%
	
}
          
          %>
          </select>
        </li>
      </ul>
      <div class="clear"></div><%
      
      if (isUserLoggedIn) {
      
      %>
      <div id="love"<%
        
        if (view.isLoved() == true) {
            
            %> class="set"<%
            
        }
      
      %>><img id="heartIcon" src="icons/heart.png" /></div>
      <script type="text/javascript" lang="javascript">
        var IS_NOT_CURRENTLY_LOVED = <%= view.isNotLoved() %>;
        var LOVE_URI_WITHOUT_VAL = "/love?<%
            %>p=<%= view.getPageId() %><%
            %>&user=<%= View.getMyUserId() %><%
            %>&isAdded=";
        var love_uri = LOVE_URI_WITHOUT_VAL + IS_NOT_CURRENTLY_LOVED;
      </script>
      <div id="loveCount"><%= view.getNumLovingUsersString() %></div><%
      
      }
      
      %>
      <div class="clear"></div><%
    
        if (isBeginning == false) {
        	final String first = view.getFirstUrl();
        	pageContext.setAttribute("first", first);

        	%>
      <p><a href="/read?p=<%= view.getParentId() %>">Return to the previous
          page.</a></p>
      <p><a href="${first}">Return to the first page of this story.</a></p><%
    
        }
	
    } else {
	
	    %>
      <div><p>This page doesn't appear to be written yet.</p></div><%
	
    }
	
	if (view.isShowingFirstPage() == false) {
		view.preparePrevPageNum();
		
		%>
      <span
          class="pageArrow"
          id="prevPage"><a href="/read?p=${prevPageNum}">←</a></span><%
		
	}
	
	view.prepareNextPageNum();
	%>
      <span
          class="pageArrow"
          id="nextPage"><a href="/read?p=${nextPageNum}">→</a></span>
<%@include file="bottom.jspf" %>