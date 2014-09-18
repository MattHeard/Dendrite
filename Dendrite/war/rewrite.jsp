<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.RewriteView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Rewrite");
final RewriteView view = new RewriteView();
view.setPageContext(pageContext);
view.setSession(session);

final String pageNumber = request.getParameter("p");
view.setPageNumber(pageNumber);
final String error = request.getParameter("error");
view.setError(error);



%><%@include file="top_with_side_bar.jspf"

%><%
  
final boolean isExistingPage = view.isExistingPage();
if (isExistingPage == true) {
    pageContext.setAttribute("pageNumber", pageNumber);
      
    %>
      <div class="intro">The <i>rewrite</i> option allows you to completely
          rewrite the text of this page. You will be credited as the only
          author.</div><%
    
    final boolean isBeginning = view.isBeginning();
    if (isBeginning == true) {
    	
    	final String storyTitle = view.getStoryTitle();
    	pageContext.setAttribute("title", storyTitle);
    	
    	%>
      <h2>${fn:escapeXml(title)}</h2><%
    	
    } else {
    
        final String incomingOptionText = view.getIncomingOptionText();
        pageContext.setAttribute("option", incomingOptionText);
      
        %>
      <h2>${fn:escapeXml(option)}</h2><%
    
    }
    
    %>
      <form action="submitRewrite" method="post">
        <input type="hidden" name="pageNumber"
            value="${fn:escapeXml(pageNumber)}" />
        <div>
          <label for="content">Story</label>
          <span
              id="contentCountNote"
              class="countNote">(<span id="contentCount">5000</span>
              characters remaining)
          </span>
        </div>
        <div class="clear"></div>
        <div>
          <textarea id="content" name="content"><%

final boolean isDraftPending = view.isDraftPending();
if (isDraftPending == true) {
    view.prepareDraftContent();
    
    %>${fn:escapeXml(draftContent)}<%
}
            
%></textarea>
          <aside class="formattingTips" id="contentFormattingTips">
            <h3>Formatting</h3>
            <ul>
              <li>*i* → <i>i</i>
              <li>**B** → <b>B</b>
              <li>***B & i*** → <b><i>B & i</i></b>
            </ul>
          </aside>
        </div>
        <div class="clear"></div><%

    final boolean isContentBlank = view.isContentBlank();
    final boolean isContentTooLong = view.isContentTooLong();
    if (isContentBlank == true) {
			
        %>
        <div><i>Must not be blank</i></div>
        <div class="clear"></div><%
		
    } else if (isContentTooLong == true) {
	
        %>
        <div><i>Must not be longer than 5000 characters</i></div>
        <div class="clear"></div><%
		
    }

    %>
        <div>
          <label for="option0">Options</label><%

    final boolean isAnOptionTooLong = view.isAnOptionTooLong();
    if (isAnOptionTooLong == true) {
      		
    %>
          <i>Must not be longer than 80 characters</i><%
  		
		}
  	
    %>
          <span
              id="optionCountNote"
              class="countNote">(<span id="optionCount">80</span> characters
              remaining)
          </span>
        </div>
        <div class="clear"></div>
        <div>
          <div id="optionSet"><%
      
    for (int i = 0; i < 5; i++) {
        pageContext.setAttribute("optionNumber", i);
        
        if (view.isDraftPending() == true) {
            view.prepareDraftOption(i);
        }
        	
        %>
            <input id="option${fn:escapeXml(optionNumber)}"
                name="option${fn:escapeXml(optionNumber)}"
                type="text" value="${fn:escapeXml(draftOption)}"></input><%
        	
    }
        
    %>
          </div>
          <aside class="formattingTips" id="optionsFormattingTips">
            <h3>Formatting</h3>
            <ul>
              <li>*i* → <i>i</i>
              <li>**B** → <b>B</b>
              <li>***B & i*** → <b><i>B & i</i></b>
            </ul>
          </aside>
        </div>
        <div class="clear"></div><%
        
    if (isUserLoggedIn == true) {
        final String authorId = RewriteView.getMyUserId();
        pageContext.setAttribute("authorId", authorId);
        
        %>
        <input name="authorId" type="hidden" value="${fn:escapeXml(authorId)}"
            /><%
        
    }
      
    %>
        <div>
          <label for="authorName">Author</label>
          <span
              id="authorNameCountNote"
              class="countNote">(<span id="authorNameCount">100</span>
              characters remaining)
          </span>
        </div>
        <div class="clear"></div>
        <div>
          <input id="authorName" name="authorName" type="text"<%
        
    if (isUserLoggedIn == true) {
        
        %> value="${fn:escapeXml(userName)}"<%
        
    } else {
			
        %> value="???"<%
			
    }

    %>></input><%

    final boolean isAuthorNameBlank = view.isAuthorNameBlank();
    final boolean isAuthorNameTooLong = view.isAuthorNameTooLong();
    if (isAuthorNameBlank == true) {

        %>
          <div><i>Must not be blank</i></div><%

    } else if (isAuthorNameTooLong == true) {

        %>
          <div><i>Must not be longer than 100 characters</i></div><%

    }
      
    %>
        </div>
        <div class="clear"></div>
        <div>
          <button type="submit">Submit</button>
        </div>
        <div class="clear"></div>
      </form><%
    	
} else {
    	
    %>
    <div class="intro">
      <p class="notice">Oh. What happened?</p>
      <p class="notice">This doesn't seem to be the right page.</p>
    </div><%
    	
}
    
%>
<%@include file="bottom.jspf" %>