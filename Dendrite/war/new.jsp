<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><% 

/*
 * The NewView contains the view logic for the table of contents on Dendrite.
 * This JSP page should not perform any calculation itself but instead merely
 * call functions from the NewView. This ensures that the HTML and the Java
 * components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.NewView" %><%

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%
%><%

final NewView view = new NewView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top.jspf"

%>
        <h2>Start a new story</h2>
        <form action="submitNewStory" method="post">
          <div id="form_body">
            <label for="title">Title</label>
            <br />
            <input id="title" name="title" type="text"></input><%

if (view.isTitleBlank() == true) {

    %>
            <i>Must not be blank</i><%
		
} else if (view.isTitleTooLong() == true) {
		
    %>
            <i>Must not be longer than 100 characters</i><%
		
}
      
%>
            <br />
            <label for="content">Story</label>
            <span id="contentCountNote">(<span id="contentCount">5000</span>
                characters remaining)
            </span>
            <br />
            <textarea id="content" name="content"></textarea><%
      
if (view.isContentBlank() == true) {
		
    %>
            <i>Must not be blank</i><%
		
} else if (view.isContentTooLong() == true) {
		
    %>
            <i>Must not be longer than 5000 characters</i><%
		
}
      
%>
            <br />
            <label for="option0">Options</label><%
      
if (view.isAnOptionTooLong() == true) {

    %>
            <i>Must not be longer than 80 characters</i><%
  		
}
  	
%>
            <br /><%

for (int i = 0; i < 5; i++) {
    pageContext.setAttribute("optionNumber", i);
        	
    %>
            <input
                id="option${fn:escapeXml(optionNumber)}"
                name="option${fn:escapeXml(optionNumber)}"
                type="text"></input>
            <br /><%
        	
}

if (isUserLoggedIn == true) {
    final String authorId = NewView.getMyUserId();
    pageContext.setAttribute("authorId", authorId);
      
    %>
            <input
                name="authorId"
                type="hidden"
                value="${fn:escapeXml(authorId)}" /><%
      
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