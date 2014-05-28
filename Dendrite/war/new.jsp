<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.NewView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - New Story");
final NewView view = new NewView();

final String error = request.getParameter("error");
view.setError(error);

%><%@include file="top.jspf"

%>
    <h2>Start a new story</h2>
    <form action="submitNewStory" method="post">
      <div id="form_body">
      <label for="title">Title</label>
      <br />
      <input id="title" name="title" type="text"></input><%
      
	final boolean isTitleBlank = view.isTitleBlank();
	final boolean isTitleTooLong = view.isTitleTooLong();
	if (isTitleBlank == true) {

		%>
      <i>Must not be blank</i><%
		
	} else if (isTitleTooLong == true) {
		
		%>
      <i>Must not be longer than 100 characters</i><%
		
	}
      
	%>
      <br />
      <label for="content">Story</label><br />
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
      <input id="option${fn:escapeXml(optionNumber)}" name="option${fn:escapeXml(optionNumber)}"<%
      %> type="text"></input>
      <br /><%
        	
    }
    if (isUserLoggedIn == true) {
    	final String authorId = NewView.getMyUserId();
    	pageContext.setAttribute("authorId", authorId);
      
    %>
      <input name="authorId" type="hidden" value="${fn:escapeXml(authorId)}" /><%
      
    }
    
      %>
      <label for="authorName">Author</label>
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
    </form>
<%@include file="bottom.jspf" %>