package com.deuteriumlabs.dendrite.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class MigrationServlet extends HttpServlet {

	private static final long serialVersionUID = -2322493860860753219L;
	private HttpServletResponse response;
	
	private void print(final String message) {
		try {
			response.getWriter().println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.response = resp;
		resp.setContentType("text/plain");
		
		final DatastoreService datastore;
		datastore = DatastoreServiceFactory.getDatastoreService();
		final Query query = new Query("StoryPage");
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final PreparedQuery preparedQuery = datastore.prepare(query);
		final List<Entity> entities = preparedQuery.asList(fetchOptions);
		
		print(entities.size() + " entities found.");
		
		int count = 1;
		
		for (Entity entity : entities) {
			print("Entity " + count);
			
			final String authorUserId = (String) entity.getProperty("authorUserId");
			if (authorUserId != null) {
				final Long idNumber = (Long) entity.getProperty("idNumber");
				if (idNumber != null) {
					final String idVersion = "a";
					final PageId id = new PageId();
					id.setNumber(idNumber.intValue());
					id.setVersion(idVersion);
					
					print("id: " + id);
					
					final StoryPage page = new StoryPage();
					page.setId(id);
					page.read();
					page.setAuthorId(authorUserId);
					
					print("authorId: " + authorUserId);
					
					page.update();
				}
			}
			
			count++;
			print("");
		}
	}
}
