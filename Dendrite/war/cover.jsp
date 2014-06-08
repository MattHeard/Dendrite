<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="cover.css">
</head>
<body>
<div id="main">
  <div id="welcome">
    <a href="index.jsp"><img id="logo" src="logo/head.png"/></a>
    <h1>Welcome to Dendrite</h1>
    <p>Dendrite is a read-and-write-your-own-adventure website.</p>
    <p>Click the "Read" button below to go to the Table of Contents.</p>
    <p>Click the "Write" button to start your own story.</p>
    <p>Or select one of the three stories below!</p>
  </div>
  <div id="actions">
    <a href="index.jsp"><div class="option">READ</div></a>
    <a href="new.jsp"><div class="option">WRITE</div></a>
  </div>
  <div>
    <a href="read.jsp?p=16">
      <div class="story first">
        <h2>Leah's adventures in Mattland</h2>
        <p>Once upon a time...</p>
      </div>
      <div class="story second">
        <h2>Leah's adventures in Flatland</h2>
        <p>Once upon a time...</p>
      </div>
      <div class="story third">
        <h2>Leah's adventures in Catland</h2>
        <p>Once upon a time...</p>
      </div>
    </a>
  </div>
</div>
</body>
</html>