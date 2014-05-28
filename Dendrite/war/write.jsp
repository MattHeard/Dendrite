<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.WriteView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Write");
final WriteView view = new WriteView();

final String from = request.getParameter("from");
view.setFrom(from);
final String linkIndex = request.getParameter("linkIndex");
view.setLinkIndex(linkIndex);
final String error = request.getParameter("error");
view.setError(error);

%><%@include file="top.jspf"

%><%
      
final boolean isValidOption = view.isValidOption();
if (isValidOption == true) {
	final boolean isConnected = view.isOptionConnected();
	
	if (isConnected == true) {
		int target = view.getTarget();
		response.sendRedirect("/rewrite.jsp?p=" + target);
	}
	
    final String optionText = view.getOptionText();
    pageContext.setAttribute("optionText", optionText);
      
    %>
      <h3>${fn:escapeXml(optionText)}</h3>
      <form action="submitWrite" method="post">
      <div id="form_body"><%
    
	    pageContext.setAttribute("from", from);
	    pageContext.setAttribute("linkIndex", linkIndex);
    
    %>
        <input type="hidden" name="from" value="${fn:escapeXml(from)}" />
        <input type="hidden" name="linkIndex" value="${fn:escapeXml(linkIndex)}" />
        <label for="content">Story</label>
        <br />
        <textarea id="content" name="content"></textarea><%
    	      
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
        <br /><%
      
    for (int i = 0; i < 5; i++) {
        pageContext.setAttribute("optionNumber", i);
        	
        %>
        <input id="option${fn:escapeXml(optionNumber)}" name="option${fn:escapeXml(optionNumber)}"
            type="text"></input>
        <br /><%
        	
    }
    if (isUserLoggedIn == true) {
        final String authorId = view.getMyUserId();
        pageContext.setAttribute("authorId", authorId);
        
        %>
        <input name="authorId" type="hidden" value="${fn:escapeXml(authorId)}" /><%
        
    }
      
    %>
        <label for="authorName">Author</label>
        <br />
        <input id="authorName" name="authorName" type="text"<%
        
    if (isUserLoggedIn == true) {
        
        %> value="${userName}"<%
        
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
    <p>Oh. What happened? This doesn't seem to be the right page.</p><%
   	
}
    
%>
<%@include file="bottom.jspf" %>