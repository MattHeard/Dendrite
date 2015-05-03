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

%><%@include file="top_simplified_theming.jspf"

%>
        <div class="about accordion">
        <ul class="tabs">
          <li class="about selected">About</li>
          <li class="contributors">Contributors</li>
          <li class="faq">FAQ</li>
          <li class="patches">Patches</li>
        </ul>
        <div class="clear"></div>
        <section class="about">
          <h2>About</h2>
          <div>
            <p><i>Dendrite</i> is an online, choose-your-own-adventure book that
              you can both read, and write. This allows you, the reader and author, to
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
        <p><i>Dendrite</i> is a creation of <i>Deuterium Labs</i>.</p></div>
          <p>Discuss Dendrite with us at:
            <ul>
              <li><a href="http://www.reddit.com/r/dendritestories">reddit.com/r/DendriteStories</a></li>
              <li><a href="https://twitter.com/DendriteStories">twitter.com/DendriteStories</a></li>
              <li><a href="https://www.facebook.com/dendritestories">facebook.com/DendriteStories</a></li>
            </ul>
          </p>
        </section>
        <section class="contributors">
          <h2>Contributors</h2>
          <div>
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
        <h3>Major contributors</h3>
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
        <ul class="testers">
          <li>Calum Barrett</li>
          <li>Neil Dudley</li>
          <li>Duncan Hamilton</li>
          <li>Sean Hamilton</li>
          <li>John Heard</li>
          <li>Nic Henwood</li>
          <li>Jack Li</li>
          <li>Bayard Randel</li>
          <li>Isaac Randel</li>
          <li>Chris Youngson</li>
        </ul>
        <i>If you would like to contact the developer or either of the major
        contributors, please send a message via the
        <a href="/contact">Contact</a> form.</i></div>
        </section>
        <section class="faq">
          <h2>FAQ</h2>
          <div>
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
        <p>The ranking of stories in the <a href="/contents">table of
          contents</a> is based on two factors: first, the length of the story
          tree; and second, the number of "Loves" your story has. If you want
          your story to go further up in the ranking, try adding more pages to
          your story, or writing things that everyone will "Love"!</p>
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
          contact form</a>.</p></div>
        </section>
        <section class="patches">
          <h2>Patches</h2>
          <div>
        <p>Welcome, you adventurous devil! Anything and everything could (and
          probably will) change at any second. We're moving too fast to know
          what's going on, but once we're more stable, we're going to publish
          descriptions of all of our updates here. Bug fixes, new features,
          minor tweaks.</p>
            <ul>
              <li>1.0.12: Fixed a link in the FAQ.</li>
              <li>1.0.11: Added indications about which options have been written.</li>
              <li>1.0.10: Fixed a bug on the second page of filtered contents.</li>
              <li>1.0.9: Improved contents page numbering.</li>
              <li>1.0.8: Added link to contents page on each read page.</li>
              <li>1.0.7: Added links to social media at <a href="/about">About</a>.</li>
              <li>1.0.6: Added 'Young-Adult' genre tag.</li>
              <li>1.0.5: Fixed another NullPointerException in
                  notifications.</li>
              <li>1.0.4: Fixed NullPointerException in notifications.</li>
              <li>1.0.3: More minor CSS tweaks.</li>
              <li>1.0.2: Minor CSS tweaks.</li>
              <li>1.0.1: Fixed NullPointerException in meta descriptions.</li>
              <li>1.0.0: Lift-off!</li>
            </ul>
          </div>
        </section></div>
<%@include file="bottom.jspf" %>