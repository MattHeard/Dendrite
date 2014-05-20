<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AuthorView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

final AuthorView view = new AuthorView();
final String id = request.getParameter("id");
view.setId(id);
final String pParameter = request.getParameter("p");
int authorPageNumber;
try {
    authorPageNumber = Integer.parseInt(pParameter);
} catch (NumberFormatException e) {
    authorPageNumber = 1;
}
view.setAuthorPageNumber(authorPageNumber);
final String penName = view.getPenName();

pageContext.setAttribute("penName", penName);
pageContext.setAttribute("webPageTitle", "Dendrite - " + penName);

%><%@include file="top.jspf"

%>
    <div id="author_side_bar"><%

final int avatarId;
if (view.isAuthorAvatarAvailable() == true) {
	avatarId = view.getAuthorAvatarId();
} else {
	avatarId = 1;
}
pageContext.setAttribute("avatarId", avatarId);

%>
      <img id="author_avatar" src="avatars/${avatarId}.png" />
      <h1>${fn:escapeXml(penName)}</h1><%
    
    final String myUserId = AuthorView.getMyUserId();
    if (id.equals(myUserId) == true) {
    
        %>
      <p><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}" href="/preferences.jsp">Preferences</a></p><%
    
    }
    
    %>
    </div>
    <div id="author_body">
      <h2>Pages Written</h2><%
    
    final List<String> titles = view.getTitles();
    final List<String> summaries = view.getSummaries();
    final List<String> pageIds = view.getPageIds();
    final List<String> authorNames = view.getAuthorNames();
    String prevTitle = null;
    
    for (int i = 0; i < titles.size(); i++) {
    	String currTitle = titles.get(i);
    	if (currTitle == null)
    		currTitle = "";
    	final boolean isDifferentTitle = (currTitle.equals(prevTitle) == false);
    	if (isDifferentTitle == true) {
    		final String title = currTitle;
    		pageContext.setAttribute("title", title);
    		
    		%>
      <h3 class="do_not_clear">${fn:escapeXml(title)}</h3><%
	
			prevTitle = currTitle;
    	}
    	String summary = summaries.get(i);
    	if (summary == null)
    		summary = "";
    	pageContext.setAttribute("summary", summary);
    	String pageId = pageIds.get(i);
    	pageContext.setAttribute("pageId", pageId);
    	final String authorName = authorNames.get(i);
    	final boolean isSameAuthorName = (authorName.equals(penName));
    	%>
      <div class="item">
        <div class="itemContent"><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}"
            href="/read.jsp?p=${fn:escapeXml(pageId)}">${fn:escapeXml(summary)}</a><%
	
		if (isSameAuthorName == false) {
			pageContext.setAttribute("authorName", authorName);
			
			%> (credited as <i>${fn:escapeXml(authorName)}</i>)<%
			
		}
	
	%></div>
        <div class="itemNumber">${fn:escapeXml(pageId)}</div>
      </div>
      <div class="clear"></div><%
    }
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	pageContext.setAttribute("id", id);
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
      <div><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}"
          href="/author.jsp?id=${fn:escapeXml(id)}&p=${fn:escapeXml(prev)}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	pageContext.setAttribute("id", id);
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
      <div><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}"
          href="/author.jsp?id=${fn:escapeXml(id)}&p=${fn:escapeXml(next)}">Next</a></div><%
    	
    }
	
	%>
    </div>
<%@include file="bottom.jspf" %>