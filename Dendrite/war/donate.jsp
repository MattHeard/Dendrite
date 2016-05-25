<% /* © 2013-2015 Deuterium Labs Limited */ %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.TermsView"
%><%@ page import="com.deuteriumlabs.dendrite.model.User"
%><%

pageContext.setAttribute("webPageTitle", "Dendrite - Donate");
final User myUser = User.getMyUser();
final TermsView view = new TermsView();
view.setPageContext(pageContext);

%><%@include file="top_simplified_theming.jspf"

%>
      <h1 class="donate">Your donations keep us online</h1>
      <p class="donate">Dendrite is experiencing higher traffic than we expected and our one-person development team is having trouble keeping up. Please
          consider donating to keep Dendrite up and running.</p>
      <p>You can donate to us via PayPal below:</p>
      <form class="donate" action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>
<%@include file="bottom.jspf" %>