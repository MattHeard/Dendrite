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
		
		final DatastoreService datastore;
		datastore = DatastoreServiceFactory.getDatastoreService();
		final Query query = new Query("StoryPage");
		final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		final PreparedQuery preparedQuery = datastore.prepare(query);
		final List<Entity> entities = preparedQuery.asList(fetchOptions);
		
		print(entities.size() + " entities found.");
		
		int count = 1;
		
		final List<Long> pageNumbers = new ArrayList<Long>();
		for (Entity entity : entities) {
			print("Entity " + count);
			
			final Long idNumberProperty = (Long) entity.getProperty("idNumber");
			Long pageNumProperty = (Long) entity.getProperty("pageNum");
			
			print("idNumberProperty: " + idNumberProperty);
			print("pageNumProperty: " + pageNumProperty);
			
			if (idNumberProperty == null) {
				print("idNumberProperty == null (old object)");
				
				boolean isValidPageNum = true;
				if (pageNumProperty == null) {
					print("pageNumProperty == null (missing pageNum)");
					
					final String name = entity.getKey().getName();
					try {
						pageNumProperty = Long.parseLong(name);
						print("pageNum extracted from name: " + pageNumProperty);
					} catch (NumberFormatException e) {
						isValidPageNum = false;
						print("pageNum invalid, skip this one");
					}
				}
				if (isValidPageNum == true &&
						pageNumbers.contains(pageNumProperty) == false) {
					final PageId id = new PageId();
					final int idNumber = pageNumProperty.intValue();
					
					print("copy text for page " + idNumber);
					
					id.setNumber(idNumber);
					final String idVersion = "a";
					id.setVersion(idVersion);
					final StoryPage storyPage = new StoryPage();
					storyPage.setId(id);
					storyPage.read();
					final Text text = (Text) entity.getProperty("content");
					
					print("Text to copy: " + text.toString());
					
					storyPage.setText(text);
					storyPage.update();
					pageNumbers.add(pageNumProperty);
					
					print("page numbers completed: " + pageNumbers);
				}
			}
			
			count++;
			print("");
		}
	}
}
