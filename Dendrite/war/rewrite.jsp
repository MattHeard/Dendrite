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



%><%@include file="top.jspf"

%><%
  
final boolean isExistingPage = view.isExistingPage();
if (isExistingPage == true) {
    pageContext.setAttribute("pageNumber", pageNumber);
      
    %>
      <h3>The <i>rewrite</i> option allows you to completely rewrite the text of
          this page. You will be credited as the only author.</h3><%
    
    final boolean isBeginning = view.isBeginning();
    if (isBeginning == true) {
    	
    	final String storyTitle = view.getStoryTitle();
    	pageContext.setAttribute("title", storyTitle);
    	
    	%>
      <h3>${fn:escapeXml(title)}</h3><%
    	
    } else {
    
        final String incomingOptionText = view.getIncomingOptionText();
        pageContext.setAttribute("option", incomingOptionText);
      
        %>
      <h3>${fn:escapeXml(option)}</h3><%
    
    }
    
    %>
      <form action="submitRewrite" method="post">
        <div id="form_body">
        <input type="hidden" name="pageNumber"
            value="${fn:escapeXml(pageNumber)}" />
        <label for="content">Story</label>
        <span
            id="contentCountNote"
            class="countNote">(<span id="contentCount">5000</span>
            characters remaining)
        </span>
        <br />
        <textarea id="content" name="content"><%

final boolean isDraftPending = view.isDraftPending();
if (isDraftPending == true) {
    view.prepareDraftContent();
    
    %>${fn:escapeXml(draftContent)}<%
}
            
%></textarea><%

    final boolean isContentBlank = view.isContentBlank();
    final boolean isContentTooLong = view.isContentTooLong();
    if (isContentBlank == true) {
			
        %>
        <i>Must not be blank</i><%
		
    } else if (isContentTooLong == true) {
	
        %>
        <i>Must not be longer than 5000 characters</i><%
		
    }

    %>
        <br />
        <label for="option0">Options</label><%

    final boolean isAnOptionTooLong = view.isAnOptionTooLong();
    if (isAnOptionTooLong == true) {
      		
    %>
        <i>Must not be longer than 80 characters</i><%
  		
		}
  	
    %>
        <span
            id="optionCountNote"
            class="countNote">(<span id="optionCount">80</span>
            characters remaining)
        </span>
        <br /><%
      
    for (int i = 0; i < 5; i++) {
        pageContext.setAttribute("optionNumber", i);
        
        if (view.isDraftPending() == true) {
            view.prepareDraftOption(i);
        }
        	
        %>
        <input id="option${fn:escapeXml(optionNumber)}"
            name="option${fn:escapeXml(optionNumber)}"
            type="text" value="${fn:escapeXml(draftOption)}"></input>
        <br /><%
        	
    }
    if (isUserLoggedIn == true) {
        final String authorId = RewriteView.getMyUserId();
        pageContext.setAttribute("authorId", authorId);
        
        %>
          <input name="authorId" type="hidden"
              value="${fn:escapeXml(authorId)}" /><%
        
    }
      
    %>
          <label for="authorName">Author</label>
          <span
              id="authorNameCountNote"
              class="countNote">(<span id="authorNameCount">100</span>
              characters remaining)
          </span>
          <br />
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
          <i>Must not be blank</i><%
    		
    } else if (isAuthorNameTooLong == true) {
    		
        %>
          <i>Must not be longer than 100 characters</i><%
    		
    }
      
    %>
          <br />
          </div>
          <button type="submit">Submit</button>
      </form><%
    	
} else {
    	
    %>
    Oh. What happened? This doesn't seem to be the right page.<%
    	
}
    
%>
<%@include file="bottom.jspf" %>