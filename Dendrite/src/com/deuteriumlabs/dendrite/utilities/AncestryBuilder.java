/* © 2013-2015 Deuterium Labs Limited */
/**
 * 
 */
package com.deuteriumlabs.dendrite.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * 
 */
public class AncestryBuilder extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8683773773918414389L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("ANCESTRY BUILDER TOOL");
        resp.getWriter().println();

        resp.getWriter().println("1. Get all StoryBeginning objects.");
        final List<StoryBeginning> beginnings;
        beginnings = StoryBeginning.getBeginnings(0, 1000);
        resp.getWriter().println("Beginnings: " + beginnings);
        resp.getWriter().println();

        resp.getWriter().println("2. For each StoryBeginning object:");

        for (final StoryBeginning beginning : beginnings) {
            resp.getWriter().println("3. Get target page number.");
            final int target = beginning.getPageNumber();
            resp.getWriter().println("target: " + target);
            resp.getWriter().println();

            resp.getWriter().println("4. Get all pages with that number.");
            final int numVersions = StoryPage.countVersions(target);
            List<StoryPage> pages = new ArrayList<StoryPage>();
            for (int i = 0; i < numVersions; i++) {
                final String version = StoryPage.convertNumberToVersion(i + 1);
                final StoryPage page = new StoryPage();
                page.setId(new PageId(target + version));
                page.read();
                pages.add(page);
            }
            resp.getWriter().println("pages: " + pages);
            resp.getWriter().println();

            resp.getWriter().println("5. For each StoryPage object:");

            for (final StoryPage page : pages) {
                resp.getWriter().println("6. Look at the ancestry.");
                List<PageId> ancestry = page.getAncestry();
                resp.getWriter().println("ancestry: " + ancestry);
                resp.getWriter().println();

                resp.getWriter().println("7. Set correct ancestry.");
                resp.getWriter().println();
                page.generateAncestry();
                page.update();

                resp.getWriter().println("8. Check ancestry.");
                ancestry = page.getAncestry();
                resp.getWriter().println("ancestry: " + ancestry);
                resp.getWriter().println();

                resp.getWriter().println("9. Recursively fix children:");
                resp.getWriter().println();
                this.fixChildrenAncestry(page, resp);
            }
        }
    }

    /**
     * @param page
     * @param resp 
     * @throws IOException 
     */
    private void fixChildrenAncestry(final StoryPage page,
            final HttpServletResponse resp) throws IOException {
        resp.getWriter().println("10. Find all options from this page.");
        final PageId source = page.getId();
        final int numOptions = StoryOption.countOptions(source);
        final List<StoryOption> options = new ArrayList<StoryOption>();
        for (int i = 0; i < numOptions; i++) {
            final StoryOption option = new StoryOption();
            option.setSource(source);
            option.setListIndex(i);
            option.read();
            options.add(option);
        }
        resp.getWriter().println("options: " + options);
        resp.getWriter().println();

        resp.getWriter().println("11. For each StoryOption:");

        for (final StoryOption option : options) {
            resp.getWriter().println("12. Get the target page num.");
            resp.getWriter().println();

            final int target = option.getTarget();
            resp.getWriter().println("13. Get all pages with that number.");
            final int numVersions = StoryPage.countVersions(target);
            List<StoryPage> pages = new ArrayList<StoryPage>();
            for (int i = 0; i < numVersions; i++) {
                final String version = StoryPage.convertNumberToVersion(i + 1);
                final StoryPage child = new StoryPage();
                child.setId(new PageId(target + version));
                child.read();
                pages.add(child);
            }
            resp.getWriter().println("pages: " + pages);
            resp.getWriter().println();

            resp.getWriter().println("14. For each StoryPage object:");

            for (final StoryPage child : pages) {
                resp.getWriter().println("15. Look at the parent.");
                final StoryPage parent = child.getParent();
                resp.getWriter().println("parent: " + parent);
                resp.getWriter().println();

                resp.getWriter().println("16. Set correct parent.");
                resp.getWriter().println("parent: " + page);
                child.setParent(page);
                resp.getWriter().println("child.getParent(): " +
                        child.getParent());
                resp.getWriter().println();

                resp.getWriter().println("17. Look at the ancestry.");
                List<PageId> ancestry = child.getAncestry();
                resp.getWriter().println("ancestry: " + ancestry);
                resp.getWriter().println();

                resp.getWriter().println("18. Set correct ancestry.");
                resp.getWriter().println();
                child.generateAncestry();
                child.update();
                child.read();

                resp.getWriter().println("19. Check ancestry.");
                ancestry = child.getAncestry();
                resp.getWriter().println("ancestry: " + ancestry);
                resp.getWriter().println();

                resp.getWriter().println("20. Recursively fix children:");
                resp.getWriter().println();
                this.fixChildrenAncestry(child, resp);
            }
        }
    }
}
