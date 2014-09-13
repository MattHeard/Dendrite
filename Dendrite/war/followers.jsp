<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%

%><%@ page import="com.deuteriumlabs.dendrite.view.FollowersView" %><%

/*
 * The JSTL functions provide `escapeXml(...)`. Currently, all
 * `pageContext` attributes are escaped to prevent malicious injections. Some
 * attributes may not need to be escaped but I cannot currently guarantee that.
 */
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

final FollowersView view = new FollowersView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

// `top.jspf` contains the JSP code starting the HTML page and displaying the
// header bar which is common to all Dendrite web pages.
%><%@include file="top_simplified_theming.jspf" %>
        <div id="author_side_bar">
          <img id="author_avatar" src="/img/avatar/2014-09-06-0/large/${fn:escapeXml(avatarId)}.png" />
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
          <ul class="tabs"><%
          
view.prepareAuthorPageUrl();
          
          %>
            <li><a href="${fn:escapeXml(authorPageUrl)}">Pages Written</a></li>
            <li class="selected">Followers</li>
            <li id="more"><a>⋮</a></li>
          </ul>
          <ul id="followerList"><%
          
for (final String id : view.getFollowerIds()) {
	view.prepareFollower(id);
	
	%>
            <li>
              <img
                  class="smallAvatar"
                  src="/img/avatar/2014-09-06-0/small/${fn:escapeXml(followerAvatarId)}.png" />
              <a href="${fn:escapeXml(followerProfileUrl)}">${fn:escapeXml(followerName)}</a>
            </li><%
	
}
          
          %>
          </ul>
        </div>
<%@include file="bottom.jspf" %>