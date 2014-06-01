<%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %><%
        
/*
 * The AboutView contains the view logic for the pages describing the users of
 * Dendrite. This JSP page should not perform any calculation itself but instead
 * merely call functions from the AboutView. This ensures that the HTML and the
 * Java components are cleanly separated.
 */
%><%@ page import="com.deuteriumlabs.dendrite.view.AboutView"
%><%

final AboutView view = new AboutView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top.jspf"

%>
        <div id="tabs">
          <a href="about.jsp">About</a>
          <a class="selected_tab">Contributors</a>
          <a id="more" href="about_more.jsp">⋮</a>
        </div>
        <div class="clear"></div>
        <h3>Developer</h3>
        <ul class="contributors">
          <li>
            <div>
              <b>Matthew Heard</b>: software developer behind Dendrite. He began
                  working on Dendrite in 2013. Matthew's primary motivation for
                  Dendrite is to build a creative environment which gets better
                  when more people create stories, as he wants to explore how
                  network effects and organic user contributions can combine to
                  bloom new communities.
            </div>
            <div>
              Matthew has optimised Dendrite to run well on multiple platforms
                  and browsers, as he considers that accessibility is pivotal to
                  obtaining collaboration from a wide variety of users.
            </div>
            <div>
              Matthew is a sustaining software developer for Oracle in New
                  Zealand. He primarily works with C++, Java, and SQL to find
                  bugs and provide software patches for the telecommunication
                  networks of Oracle customers.
            </div>
            <div>
              Matthew writes a blog at <a
                  href="http://mattheard.net">MattHeard.net</a>.
            </div>
          </li>
        </ul>
        <h3>Significant help</h3>
        <ul class="contributors">
          <li>
            <b>Leah Hamilton</b>: assisted with the general creative direction
            of Dendrite, and provided significant amounts of feedback when
            testing early releases of Dendrite. She also wrote the <i>About</i>
            and <i>Contributors</i> pages, and drafted the <i>Terms</i> and
            <i>Privacy</i> pages. Leah is a solicitor at Minter Ellison Rudd
            Watts, as well as an editor, writer, and tutor.
          </li>
          <li>
            <b>Ryan Hamilton</b>: provided creative direction and web design
            assistance, as well as logo design and feedback from testing early
            releases of Dendrite. Ryan is a service designer for the Inland
            Revenue Department of New Zealand.
          </li>
        </ul>
        <h3>Beta testing and bug reporting</h3>
        <ul>
          <li>Calum Barrett</li>
          <li>Neil Dudley</li>
          <li>Duncan Hamilton</li>
          <li>Sean Hamilton</li>
          <li>John Heard</li>
          <li>Nic Henwood</li>
          <li>Jack Li</li>
          <li>Bayard Randel</li>
          <li>Chris Youngson</li>
        </ul>
        <i>If you would like to contact the developer or either of the major
        contributors, please send a message via the
        <a href="/contact.jsp">Contact</a> form.</i>
<%@include file="bottom.jspf" %>