<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AboutView"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - About");
final View view = new AboutView();

%><%@include file="top.jspf"

%>
        <h2>About Dendrite</h2>
        <p><i>Dendrite</i> is an online, choose-your-own-adventure book that you
          can both read, and write. This allows you, the reader and author, to
          participate in the story however you see fit. The stories branch
          through various pathways, with endless potential to read, write,
          re-write, edit, and change the story to make it truly your own. Find
          your way through other authors' stories, and pick up where they left
          off, creating your own thrilling plot lines to pique your
          interest.</p>
        <p>Dendrites, from the Greek <i>δένδρον</i> (meaning <i>tree</i>), are
          spindly tentacles which connect the nucleus of one brain cell to
          another, very similar to the way that links on <i>Dendrite</i> connect
          the pages of each story. The dendrites form a significant part in
          growth and learning in the brain, and slowly extend to connect to
          other brain cells, forming new thoughts and memories in the
          process.</p>
        <p>You, the reader and author, can take on a role as a part of the
          adventure—you can be a spy, a princess, a pirate, a rockstar, a ninja,
          a space cowboy, an explorer, a chef, a supermodel, a scientist, or
          write the story and watch it unfold for any and all of these
          characters.</p>
        <p><i>Dendrite</i> is a creation of <i>Deuterium Labs</i>.</p>
<%@include file="bottom.jspf" %>