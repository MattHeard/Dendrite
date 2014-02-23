<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
%><%@ page import="com.deuteriumlabs.dendrite.view.TermsView"
%><%@ page import="java.util.List"

%><!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Dendrite - Terms of Use</title>
  </head>
  <body>
    <div id="nonFooter">
    <div id="header">
      <div id="logo"><a href="/"><img id="logoImage" src="logo.png"
          /></a></div><%
    
    final TermsView view = new TermsView();
    final boolean isUserLoggedIn = TermsView.isUserLoggedIn();
    if (isUserLoggedIn == true) {
        final String authorLink = TermsView.getAuthorLink();
        pageContext.setAttribute("authorLink", authorLink);
        final String userName = TermsView.getMyUserName();
        pageContext.setAttribute("userName", userName);
        final String logoutLink = view.getLogoutLink();
        pageContext.setAttribute("logoutLink", logoutLink);
        
    %>
      <div id="logout">Welcome back, <a href="${authorLink}">${userName}</a>.
          (<a href="${logoutLink}">Logout</a>)</div><%
    
    } else {
    	final String loginLink = view.getLoginLink();
    	pageContext.setAttribute("loginLink", loginLink);
    
        %>
      <div id="login"><a href="${loginLink}">Login or register</a></div><%
    
    }
    
    %>
    </div>
    <div id="main">
      <h2>Terms of use</h2>
      <h3>General</h3>
      <p>Welcome to our website <a href="/">www.dendrite.net</a>
          (<b>Website</b>).</p>
      <p>You have found one of the most amazing places on the Web where you can
          share your ideas, stories and dreams with millions of other people
          around the world. You are now part of the "Dendrite Community."</p>
      <p>Our Website is owned and managed by <b>Deuterium Labs Limited</b>
          (<b>we</b>, <b>us</b> or <b>our</b>). We have a common set of
          community rules or terms that all our community members and users of
          this Website must adhere to at all times. These rules are set out in
          these Terms of Use.</p>
      <p>These terms apply to your access to, and use of, this Website. If you
          continue to use this Website you are agreeing to comply with and be
          bound by these terms.</p>
      <p>These terms may be amended by us without notice from time to time.
          Amendments will be effective at the time the amended terms are posted
          on this Website. You are responsible for ensuring you are aware of the
          terms, so please check regularly for any updates.</p>
      <h3>Your use of this Website</h3>
      <p>You agree not to use this Website to:</p>
      <ul>
        <li>upload, post or otherwise transmit or disseminate any photos,
            software, material, comment or information that is unlawful,
            tortious, obscene, vulgar, defamatory, pornographic, indecent, lewd,
            harassing, threatening, hateful, harmful, invasive of privacy or
            publicity rights, abusive, inflammatory, or racially, ethnically or
            otherwise objectionable;</li>
        <li>infringe the copyright or other intellectual property rights, or
            confidential information, of any other person;</li>
        <li>defame, abuse, harass, stalk, threaten or otherwise violate the
            legal rights of others, including any breach of privacy or the
            principles of data protection;</li>
        <li>interfere with the operation of this Website via the use of viruses,
            programs or technology designed to disrupt or damage software or
            hardware, including damaging, disabling, overburdening or impairing
            this Website or interfering with any other party's use and enjoyment
            of this Website;</li>
        <li>employ a robot, spider or other process or device to harvest e-mail
            addresses or other information or to monitor activity on this
            Website;</li>
        <li>obtain or attempt to obtain any materials or information through any
            means not intentionally made available or provided for through this
            Website;</li>
        <li>transmit chain mail, junk mail, or spam any other users;</li>
        <li>impersonate another person or entity or to forge any e-mail
            communication or message; or</li>
        <li>use this Website for commercial venture, advertisement, promotion,
            or gain of any kind (whether or not for money or other reward).</li>
      </ul>
      <h3>Content on this Website</h3>
      <p>You may view and create any Content on this Website. <b>"Content"</b>
          means any text, information or other material which is capable of
          being uploaded onto this Website. You may add your Content to any
          other users' contributions of Content.</p>
      <p>You own all intellectual property in your own original Content you
          contribute. You must not upload or contribute any Content not
          originally created by you, or any Content which is not properly
          licensed to you by someone else for uploading or contributing.</p>
      <p>All Content you upload or contribute is licensed to us and all other
          users and viewers of this Website, to use and enjoy under these Terms
          of Use. You agree that this licence cannot be cancelled or revoked,
          does not expire, is freely granted and has world-wide application.</p>
      <h3>No warranties for this Website</h3>
      <p>You agree that your use of and reliance on this Website is entirely at
          your own risk.</p>
      <p>We do not make any representation or warranty that any Content you
          contribute to this Website will always be available, or will not be
          corrupted, lost or damaged.</p>
      <p>We do not guarantee that this Website will be accessible, nor will have
          any specific up-time, availability or latency characteristics. The
          Website may be down from time to time for maintenance.</p>
      <p>We have no responsibility or liability for any Content on this Website.
          Any person objecting to or having any issue or claim in relation to
          any Content should immediately contact us at
          <a href="/contact.jsp">www.dendrite.net/contact.jsp</a></p>
      <p>You agree to use this Website and its Content purely "as is".</p>
      <h3>Links to and from this Website</h3>
      <p>From time to time this Website may include links to other websites.
          Those websites are not under our control. The links are provided for
          your convenience only. We do not endorse, and are not responsible for
          the content, validity, accuracy or your use of, those websites. You
          should check the terms and conditions of use of those websites before
          you use them.</p>
      <h3>Privacy</h3>
      <p>We may collect and retain personal information provided by you through
          your use of this Website. Personal information may also be collected
          through the use of cookies to record your visit. All information
          collected will be held by us in accordance with our
          <a href="/privacy.jsp">Privacy Policy</a>.</p>
      <h3>Intellectual property of the Website</h3>
      <p>We are the owners, or are licensed users, of all intellectual property
          rights used on or in connection with this Website, including (but not
          limited to) the copyright material, patents and trademarks used to
          operate this Website – but excluding all Content contributed to or
          uploaded by users of this Website.</p>
      <p>No licence is granted to any person in respect of the intellectual
          property rights used for this Website.</p>
      <p>You agree and acknowledge that you will not reproduce, store,
          distribute, display, or publish works from this Website or any or its
          Content without our prior written permission, and also the permission
          of any contributing user (in respect of any Content).</p>
      <p>Unless otherwise stated, you are permitted to access, view, copy, print
          (in limited quantities) or temporarily store textual material and
          Content made available by us on this Website for your personal use
          only. Any copyright notice in respect of that information or Content
          must be retained on the copy.</p>
      <h3>Exclusion of our liability</h3>
      <p>You acknowledge and agree that:</p>
      <ul>
        <li>to the maximum extent permitted at law, any and all liability of us
            and our directors, officers and employees to you under or in
            connection with these terms, this Website, all Content or your use
            of this Website is excluded; and</li>
        <li>the exclusion in the paragraph above applies regardless of whether
            liability arises in contract, tort (including negligence), equity or
            by statute or other legislation, and whether such liability is for
            direct, indirect, consequential or punitive losses or damages, or
            loss of profit, income, data, business opportunity or anticipated
            savings.</li>
      </ul>
      <p>All warranties, whether express or implied, as to the accuracy,
          currency, completeness, fitness for purpose or usefulness of the
          information contained in this Website or its Content are excluded to
          the extent permitted by law.</p>
      <p>It is up to you to ensure you are protected against viruses, worms,
          Trojan horses, spyware, malware or other items of a destructive or
          harmful nature.</p>
      <h3>Your indemnity</h3>
      <p>You agree to indemnify, and keep indemnified, us and our directors,
          officers and employees against any and all actions, claims,
          proceedings, losses, liabilities, damages, costs and expenses
          (including legal costs) suffered or incurred by any of those persons
          arising out of or in connection with:</p>
      <ul>
        <li>your use of this Website; and</li>
        <li>any breach by you of these terms.</li>
      </ul>
      <h3>Termination of service</h3>
      <p>In the event that we are no longer able or willing to continue with
          this Website, then we will post a notice on the Website giving you as
          much notice as we can of the Website ceasing operations.</p>
      <p>We will not be responsible for any loss or destruction of any Content,
          and will not be able to return any of your Content to you.</p>
      <h3>Severability</h3>
      <p>If any provision of these terms and conditions becomes or is held
          invalid, unenforceable or illegal (whether partly or in full) for any
          reason, that provision (or part of that provision) will be severed
          from the remaining terms and conditions, which will continue in full
          force and effect.</p>
      <h3>Governing law</h3>
      <p>These terms, your use of this Website, any information contained on it
          including all Content, and any dispute arising out of such use of this
          Website are subject to the laws of New Zealand. You submit to the
          non-exclusive jurisdiction of the courts of New Zealand in relation to
          these terms, your use of this Website and any dispute arising out of
          such use of this Website.</p>
      <h3>Latest update</h3>
      <p>These Terms were last updated 20 October 2013.</p>
    </div>
    </div>
    <div id="footerMenu">
      <span class="footer"><a href="/about.jsp">About</a></span>
      <span class="footer"><a href="/terms.jsp">Terms</a></span>
      <span class="footer"><a href="/privacy.jsp">Privacy</a></span>
      <span class="footer"><a href="/contact.jsp">Contact</a></span>
    </div>
  </body>
</html>