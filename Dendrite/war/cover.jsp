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
    <link rel="stylesheet" type="text/css"
        href="/static/css/2014-09-28-0/cover.css">
    <link
        href='http://fonts.googleapis.com/css?family=Open+Sans:300'
        rel='stylesheet'
        type='text/css'>
        
    <link rel="apple-touch-icon"
        href="/static/img/apple/2014-09-28-0/touch-icon-iphone.png">
    <link rel="apple-touch-icon" sizes="76x76"
        href="/static/img/apple/2014-09-28-0/touch-icon-ipad.png">
    <link rel="apple-touch-icon" sizes="120x120"
        href="/static/img/apple/2014-09-28-0/touch-icon-iphone-retina.png">
    <link rel="apple-touch-icon" sizes="152x152"
        href="/static/img/apple/2014-09-28-0/touch-icon-ipad-retina.png">
        
    <meta name="title" content="Dendrite">
    <meta name="viewport" content="width=device-width">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="description" content="Dendrite is an online adventure game book where you can choose the story path, or write your own. Be a spy, a princess, a pirate, a rockstar, a ninja...">
    <title>Dendrite</title>
    <script>
    
// Google Analytics
    
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-53889088-1', 'auto');
ga('require', 'displayfeatures');
ga('send', 'pageview');

    </script>
  </head>
  <body>
    <img src="/static/img/logo/2014-08-20-0/cover.png"
        alt="The Dendrite logo: a brown deer head with leaves on its antlers." />
    <p>Read or write your own adventure!</p>
    <ul class="links">
      <li>
        <a href="/contents">READ</a>
      </li>
      <li>
        <a href="/new">WRITE</a>
      </li>
    </ul>
    <p>Filter stories by genre</p>
    <form action="/contents" class="filter">
      <select name="filter"><%
          
final String[] tagNames = { 
        "ACTION",
        "ADVENTURE",
        "COMEDY",
        "CRIME",
        "DRAMA",
        "EROTIC",
        "FANFIC",
        "FANTASY",
        "HISTORICAL",
        "HORROR",
        "INSPIRATIONAL",
        "MYSTERY",
        "POLITICAL",
        "RELIGIOUS",
        "ROMANCE",
        "SCIFI",
        "SPAM",
        "THRILLER",
        "WESTERN",
        "YOUNG-ADULT" };
      
for (final String tag : tagNames) {
	view.prepareTagName(tag);
    
    %>
        <option>${fn:escapeXml(tagName)}</option><%
    
}
          
          %>
      </select>
      <button type="submit">OK</button>
    </form>
  </body>
</html>