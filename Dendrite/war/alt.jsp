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
	view.prepareAlt(alt);
	
    %>
          <li>
            <div class="pgId">
              <a href="/read?p=${fn:escapeXml(pgId)}">${fn:escapeXml(pgId)}</a>
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
                  
                  %>
                </a><%
                
}
                
                %>
                <img class="smallAvatar"
                    src="/img/avatar/2014-08-20-0/${fn:escapeXml(authorAvatarId)}.png"
                    />
              </div>
              <div class="love">
                ${fn:escapeXml(numLovers)}
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