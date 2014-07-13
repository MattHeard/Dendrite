<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.CoverView"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%

final CoverView view = new CoverView();
view.setPageContext(pageContext);

%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="cover.css">
    <meta name="viewport" content="width=device-width">
    <title>Dendrite</title>
  </head>
  <body>
    <div id="main">
      <div id="welcome">
        <a href="index"><img id="logo" src="logo/head.png"/></a>
        <h1>Dendrite</h1>
        <p>Read or write your own adventure.</p>
      </div>
      <div id="actions">
        <a href="index"><h2 class="option">READ</h2></a>
        <a href="new"><h2 class="option">WRITE</h2></a>
      </div>
      <div id="explanation">
        <p>Click the "Read" button above to go to the table of contents.</p>
        <p>Click the "Write" button to start your own story.</p>
        <p>Or select one of the three stories below!</p>
      </div><%

view.findThreeStories();

%>
      <div id="stories">
        <a href="read?p=16">
          <div class="story first">
            <h2>${fn:escapeXml(firstStoryTitle)}</h2>
            <p>${fn:escapeXml(firstStorySummary)}</p>
          </div>
        </a>
        <a href="read?p=16">
          <div class="story second">
            <h2>${fn:escapeXml(secondStoryTitle)}</h2>
            <p>${fn:escapeXml(secondStorySummary)}</p>
          </div>
        </a>
        <a href="read?p=16">
          <div class="story third">
            <h2>${fn:escapeXml(thirdStoryTitle)}</h2>
            <p>${fn:escapeXml(thirdStorySummary)}</p>
          </div>
        </a>
      </div>
    </div>
  </body>
</html>