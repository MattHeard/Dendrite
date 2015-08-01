<% /* © 2013-2015 Deuterium Labs Limited */ %>
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
view.initialise();

final boolean isPageInStore = view.isPageInStore();
if (isPageInStore == true) {
    final String title = view.getTitle();
    pageContext.setAttribute("webPageTitle", "Dendrite - " + title);
} else {
	pageContext.setAttribute("webPageTitle", "Dendrite");
}

%><%@include file="top_simplified_format.jspf"

%><%

	final String pageNumber = view.getPageNumber();
	pageContext.setAttribute("pageNumber", pageNumber);
    if (isPageInStore == true) {
    	final boolean isBeginning = view.isBeginning();
    	if (isBeginning == true) {
    		final String title = view.getTitle();
    		pageContext.setAttribute("title", title);

    		%>
      <div id="storyTitle"><h1>${fn:escapeXml(title)}</h1></div><%

    	}

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
    	int numDisplayed = 0;
    	boolean isOptionPairOpen = false;
    	for (int i = 0; i < numberOfOptions + numSkipped; i++) {
    		final String optionLink = view.getOptionLink(i);
    		pageContext.setAttribute("optionLink", optionLink);
    		final String optionText = view.getOptionText(i);
    		if (optionText != null && optionText.length() > 0) {
    		    if (numDisplayed % 2 == 0) {
    		    	
    		    	%>
      <ul class="optionPair"><%
      
                    isOptionPairOpen = true;
    		    }
    		%>
        <li><a class="optionLink"
          href="${fn:escapeXml(optionLink)}"><div class="option<%
          
         		boolean isWritten = view.isOptionWritten(i);
       			if (isWritten == false) {
       			
       				%> unwritten<%
       			
       			}
       		
       		%>"><%
    
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
  
    %></div></a></li><%
    
                numDisplayed++;
    
                if (numDisplayed % 2 == 0) {
                    
                	%>
      </ul>
      <div class="clear"></div><%
      
                    isOptionPairOpen = false;
                	
                }
            } else {
            	numSkipped++;
            }
    	}
    	
    	if (isOptionPairOpen == true) {
            
            %>
      </ul>
      <div class="clear"></div><%
    		
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
    		
    		%><a href="/author?id=${fn:escapeXml(authorId)}"><%
    		
    	}
    
        %>${fn:escapeXml(authorName)}<%
    
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
        
        final String avatarDesc = ReadView.getAvatarDesc(avatarId);
        pageContext.setAttribute("avatarDesc", avatarDesc);
        
        %>
      <img id="avatar"
          alt="${fn:escapeXml(avatarDesc)}"
          src="/static/img/avatar/2014-09-06-0/small/${fn:escapeXml(avatarId)}.png" />
      <div class="clear"></div>
      <script><%

        view.preparePgId();
      
        %>
        var ADD_TAG_URL_WITHOUT_TAG = "/addTag?p=${fn:escapeXml(pgId)}&tag=";
        var REMOVE_TAG_URL_WITHOUT_TAG = "/removeTag?p=${fn:escapeXml(pgId)}&tag=";
        </script>
      <ul class="read tags"><%
      
        final List<String> tags = view.getTags();
        for (String tag : tags) {
        	view.prepareTag(tag);
        	
        	%>
        <li class="${fn:escapeXml(tagClass)}">${fn:escapeXml(tagName)}</li><%
        	
        }
      
        %>
      </ul>
      <ul class="tagControls">
        <li class="add">＋</li>
        <li class="cancel">Cancel</li>
        <li class="ok">OK</li>
        <li class="select">
          <select><%
          
final String[] tagNames = { 
		"ACTION",
		"ADVENTURE",
		"COMEDY",
		"CRIME",
		"DRAMA",
		"EROTIC",
		"FANFIC",
		"FANTASY",
		"HISTORICAL",
		"HORROR",
		"INSPIRATIONAL",
		"MYSTERY",
		"POLITICAL",
		"RELIGIOUS",
		"ROMANCE",
		"SCIFI",
		"SPAM",
		"THRILLER",
		"WESTERN",
		"YOUNG-ADULT" };
for (final String tag : tagNames) {
	view.prepareTagName(tag);
	
	%>
            <option>${fn:escapeXml(tagName)}</option><%
	
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
      
      %>><img id="heartIcon"
        alt="A small heart."
        src="/static/img/icons/2014-09-01-0/heart.png" /></div>
      <script type="text/javascript" lang="javascript"><%

      view.prepareIsNotLoved();
      
      %>
        var IS_NOT_CURRENTLY_LOVED = ${fn:escapeXml(isNotLoved)};
        var LOVE_URI_WITHOUT_VAL = "/love?p=${fn:escapeXml(pgId)}&isAdded=";
        var love_uri = LOVE_URI_WITHOUT_VAL + IS_NOT_CURRENTLY_LOVED;
      </script><%
      
      view.prepareNumLovers();
      
      %>
      <div id="loveCount">${fn:escapeXml(numLovers)}</div><%
      
      }
      
      %>
      <div class="clear"></div><%
    
        if (isBeginning == false) {
        	final String first = view.getFirstUrl();
        	pageContext.setAttribute("first", first);
        	
        	view.prepareParentId();

        	%>
      <p><a href="/read?p=${fn:escapeXml(parentId)}">Return to the previous
          page.</a></p>
      <p><a href="${fn:escapeXml(first)}">Return to the first page of this story.</a></p><%
    
        }
      
      %>
      <p><a href="/contents">Return to the table of contents.</a></p><%
	
    } else {
    	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	
	    %>
      <p class="notice">This page doesn't appear to be written yet.</p>
      <p class="notice">Why don't you <a href="/new">start a new story?</a></p><%
	
    }

	%>
      <p id="pgNav"><%
	
	if (view.isShowingFirstPage() == false) {
		view.preparePrevPageNum();
		
		%>
        <span class="pgArrow"><a href="/read?p=${fn:escapeXml(prevPageNum)}">◀</a></span><%
		
	}
	
	%>
        <span>${fn:escapeXml(pageNumber)}</span><%
	
	view.prepareNextPageNum();
	%>
        <span class="pgArrow"><a href="/read?p=${fn:escapeXml(nextPageNum)}">▶</a></span>
      </p>
<%@include file="bottom.jspf" %>