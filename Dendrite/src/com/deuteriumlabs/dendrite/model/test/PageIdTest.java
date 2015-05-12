/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model.test;

import static org.junit.Assert.assertEquals;
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
        } catch (IllegalStateException expected) {
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

        expectedVersion = null;
        actualVersion = id.getVersion();
        versionMsg = "The empty string should produce null ID version.";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        final String genericNum = "1";
        id = new PageId(genericNum);
        expectedNum = 1;
        actualNum = id.getNumber();
        numMsg = "The string \"1\" should produce ID number 1.";
        assertEquals(numMsg, expectedNum, actualNum);

        expectedVersion = null;
        actualVersion = id.getVersion();
        versionMsg = "The string \"1\" should produce null ID version.";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        final String specificNum = "1a";
        id = new PageId(specificNum);
        expectedNum = 1;
        actualNum = id.getNumber();
        numMsg = "The string \"1a\" should produce ID number 1.";
        assertEquals(numMsg, expectedNum, actualNum);

        expectedVersion = "a";
        actualVersion = id.getVersion();
        versionMsg = "The string \"1a\" should produce ID version \"a\".";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        final String genericNumWithCruft = "1!";
        id = new PageId(genericNumWithCruft);
        expectedNum = 1;
        actualNum = id.getNumber();
        numMsg = "The string \"1!\" should produce ID number 1.";
        assertEquals(numMsg, expectedNum, actualNum);

        expectedVersion = null;
        actualVersion = id.getVersion();
        versionMsg = "The string \"1!\" should produce null ID version.";
        assertEquals(versionMsg, expectedVersion, actualVersion);

        final String specificNumWithCruft = "1a!";
        id = new PageId(specificNumWithCruft);
        expectedNum = 1;
        actualNum = id.getNumber();
        numMsg = "The string \"1a!\" should produce ID number 1.";
        assertEquals(numMsg, expectedNum, actualNum);

        expectedVersion = "a";
        actualVersion = id.getVersion();
        versionMsg = "The string \"1a!\" should produce ID version \"a\".";
        assertEquals(versionMsg, expectedVersion, actualVersion);
    }

    @Test
    public final void testToString() {
        final PageId id = new PageId();
        int num = 1;
        id.setNumber(num);

        String expectedStr = "1";
        String actualStr = id.toString();
        String msg = "The page ID with 1 and null should print '1'.";
        assertEquals(msg, expectedStr, actualStr);

        String version = "a";
        id.setVersion(version);

        expectedStr = "1a";
        actualStr = id.toString();
        msg = "The page ID with 1 and 'a' should print '1a'.";
        assertEquals(msg, expectedStr, actualStr);
    }

    @Test
    public final void testIsValid() {
        final PageId id = new PageId();
        boolean expected = false;
        boolean actual = id.isValid();
        String msg = "The default page ID should be invalid.";
        assertEquals(msg, expected, actual);

        final int num = 1;
        id.setNumber(num);
        expected = false;
        actual = id.isValid();
        msg = "Page ID '1' should be invalid.";
        assertEquals(msg, expected, actual);

        final String version = "a";
        id.setVersion(version);
        expected = true;
        actual = id.isValid();
        msg = "Page ID '1a' should be valid.";
        assertEquals(msg, expected, actual);
    }
}
