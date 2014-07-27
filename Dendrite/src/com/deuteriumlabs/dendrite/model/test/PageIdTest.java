package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.deuteriumlabs.dendrite.model.PageId;

public class PageIdTest {
    
    @Test
    public final void testDefaultConstructor() {
        final PageId id = new PageId();

        final int expectedNum = 0;
        final int actualNum = id.getNumber();
        final String numMsg = "The default number should be 0.";
        assertEquals(numMsg, expectedNum, actualNum);

        final String expectedVersion = null;
        final String actualVersion = id.getVersion();
        final String versionMsg = "The default version should be null.";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        try {
            id.incrementVersion();
            final String incrementMsg;
            incrementMsg = "The default page ID should not be incrementable.";
            fail(incrementMsg);
        } catch (IllegalStateException e) {
            // The id version is null, so it cannot be incremented.
        }

        final boolean expectedValidity = false;
        final boolean actualValidity = id.isValid();
        final String validityMsg = "The default page ID should be invalid.";
        assertEquals(validityMsg, expectedValidity, actualValidity);

        final String expectedString = "0";
        final String actualString = id.toString();
        final String stringMsg = "The default page ID string should be \"0\".";
        assertEquals(stringMsg, expectedString, actualString);
    }

    @Test
    public final void testConstructorFromString() {
        final String nullStr = null;
        PageId id = new PageId(nullStr);
        int expectedNum = 0;
        int actualNum = id.getNumber();
        String numMsg = "The null string should produce ID number 0.";
        assertEquals(numMsg, expectedNum, actualNum);

        String expectedVersion = null;
        String actualVersion = id.getVersion();
        String versionMsg = "The null string should produce null ID version.";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        final String emptyStr = "";
        id = new PageId(emptyStr);
        expectedNum = 0;
        actualNum = id.getNumber();
        numMsg = "The empty string should produce ID number 0.";
        assertEquals(numMsg, expectedNum, actualNum);
    }

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
