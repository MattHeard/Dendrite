/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.model.User;
import com.deuteriumlabs.dendrite.view.BibliographyView;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BibliographyViewTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());
    private final User author = new User();

    @Before
    public void setUp() {
        helper.setUp();
        author.setId("1");
		author.create();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
	public final void testGetLastPageNumber() {
    	final BibliographyView view = new BibliographyView();
		view.setNumStoryPagesAlreadyDisplayed(0);
		view.setPrevTitle(null);
		view.setCurrTitle(null);
		author.read();
		final String authorId = author.getId();
		System.out.println(authorId);
		view.setAuthorId(authorId);
    	final int expected = 1;
    	final int actual = view.getLastPageNumber();
    	assertEquals(expected, actual);
    }
}
