<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AuthorView"
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
    <h2>${penName}</h2><%
    
    final String myUserId = AuthorView.getMyUserId();
    if (id.equals(myUserId) == true) {
    
    	%>
      <div><a class="${fontColourClassName} ${themeClassName}" href="/preferences.jsp">My
        preferences</a></div><%
    
    }
    
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
      <h3>${title}</h3><%
	
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
        <div class="itemContent"><a class="${fontColourClassName} ${themeClassName}"
            href="/read.jsp?p=${pageId}">${summary}</a><%
	
		if (isSameAuthorName == false) {
			pageContext.setAttribute("authorName", authorName);
			
			%> (credited as <i>${authorName}</i>)<%
			
		}
	
	%></div>
        <div class="itemNumber">${pageId}</div>
      </div><%
    }
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	pageContext.setAttribute("id", id);
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
      <div><a class="${fontColourClassName} ${themeClassName}"
          href="/author.jsp?id=${id}&p=${prev}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	pageContext.setAttribute("id", id);
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
      <div><a class="${fontColourClassName} ${themeClassName}"
          href="/author.jsp?id=${id}&p=${next}">Next</a></div><%
    	
    }
	
	%>
<%@include file="bottom.jspf" %>