package com.deuteriumlabs.dendrite.controller;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

/**
 * @author Matt Heard
 * Modify all StoryPage entities so that `key` == `pageId.toString()`.
 *
 */
public class KeySetter extends DendriteServlet {
    private static final long serialVersionUID = 6925232458038636521L;
    
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) {
        // PSEUDO
        // Get a cursor for batches of ten StoryPage entities with no filter
        // For each page of the cursor:
        //     For each entity in the page:
        //         Start a new transaction
        //         Read the entity
        //         Print the key and page ID of the entity
        //         Create a new entity with the page ID as the key
        //         Copy the properties of the old entity to the new entity
        //         Delete the old entity
        //         Save the new entity
        //         Close the transaction
        response.setContentType("text/plain");
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    final int limit = 10;
        Cursor startCursor;
        FetchOptions fetchOptions;
        fetchOptions = FetchOptions.Builder.withLimit(limit);
        final Query query = new Query("StoryPage").addSort(Entity.KEY_RESERVED_PROPERTY);
        final PreparedQuery preparedQuery = datastore.prepare(query);
        final TransactionOptions options = TransactionOptions.Builder.withXG(true);
        QueryResultList<Entity> results = preparedQuery.asQueryResultList(fetchOptions);
        int count = 0;
        while (results.size() > 0 && count < 1000) {
            final Transaction transaction = datastore.beginTransaction(options);
            try {
                for (final Entity oldEntity : results) {
                    try {
                        count++;
                        response.getWriter().println("#" + count);
                        response.getWriter().println("old: " + oldEntity);
                        if (oldEntity.hasProperty("idNumber") &&
                                oldEntity.hasProperty("idVersion")) {
                            String id = oldEntity.getProperty("idNumber").toString();
                            id += oldEntity.getProperty("idVersion").toString();
                            Entity newEntity = new Entity("StoryPage", id);
                            newEntity.setPropertiesFrom(oldEntity);
                            response.getWriter().println("new: " + newEntity);
                            response.getWriter().println();
                            datastore.delete(oldEntity.getKey());
                            datastore.put(newEntity);
                        } else {
                            response.getWriter().println("ERROR: Invalid Page ID");
                        }
                    } catch (IOException e) {
                        // IGNORE IOException
                    }
                }
                transaction.commit();
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            startCursor = results.getCursor();
            fetchOptions = FetchOptions.Builder.withLimit(limit).startCursor(startCursor);
            results = preparedQuery.asQueryResultList(fetchOptions);
        }
	}
}
