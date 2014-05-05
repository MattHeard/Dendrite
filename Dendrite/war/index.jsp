<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.ContentsView"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite");
final ContentsView view = new ContentsView();

final String pParameter = request.getParameter("p");
int contentsPageNumber;
try {
    contentsPageNumber = Integer.parseInt(pParameter);
} catch (NumberFormatException e) {
    contentsPageNumber = 1;
}
view.setContentsPageNumber(contentsPageNumber);

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
        <div class="itemContent"><a class="${fontColourClassName} ${themeClassName}" href="${link}">${title}</a></div>
        <div class="itemNumber">${pageNumber}</div>
      </div><%
    }
    
    %>
      <p><%
    
    final boolean isFirstPage = view.isFirstPage();
    if (isFirstPage == false) {
    	final String prev = view.getPrevPageNumber();
    	pageContext.setAttribute("prev", prev);
    	
    	%>
        <div><a class="${fontColourClassName} ${themeClassName}" href="/index.jsp?p=${prev}">Previous</a></div><%
    	
    }
    final boolean isLastPage = view.isLastPage();
    if (isLastPage == false) {
    	final String next = view.getNextPageNumber();
    	pageContext.setAttribute("next", next);
    	
    	%>
        <div><a class="${fontColourClassName} ${themeClassName}" href="/index.jsp?p=${next}">Next</a></div><%
    	
    }
	
	%>
      </p>
      <p id="newStory"><a class="${fontColourClassName} ${themeClassName}" href="/new.jsp">Start a new story</a></p>
<%@include file="bottom.jspf" %>