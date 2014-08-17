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
view.setSession(session);
view.initialise();

%><%@include file="top_with_side_bar.jspf"

%>
        <h2>Start a new story</h2>
        <div class="clear"></div>
        <form action="submitNewStory" method="post">
          <div>
            <label for="title">Title</label>
            <span
                id="titleCountNote"
                class="countNote">(<span id="titleCount">100</span>
                characters remaining)
            </span>
          </div>
          <div class="clear"></div>
          <div>
            <input id="title" name="title" type="text" value="<%
            
final boolean isDraftPending = view.isDraftPending();
if (isDraftPending == true) {
	view.prepareDraftTitle();
	
	%>${fn:escapeXml(draftTitle)}<%
	
}
            
%>"></input>
          </div>
          <div class="clear"></div><%

if (view.isTitleBlank() == true) {

    %>
          <div>
            <i>Must not be blank</i>
          </div>
          <div class="clear"></div><%
              
		
} else if (view.isTitleTooLong() == true) {
		
    %>
          <div>
            <i>Must not be longer than 100 characters</i>
          </div>
          <div class="clear"></div><%
		
}
     
%>
            
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
      
if (view.isContentBlank() == true) {
		
    %>
          <div>
            <i>Must not be blank</i>
          </div>
          <div class="clear"></div><%
		
} else if (view.isContentTooLong() == true) {
		
    %>
          <div>
            <i>Must not be longer than 5000 characters</i>
          </div>
          <div class="clear"></div><%
		
}
      
%>
          <div>
            <label for="option0">Options</label><%
      
if (view.isAnOptionTooLong() == true) {

    %>
            <i>Must not be longer than 80 characters</i><%
  		
}
  	
%>
            <span
                id="optionCountNote"
                class="countNote">(<span id="optionCount">80</span>
                characters remaining)
            </span>
          </div>
          <div class="clear"></div>
          <div><div id="optionSet"><%

for (int i = 0; i < 5; i++) {
    pageContext.setAttribute("optionNumber", i);
    
    if (view.isDraftPending() == true) {
        view.prepareDraftOption(i);
    }
        	
    %>
            <input
                id="option${fn:escapeXml(optionNumber)}"
                name="option${fn:escapeXml(optionNumber)}"
                type="text"
                value="${fn:escapeXml(draftOption)}"></input><%
        	
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
            </aside></div><%

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
            <i>Must not be blank</i><%
		
} else if (isAuthorNameTooLong == true) {
		
    %>
            <i>Must not be longer than 100 characters</i><%
		
}
      
%>
          </div>
          <div class="clear"></div>
          <div>
            <button type="submit">Submit</button>
          </div>
          <div class="clear"></div>
        </form>
<%@include file="bottom.jspf" %>