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
    <link
        href='http://fonts.googleapis.com/css?family=Open+Sans:300'
        rel='stylesheet'
        type='text/css'>
    <meta name="viewport" content="width=device-width">
    <title>Dendrite</title>
    <script>
    
// Google Analytics
    
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-53889088-1', 'auto');
ga('send', 'pageview');

    </script>
  </head>
  <body>
    <img src="/img/logo/2014-08-20-0/cover.png">
    <p>Read or write your own adventure!</p>
    <ul>
      <li>
        <form action="/contents">
          <button class="link" type="submit">READ</button>
        </form>
      </li>
      <li>
        <form action="/new">
          <button class="link" type="submit">WRITE</button>
        </form>
      </li>
    </ul>
    <p>Filter stories by genre</p>
    <form action="/contents" method="get" class="filter">
      <select name="filter"><%
          
final String[] tagNames = { "SCIFI", "WESTERN", "ROMANCE", "FANFIC", "ACTION",
    "ADVENTURE", "COMEDY", "CRIME", "DRAMA", "EROTIC", "FANTASY", "HISTORICAL",
    "HORROR", "INSPIRATIONAL", "MYSTERY", "POLITICAL", "RELIGIOUS",
    "THRILLER", "SPAM" };
for (final String tag : tagNames) {
    
    %>
            <option><%= tag %></option><%
    
}
          
          %>
      </select>
      <button type="submit">OK</button>
    </form>
  </body>
</html>