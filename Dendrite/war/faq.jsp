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
%><%@ page import="com.deuteriumlabs.dendrite.view.FaqView"
%><%

final FaqView view = new FaqView();
view.setPageContext(pageContext);
view.setRequest(request);
view.initialise();

%><%@include file="top_simplified_theming.jspf"

%>
        <ul class="tabs">
          <li><a href="about">About</a></li>
          <li><a href="contributors">Contributors</a></li>
          <li class="selected">FAQ</li>
          <li><a href="patches">Patches</a></li>
          <li id="more"><a href="about_more">⋮</a></li>
        </ul>
        <div class="clear"></div>
        <h3>How Dendrite works</h3>
        <h4>How does the <i>Rewrite</i> function work?</h4>
        <p>The rewrite function allows you to completely rewrite the text of the
          page. The text from the original version of the page will be preserved 
          as one version of that page, while your rewritten page will be another
          version of the page. Both versions are able to be accessed and are
          served to the user based on how many additional pages branch off that 
          story.</p>
        <p>For example, if your version of the page has story pages
          relating to three options filled out, whereas the original version of 
          the page has a story page relating to only one option filled out, your
          page will be more likely to appear. This is to encourage you, as a
          user, to continue writing on your own story.You will be credited as 
          the only author of any rewritten page.</p>
        <h4>What happens to the old page and its links if I rewrite the page? 
        </h4>
        <p>The old page and links are preserved as one version of that page, 
          while your rewritten page will be another version of the page. For 
          example, if you rewrote page 250, the original version of page 250 
          would become page 250a, while your new version will become page 250b. 
          When a user gets to page 250, they will either be served page 250a or 
          page 250b.</p>
        <h4>I accidentally made a typo in my story. How can I fix this?</h4>
        <p>Currently, the only way of fixing this is to completely rewrite the 
          page. We plan to add in a function where authors can edit a page, 
          rather than completely rewriting it. If multiple users edit the same 
          page, they will all be credited as authors of that page.</p>
        <p>If a user rewrites a page completely, they will be credited as the 
          only author of that page until another user edits it (then they will 
          become co-authors).</p>
        <h4>How can I make my story appear on the first page of the table of 
          contents?</h4>
        <p>The ranking of stories in the <a href="/">table of contents</a> is
          based on two factors: first, the length of the story tree; and second,
          the number of "Loves" your story has. If you want your story to go
          further up in the ranking, try adding more pages to your story, or
          writing things that everyone will "Love"!</p>
        <h4>What are tags for?</h4>
        <p>Tags are for displaying the genre of a particular page. Dendrite has 
          a set list of genre tags that you can choose from - in future, we hope 
          to allow user-generated tags so that keywords can be included as well 
          as genres.</p>
        <h4>How can I search for a particular genre?</h4>
        <p>Use the search bar on <a href="/">the Dendrite homepage</a> to search
          for a particular genre, or browse through the table of contents page
          until you find a story with the genre you are looking for.</p>
        <h4>Someone tagged my page with a tag that I disagree with. What can I 
          do?</h4>
        <p>You can click on the tag to remove it if you disagree - keep in mind 
          that someone else could add it back later! We plan to develop a system 
          where if tags are disputed, a certain number of users will need to 
          select a tag (or click to remove a tag) before that change is applied.
        </p>
        
        <h3>Your author name</h3>
        <h4>If I change my author name on a story, will it still be linked to my
          account?</h4>
        <p>Yes. If you change your author name on a story, it will be credited 
          to your account under that name. For example, if your regular user 
          name is "Hector IV", and you write a story as "Jonas XI", your user 
          page will credit you with that story by adding in brackets after the 
          story title "credited as Jonas XI".</p>

        <h3>User preferences</h3>
        <h4>I changed some of the format bar settings on the <i>Read</i> page,
          and then the settings all disappeared when I clicked on a new page.
          How can I make these changes permanent?</h4>
        <p>If you want to permanently save preferences, create a user account 
          and edit your <a href="/preferences">preferences</a> from your
          profile. This will save these  preferences across the entire Dendrite
          website.</p>
        <h4>I want my child to be able to use Dendrite, but I am worried that 
          some pages will not be appropriate. What can I do?</h4>
        <p>At this point, we have no moderation tools or ways to prevent access 
          to pages that are not child-friendly. We take no responsibility for 
          your child's access to Dendrite, and you should monitor their usage 
          yourself.</p>
        <p>In future, we hope to implement parent/child accounts so that you can 
          impose parental controls on your child's account.</p>
        <p>As part of this, in future we also hope to implement genre and filter
          tags. Filters will allow users to only be served pages matching or 
          not-matching specified tags. Possible filters will include:
        <ul>
          <li><b>never</b>: Pages matching this filter will never be chosen
            (e.g. A tag called <i>profanity</i> could be applied to any given
            page, and you will be able to set your user preferences to filter by
            <i>never: profanity</i>).</li>
          <li><b>always</b>: Only pages matching this filter will ever be
            displayed. This would be useful if you want to restrict pages to a
            particular language such as <i>always:lang:en</i> to display only
            English pages.</li>
          <li><b>prefer</b>: This will significantly increase the likelihood
            that a page with the tag is included but will not prevent pages not 
            matching to be displayed too.</li>
        </ul>
        <p>The filter system will allow you to moderate your child's usage of 
          Dendrite.</p>
        <h4>Why can't I upload my own profile picture?</h4>
        <p>We currently only allow default images because Dendrite is not 
          designed to handle user-uploaded images. Once we investigate the 
          security issues related to user uploads, we will determine whether we 
          can allow users to upload their own profile pictures. In the future, 
          we hope to link user profile pictures with users' Facebook or Google 
          accounts.</p>
          
        <h3>Visual design</h3>
        <h4>Why can't I use images in my story?</h4>
        <p>Images are not allowed on Dendrite, as text-only stories are simpler 
          to collaborate on and faster to load. We would like to keep Dendrite 
          as a text-based website rather than an image board. Dendrite is also 
          not designed to handle user-uploaded images for security reasons.</p>
        <h4>I don't like any of the available themes. Can I make my own?</h4>
        <p>At this stage, only four themes are available: Light, Dark, Lovely, 
          and Sepia. In the future we hope to allow users to make user-created 
          themes that can be private or available for use by other Dendrite 
          users.</p>
          
        <h3>Features and development</h3>
        <h4>What new features are going to be added?</h4>
        <p>We currently plan to add the following features:
        <ul>
          <li>User-generated genre tags and keyword tags</li>
          <li>System for mediating on disputed genre tags/keywords</li>
          <li>Notifications by email</li>
          <li>Edit function for story pages, rather than just
            <i>Rewrite</i></li>
          <li>Allowing multiple authors and author history/attribution</li>
          <li>Allowing users to create custom themes that are public for all
            Dendrite users</li>
        </ul>
        <h4>I want to help develop Dendrite. What can I do, and who can I 
          contact?</h4>
        <p>That's great! You can help develop Dendrite by contributing more 
          stories, sharing it with your friends, or contacting us and letting us 
          know of any features or changes that you would like to see. If you 
          want to help with web design or discuss any software development 
          changes, please send a submission through <a href="/contact">the
          contact form</a>.</p>
<%@include file="bottom.jspf" %>