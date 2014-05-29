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

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

final ContentsView view = new ContentsView();

// The value of `webPageTitle` is passed to `top.jspf` and inserted into the
// title of the HTML page.
// TODO: Move this into `top.jspf` after making sure that all Views implement
// `getWebPageTitle()` correctly.
final String webPageTitle = view.getWebPageTitle();
pageContext.setAttribute("webPageTitle", webPageTitle);

view.setContentsPageNumberFromRequest(request);

%><%@include file="top.jspf"

%>
      <h2>Table of Contents</h2><%
	
    final List<String> links = view.getLinks();
    final int length = links.size();
	final List<String> titles = view.getTitles();
    final List<String> pageNumbers = view.getPageNumbers();
    for (int i = 0; i < length; i++) {
    	final String link = links.get(i);
    	pageContext.setAttribute("link", link);
    	final String title = titles.get(i);
    	pageContext.setAttribute("title", title);
    	final String pageNumber = pageNumbers.get(i);
    	pageContext.setAttribute("pageNumber", pageNumber);
    	%>
      <div class="item">
        <div class="itemContent"><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}" href="${fn:escapeXml(link)}">${fn:escapeXml(title)}</a></div>
        <div class="itemNumber">${fn:escapeXml(pageNumber)}</div>
        <div class="clear"></div>
      </div><%
    }
    
    %>
      <p><%
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
        <div><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}" href="/index.jsp?p=${fn:escapeXml(prev)}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
        <div><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}" href="/index.jsp?p=${fn:escapeXml(next)}">Next</a></div><%
    	
    }
	
	%>
      </p>
      <p id="newStory"><a class="${fn:escapeXml(fontColourClassName)} ${fn:escapeXml(themeClassName)}" href="/new.jsp">Start a new story</a></p>
<%@include file="bottom.jspf" %>