<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%

/*
 * The AuthorView contains the view logic for the pages describing the users of
 * Dendrite. This JSP page should not perform any calculation itself but instead
 * merely call functions from the AuthorView. This ensures that the HTML and the
 * Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.AuthorView" %><%

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

final AuthorView view = new AuthorView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

// `top.jspf` contains the JSP code starting the HTML page and displaying the
// header bar which is common to all Dendrite web pages.
%><%@include file="top.jspf" %>
        <div id="author_side_bar">
          <img id="author_avatar" src="avatars/${fn:escapeXml(avatarId)}.png" />
          <h1>${fn:escapeXml(penName)}</h1><%

if (view.isAuthorPageOfUser() == true) {
    
        %>
          <p><a href="/preferences.jsp">Preferences</a></p><%
    
}
    
%>
        </div>
        <div id="author_body">
          <h2>Pages Written</h2><%

while (view.hasAnotherStoryPage() == true) {
	view.prepareNextStoryPage();
	if (view.isThisStoryPageInADifferentStory() == true) {
		
		%>
          <h3 class="do_not_clear">${fn:escapeXml(title)}</h3><%
          
	}
    
    %>
          <div class="item">
            <div class="itemContent"><a
                href="/read.jsp?p=${fn:escapeXml(pageId)}"
                >${fn:escapeXml(summary)}</a><%
                
    if (view.isThisStoryPageCreditedDifferently() == true) {
    	
    	%> (credited as <i>${fn:escapeXml(authorName)}</i>)<%
    	
    }
                
%></div>
            <div class="itemNumber">${fn:escapeXml(pageId)}</div>
          </div>
          <div class="clear"></div><%
        
}
    
final boolean isFirstPage = view.isFirstPage();
if (isFirstPage == false) {
	final String id = view.getId();
	pageContext.setAttribute("id", id);
	final String prev = view.getPrevPageNumber();
	pageContext.setAttribute("prev", prev);
    	
    %>
          <div><a
              href="/author.jsp?id=${fn:escapeXml(id)}&p=${fn:escapeXml(prev)}">Previous</a></div><%
    	
}
final boolean isLastPage = view.isLastPage();
if (isLastPage == false) {
	final String id = view.getId();
	pageContext.setAttribute("id", id);
	final String next = view.getNextPageNumber();
	pageContext.setAttribute("next", next);
    	
    %>
          <div><a
              href="/author.jsp?id=${fn:escapeXml(id)}&p=${fn:escapeXml(next)}">Next</a></div><%
    	
}
	
%>
        </div>
<%@include file="bottom.jspf" %>