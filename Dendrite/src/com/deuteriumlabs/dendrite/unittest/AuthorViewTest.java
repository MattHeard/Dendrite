/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.view.AuthorView;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class AuthorViewTest {

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
    public final void testGetId() {
        final AuthorView view = new AuthorView();
        final String authorId = "1";
        view.setId(authorId);
        final String expected = "1";
        final String actual = view.getId();
        assertEquals(expected, actual);
    }
}
