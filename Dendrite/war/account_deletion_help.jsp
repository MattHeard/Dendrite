<% /* © 2013-2015 Deuterium Labs Limited */ %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.AccountDeletionHelpView"
%><%@ page import="com.deuteriumlabs.dendrite.model.User"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Account Deletion Help");
final User myUser = User.getMyUser();
final AccountDeletionHelpView view = new AccountDeletionHelpView();
view.setPageContext(pageContext);

%><%@include file="top_simplified_theming.jspf"

%>
      <h1>Account Deletion</h1>
      
      <h3>Help</h3>
      <p>At this time, accounts are manually deleted by the operators of
        Dendrite, so to have your account deleted, you must tick the checkbox
        labeled "Request account deletion" on your
        <a href="/preferences">preferences</a> page.</p>
      <p>When you check this box and update your preferences, your account will
        be flagged for deletion. We periodically check for accounts which have
        been flagged for deletion, but cannot guarantee any particular
        time-frame. If you have requested that your account be deleted, but it
        still has not been deleted, please contact us via
        <a href="/contact">our contact form</a> or
        <a href="mailto:matt+dendrite+privacy@mattheard.net">email us</a>.</p>
      <p>When you create an account with Dendrite, we never ask for any
        personal information. Even the Google email address that you use to log
        in is not stored in our system. Deleting your account will primarily
        delete your layout preferences.</p>
        
      <h3>What is deleted?</h3>
      <ul>
        <li>Your default pen name</li>
        <li>Your display settings</li>
        <li>Your selected avatar</li>
      </ul>
      
      <h3>What is kept?</h3>
      <h4>The pages you have written</h4>
      <p>When you write a page for a story on Dendrite, other writers can
        extend your story and build their own story out of yours. If we deleted
        any pages you wrote along with your account, writers would not be able
        to collaborate and feel confident that their contributions will still
        be accessible.</p>
      <p>For this reason, you grant us a licence to continue displaying your
        submitted stories and pages, even after your account is deleted. For
        more information, please look at our <a href="/privacy">privacy
        policy</a>.</p>
      <p>We remove the link from the pages you wrote to your account, so they
        appear and function as if you wrote them anonymously.<p>
        
      <h4>The pages you have loved</h4>
      <p>When you are logged into your account and you love a page on Dendrite,
        your "love" helps make that page and that story more visible to other
        readers. When you delete your account, that "love" remains recorded for
        that purpose, but it is no longer associated with your account. The
        pages you like can never be used to identify you, before or after your
        account is deleted.<p>
        
      <h4>System logs for the last month</h4>
      <p>Our logs record when visitors access Dendrite and help us diagnose
        errors and fix bugs.<p>
      <p>Those logs last for one month and are deleted after that time.</p>
      <p>There is some personal information in those logs, and none of it is
        recorded elsewhere. It is all deleted when the logs expire after a
        month. The information that might be included in the logs are:</p>
      <ul>
        <li>Your IP address</li>
        <li>Part or all of your Google account email address, if you are logged
          in to Dendrite.</li>
      </ul>
      <p>Again, this personal information is not used and is deleted after a
        month.</p>
        
      <h3>Contact us</h3>
      <p>Our users privacy is important to us, which is why we collect as
        little information about you as possible in the first place.</p>
      <p>However, if you ever have any concerns about how your account
        information is managed, please contact us via our
        <a href="/contact">contact form</a> or by
        <a href="mailto:matt+dendrite+privacy@mattheard.net">emailing us</a>.
      
      <h3>Latest update</h3>
      <p>This help guide was last updated 4 September 2016.</p>
<%@include file="bottom.jspf" %>