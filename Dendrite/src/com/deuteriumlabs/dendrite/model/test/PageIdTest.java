package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;

public class PageIdTest {

	@Test
	public final void testInvalidNumber() {
		final PageId id = new PageId();
		final int invalidNum = -1;
		id.setNumber(invalidNum);
		
		final int expected = 0;
		final int actual = id.getNumber();
		
		final String message = "The invalid number should have returned 0.";
		assertEquals(message, expected, actual);
	}
	
	@Test
	public final void testInvalidVersion() {
		final PageId id = new PageId();
		final String invalidVersion = "f00";
		id.setVersion(invalidVersion);
		
		final String actual = id.getVersion();
		
		final String message = "The valid version returned was not correct.";
		assertNull(message, actual);
	}
	
	@Test
	public final void testValidNumber() {
		final PageId id = new PageId();
		final int validNum = 1;
		id.setNumber(validNum);
		
		final int expected = validNum;
		final int actual = id.getNumber();
		
		final String message = "The valid number returned was not correct.";
		assertEquals(message, expected, actual);
	}
	
	@Test
	public final void testValidVersion() {
		final PageId id = new PageId();
		final String validVersion = "foo";
		id.setVersion(validVersion);
		
		final String expected = "foo";
		final String actual = id.getVersion();
		
		final String message = "The valid version returned was not correct.";
		assertEquals(message, expected, actual);
	}
}
