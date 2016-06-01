<% /* © 2013-2015 Deuterium Labs Limited */ %>
<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
%><%@ page import="com.deuteriumlabs.dendrite.view.AltView"
%><%@ page import="com.deuteriumlabs.dendrite.view.AltView.Alt"
%><%@ page import="com.deuteriumlabs.dendrite.model.User"
%><%

final User myUser = User.getMyUser();
final AltView view = new AltView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%><%

if (view.isPageNumInvalid()) {
	
	%>
        <p class="notice">Uh oh. This isn't the right page. To view the
          alternative versions of a page, click the "Alternatives" button on
          that page.</p><%
	
} else {

%>
        <ul class="alt"><%

final List<Alt> alts = view.getAlts();
for (final Alt alt : alts) {
	view.prepareAlt(alt);
	
    %>
          <li>
            <div class="pageId">
              <a href="/read?p=${fn:escapeXml(pageId)}">${fn:escapeXml(pageId)}</a>
            </div>
            <div class="altBody">
            <div class="summary">"${fn:escapeXml(summary)}"</div>
            <ul class="tags"><%
              
    final List<String> tags = alt.tags;
    for (final String tag : tags) {
    	view.prepareTag(tag);
    	
    	%>
              <li class="${fn:escapeXml(tagClass)}">
                ${fn:escapeXml(tagName)}
              </li><%
    	
    }

%>
            </ul>
            <div class="clear"></div>
            <div class="authorAndLove">
              <div class="author"><%
              
if (alt.authorId != null) {
              
              %>
                <a href="/author?id=${fn:escapeXml(authorId)}">
                  <%
                  
}
                  
                  %>${fn:escapeXml(authorName)}<% 
                  
if (alt.authorId != null) {
                  
                  %></a><%
                
}
                
                %>
                <img class="smallAvatar"
                    src="/static/img/avatar/2014-09-06-0/small/${fn:escapeXml(authorAvatarId)}.png"
                    alt="${fn:escapeXml(authorAvatarDesc)}"
                    />
              </div>
              <div class="love">
                ${fn:escapeXml(numLovers)}
                <img class="heartIcon<%
              
    if (alt.numLovers < 1) {
        
        // Turn down the opacity of the heart by applying the 'faded' class.
        %> faded<%
        
    }
              
    %>" src="/static/img/icons/2014-09-01-0/heart.png" alt="A small heart." />
              </div>
            </div>
            </div>
            <div class="clear"></div>
          </li><%
	
}

%>
        </ul>
<%

}

%><%@include file="bottom.jspf" %>