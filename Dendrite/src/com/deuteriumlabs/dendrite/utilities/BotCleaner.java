/* © 2013-2015 Deuterium Labs Limited */
/**
 * 
 */
package com.deuteriumlabs.dendrite.utilities;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * 
 */
public class BotCleaner extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8203238739960697515L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("BOT CLEANER TOOL");
        resp.getWriter().println();
        final String numParam = req.getParameter("num");
        int pgNum = 0;
        try {
        	pgNum = Integer.parseInt(numParam);
        } catch (NumberFormatException e) {
        	resp.getWriter().println("ERROR: Could not get valid page number");
        	return;
        }
        final PageId id = new PageId();
        id.setNumber(pgNum);
        List<StoryPage> pgs = StoryPage.getAllVersions(id);
        int count = 0;
        for (StoryPage pg : pgs) {
        	pg.delete();
        	count++;
        }
        resp.getWriter().println("Deleted " + count + " versions.");
        
    }
}
