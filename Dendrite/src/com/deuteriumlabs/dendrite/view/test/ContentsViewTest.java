/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view.test;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.controller.SubmitNewController;
import com.deuteriumlabs.dendrite.view.ContentsView;
import com.deuteriumlabs.dendrite.view.ContentsView.Link;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

// HttpSessionContext is deprecated but is necessary for implementing
// DummyHttpSession.
@SuppressWarnings("deprecation")
public class ContentsViewTest {
	
	private class DummyHttpSession implements HttpSession {

		@Override
		public Object getAttribute(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<?> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getCreationTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getLastAccessedTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getMaxInactiveInterval() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getValue(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getValueNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void invalidate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isNew() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void putValue(String arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeAttribute(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeValue(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setAttribute(String arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setMaxInactiveInterval(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@SuppressWarnings("deprecation")
		@Override
		public HttpSessionContext getSessionContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

    private static final String DUMMY_STORY_TITLE = "Dummy story title";
	private static final String DUMMY_STORY_CONTENT = "Dummy story content";
	private static final String DUMMY_STORY_AUTHOR_NAME = 
			"Dummy story author name";
	private static final String DUMMY_STORY_AUTHOR_ID = "1234567890";
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
	public final void testGetEmptyLinks() {
		final ContentsView view = new ContentsView();
		final String msg = "There should be no contents entries.";
		final List<Link> expected = new ArrayList<Link>();
		final List<Link> actual = view.getLinks();
		assertEquals(msg, expected, actual);
	}
    
    @Test
    public final void testGetOneLink() {
    	createSingleDummyStory();
    	
    	final ContentsView view = new ContentsView();
    	final String msg = "There should be one contents entry.";
    	final int expected = 1;
		final List<Link> links = view.getLinks();
		final int actual = links.size();
		assertEquals(msg, expected, actual);
    }

	private void createSingleDummyStory() {
		final SubmitNewController controller = new SubmitNewController();
		final DummyHttpSession session = new DummyHttpSession();
		controller.setSession(session);
		controller.setTitle(DUMMY_STORY_TITLE);
		controller.setContent(DUMMY_STORY_CONTENT);
		controller.setAuthorName(DUMMY_STORY_AUTHOR_NAME);
		controller.setAuthorId(DUMMY_STORY_AUTHOR_ID);
		controller.buildNewStory();
	}
	
	@Test
    public final void testGetWebPageTitle() {
        final String msg = "The web page title is incorrect.";
        final String expected = "Dendrite";
        final ContentsView view = new ContentsView();
        final String actual = view.getWebPageTitle();
        assertEquals(msg, expected, actual);
    }
}