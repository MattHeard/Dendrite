package com.deuteriumlabs.dendrite.view.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.deuteriumlabs.dendrite.view.ContentsView;

public class ContentsViewTest {
	
	@Test
	public final void testGetWebPageTitle() {
		final String message = "The web page title is incorrect.";
		final String expected = "Dendrite";
		final ContentsView view = new ContentsView();
		final String actual = view.getWebPageTitle();
		assertEquals(message, expected, actual);
	}
}