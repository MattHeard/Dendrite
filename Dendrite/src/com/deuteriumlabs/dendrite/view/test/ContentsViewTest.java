package com.deuteriumlabs.dendrite.view.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.deuteriumlabs.dendrite.view.ContentsView;

public class ContentsViewTest {

	@Test
	public final void testIsUserLoggedIn() {
		// I currently do not know how to test the Google App Engine user with a
		// unit test.
	}
	
	@Test
	public final void testGetLoginLink() {
		// I currently do not know how to test the Google App Engine user
		// service with a unit test.
	}
	
	@Test
	public final void testGetWebPageTitle() {
		final String message = "The web page title is incorrect.";
		final String expected = "Dendrite";
		final ContentsView view = new ContentsView();
		final String actual = view.getWebPageTitle();
		assertEquals(message, expected, actual);
	}
}