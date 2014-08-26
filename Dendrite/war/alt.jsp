<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
%><%@ page import="com.deuteriumlabs.dendrite.view.AltView"
%><%@ page import="com.deuteriumlabs.dendrite.view.AltView.Alt"
%><%

final AltView view = new AltView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();


%><%@include file="top_simplified_theming.jspf"

%>
        <ul class="alt"><%

final List<Alt> alts = view.getAlts();
for (final Alt alt : alts) {
	
    %>
          <li>
            <div class="pgId">
              <a href="/read?p=<%= alt.pgId %>"><%= alt.pgId %></a>
            </div>
            <div class="altBody">
            <div class="summary">"<%= alt.summary %>"</div>
            <ul class="tags"><%
              
    final List<String> tags = alt.tags;
    for (final String tag : tags) {
    	
    	%>
              <li class="<%= tag.toLowerCase() %>">
                <%= tag.toUpperCase() %>
              </li><%
    	
    }

%>
            </ul>
            <div class="clear"></div>
            <div class="authorAndLove">
              <div class="author">
                <a href="/author?id=<%= alt.authorId %>">
                  <%= alt.authorName %>
                </a>
                <img class="smallAvatar"
                    src="/img/avatar/2014-08-20-0/<%= alt.authorAvatarId %>.png"
                    />
              </div>
              <div class="love">
                <%= alt.numLovers %>
                <img class="heartIcon<%
              
    if (alt.numLovers < 1) {
        
        // Turn down the opacity of the heart by applying the 'faded' class.
        %> faded<%
        
    }
              
    %>" src="icons/heart.png" />
              </div>
            </div>
            </div>
            <div class="clear"></div>
          </li><%
	
}

%>
        </ul>
<%@include file="bottom.jspf" %>