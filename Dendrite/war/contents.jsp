<% /* © 2013-2015 Deuterium Labs Limited */ %>
<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
    
/*
 * The ContentsView contains the view logic for the table of contents on
 * Dendrite. This JSP page should not perform any calculation itself but instead
 * merely call functions from the ContentsView. This ensures that the HTML and
 * the Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView" %><%
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView.Link" %><%
%><%@ page import="com.deuteriumlabs.dendrite.dependencies.DatastoreQuery" %><%
%><%@ page import="com.deuteriumlabs.dendrite.dependencies.Store" %><%
%><%@ page import="com.deuteriumlabs.dendrite.model.User" %><%

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

final User myUser = User.getMyUser();
final ContentsView view = new ContentsView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

// `top.jspf` contains the JSP code starting the HTML page and displaying the
// header bar which is common to all Dendrite web pages.
%><%@include file="top_simplified_theming.jspf" %><%

if (view.isPastTheLastPage()) {
	
	%>
        <p class="notice">You have stumbled off the trail. Turn around and go
          back to <a href="/contents">the table of contents</a>.</p><%
	
} else {
    
	%>
        <h1>${fn:escapeXml(bodyMainTitle)}</h1>
        <div id="contents_column_headers">
        	<span>Story</span>
        	<span class="itemNumber">Page #</span>
        </div>
        <ul id="contents"><%

// Display the links to the stories which have already been written. Only ten
// links are shown at one time.
        
final DatastoreQuery query = new DatastoreQuery("StoryBeginning");
final Store store = new Store();
for (final Link link : view.getLinks(store, query)) {
	view.prepareLink(link);
	
	%>
        <li class="item">
          <div class="itemContent"><a
              href="${fn:escapeXml(link)}">${fn:escapeXml(title)}</a></div>
          <div class="itemNumber">${fn:escapeXml(pageNumber)}</div>
          <div class="clear"></div>
          <ul class="contents tags"><%
          
    for (final String tag : ContentsView.getTags(link)) {
    	view.prepareTag(tag);
    	
        %>
            <li class="${fn:escapeXml(tagClass)}">${fn:escapeXml(tagName)}</li><%
    	
    }
          
          %>
          </ul>
          <div class="clear"></div>
        </li><%
        
}
    
%>
        </ul>
        <p id="contentsNav"><%
    
final boolean isFirstPage = view.isFirstPage();
if (isFirstPage == false) {
    view.preparePrevPageLink();
    	
    %>
          <span><a title="First page of contents" href="/contents?p=1">|◀</a></span>
          <span><a title="Previous page of contents" href="${fn:escapeXml(prevLink)}">◀</a></span><%
    	
} else {
	
	%>
          <span>&nbsp;</span><!-- Hacky center balancing --><%
}

view.prepareRomanPageNum();

%>
          <span id="contentsPageNum">${fn:escapeXml(romanPageNum)}</span><%

final boolean isLastPage = view.isLastPage();
if (isLastPage == false) {
	view.prepareNextPageLink();
	view.prepareLastPageLink();
    	
    %>
          <span><a title="Next page of contents" href="${fn:escapeXml(nextLink)}">▶</a></span>
          <span><a title="Last page of contents" href="${fn:escapeXml(lastLink)}">▶|</a></span><%
    	
}
	
%>
        </p>
        <h3 id="newStory"><a href="/new">Start a new story</a></h3><%
        
}

%>
<%@include file="bottom.jspf" %>