<% /* © 2013-2015 Deuterium Labs Limited */ %>
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
%><%@ page import="com.deuteriumlabs.dendrite.model.User" %><%
%><%@ page import="com.deuteriumlabs.dendrite.view.FollowersView" %><%

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

final User myUser = User.getMyUser();
final AuthorView view = new AuthorView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

// `top.jspf` contains the JSP code starting the HTML page and displaying the
// header bar which is common to all Dendrite web pages.
%><%@include file="top_simplified_theming.jspf" %><%

if (view.isAuthorValid()) {

%>
        <div id="author_side_bar">
          <img id="author_avatar"
              src="/static/img/avatar/2014-09-06-0/large/${fn:escapeXml(avatarId)}.png"
              alt="${fn:escapeXml(avatarDesc)}" />
          <h1 id="author_name">${fn:escapeXml(penName)}</h1><%

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
          <div class="author accordion">
            <ul class="tabs">
              <li class="pages_written selected">Pages Written</li>
              <li class="followers">Followers</li>
            </ul>
            <section class="pages_written">
              <h2>Pages Written</h2>
              <div><%
              
if (view.isInvalidPgNum()) {
	view.preparePg1Url();
	
	%>
                <p class="notice">Are you lost? There are no stories on this
                  page. <a href="${fn:escapeXml(pg1Url)}">Go to page
                  1.</a></p><%
	
} else {

final boolean hasAnotherStoryPage = view.hasAnotherStoryPage();
if (hasAnotherStoryPage == false) {
	if (view.isAuthorPageOfUser() == true) {

    %>
                <p class="notice">Hmm. No stories here yet.
                  Want to <a href="/new">start one</a>?
                </p><%
		
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
                  <div class="itemContent">
                    <a href="/read?p=${fn:escapeXml(pageId)}"
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
                <div class="prev">
                  <a href="/author?id=${fn:escapeXml(id)}&p=${fn:escapeXml(prev)}"
                      >Previous</a>
                </div><%
        
}

if (view.isLastPage() == false) {
    view.prepareNextAuthorPageLink();
        
    %>
                <div class="next">
                  <a href="/author?id=${fn:escapeXml(id)}&p=${fn:escapeXml(next)}"
                      >Next</a>
                </div><%
        
}

}

%>
              </div>
            </section>
            <section class="followers">
              <h2>Followers</h2>
              <div>
                <ul id="followerList"><%

final FollowersView followersView = new FollowersView();
followersView.setPageContext(pageContext);
followersView.setRequest(request);
followersView.initialise();

for (final String id : followersView.getFollowerIds()) {
    followersView.prepareFollower(id);
    
    %>
                  <li>
                    <img
                        class="smallAvatar"
                        alt="${fn:escapeXml(followerAvatarDesc)}"
                        src="/static/img/avatar/2014-09-06-0/small/${fn:escapeXml(followerAvatarId)}.png"
                        />
                    <a href="${fn:escapeXml(followerProfileUrl)}">${fn:escapeXml(followerName)}</a>
                  </li><%
    
}
          
%>
                </ul>
              </div>
            </section>
          </div>
        </div><%
        
} else {
	
	%>
	    <p class="notice">This author has mysteriously disappeared. Or maybe
	      they never existed in the first place.</p>
	    <p class="notice">Actually, it's just an invalid author page.</p><%
	
}
        
%>
<%@include file="bottom.jspf" %>