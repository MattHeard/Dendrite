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
%><%@include file="top_simplified_theming.jspf" %>
        <div id="author_side_bar">
          <img id="author_avatar" src="/img/avatar/2014-09-02-0/large/${fn:escapeXml(avatarId)}.png" />
          <h1>${fn:escapeXml(penName)}</h1><%

if (view.isAuthorPageOfUser() == true) {
    
        %>
          <p><a href="/preferences">Preferences</a></p><%
    
} else if (view.isUserLoggedIn() == true){
	final boolean isUserFollowingAuthor = view.isUserFollowingAuthor();
	if (isUserFollowingAuthor == true) {
    
        %><div id="unfollow" class="button">Unfollow</div><%
                
    } else {
	
	    %><div id="follow" class="button">Follow</div><%
	    		
	}
	
	view.prepareAuthorId();
	
	%>
          <script>var AUTHOR_ID = "${fn:escapeXml(authorId)}";</script><%
}
    
%>
        </div>
        <div id="author_body">
          <ul class="tabs">
            <li class="selected">Pages Written</li><%
          
view.prepareFollowersPageUrl();
          
%>
            <li><a href="${followersPageUrl}">Followers</a></li>
            <li id="more"><a>⋮</a></li>
          </ul><%

final boolean hasAnotherStoryPage = view.hasAnotherStoryPage();
if (hasAnotherStoryPage == false) {
	
	if (view.isAuthorPageOfUser() == true) {

    %>
          <p class="notice">Hmm. No stories here yet. Want to
              <a href="/new">start one</a>?</p><%
            		  
	} else {
		
    %>
        <p class="notice">Hmm. No stories here yet.</p><%
		
	}

} else {

    while (view.hasAnotherStoryPage() == true) {
        view.prepareNextStoryPage();
        if (view.isThisStoryPageInADifferentStory() == true) {
        
            %>
          <h3 class="do_not_clear">${fn:escapeXml(title)}</h3><%
          
        }
    
        %>
          <div class="item">
            <div class="itemNumber">${fn:escapeXml(pageId)}</div>
            <div class="itemContent"><a
                href="/read?p=${fn:escapeXml(pageId)}"
                >${fn:escapeXml(summary)}</a><%
                
        if (view.isThisStoryPageCreditedDifferently() == true) {
        
            %> (credited as <i>${fn:escapeXml(authorName)}</i>)<%
        
        }
                
        %></div>
          </div>
          <div class="clear"></div><%
        
    }
}

if (view.isFirstPage() == false) {
    view.preparePrevAuthorPageLink();
        
    %>
          <div><a
              href="/author?id=${fn:escapeXml(id)}&p=${fn:escapeXml(prev)}"
              >Previous</a></div><%
        
}

if (view.isLastPage() == false) {
    view.prepareNextAuthorPageLink();
        
    %>
          <div><a
              href="/author?id=${fn:escapeXml(id)}&p=${fn:escapeXml(next)}"
              >Next</a></div><%
        
}

%>
        </div>
<%@include file="bottom.jspf" %>