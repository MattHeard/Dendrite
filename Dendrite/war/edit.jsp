<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.EditView"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Edit");
final EditView view = new EditView();

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
      <h2>Edit a page</h2>
      <form action="submitEdit" method="post">
        <input type="hidden" name="pageNumber" value="${pageNumber}" />
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
        <input id="option${optionNumber}" name="option${optionNumber}"
            type="text"></input>
        <br /><%
        	
    }
    if (isUserLoggedIn == true) {
        final String authorId = EditView.getMyUserId();
        pageContext.setAttribute("authorId", authorId);
        
        %>
          <input name="authorId" type="hidden" value="${authorId}" /><%
        
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
          <button type="submit">Submit</button>
      </form><%
    	
} else {
    	
    %>
    Oh. What happened? This doesn't seem to be the right page.<%
    	
}
    
%>
<%@include file="bottom.jspf" %>