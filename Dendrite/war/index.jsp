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
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

// `top.jspf` contains the JSP code starting the HTML page and displaying the
// header bar which is common to all Dendrite web pages.
%><%@include file="top.jspf"

%>
        <h2>${fn:escapeXml(bodyMainTitle)}</h2><%

// Display the links to the stories which have already been written. Only ten
// links are shown at one time.
while (view.hasAnotherLink() == true) {
	view.prepareNextLink();
    
    %>
        <div class="item">
          <div class="itemContent"><a href="${fn:escapeXml(link)}">${fn:escapeXml(title)}</a></div>
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