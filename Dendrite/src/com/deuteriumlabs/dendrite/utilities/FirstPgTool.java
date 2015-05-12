/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.utilities;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FirstPgTool extends HttpServlet {

	private static final long serialVersionUID = 3328564924638369730L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println(
				"Determine whether each page is the first "
						+ "page in its story.");
		resp.getWriter().println();

		final Query query = new Query("StoryPage");
		final DatastoreService store;
		store = DatastoreServiceFactory.getDatastoreService();
		final PreparedQuery preparedQuery = store.prepare(query);
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final List<Entity> entities = preparedQuery.asList(fetchOptions);
		for (final Entity e : entities) {
			final StoryPage pg = new StoryPage(e);
			pg.determineWhetherFirstPg();
			resp.getWriter().println("ID: " + pg.getId());
			resp.getWriter().println("isFirstPg: " + pg.isFirstPg());
			resp.getWriter().println();
			pg.update();
		}
	}
}
