package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.deuteriumlabs.dendrite.model.PageId;

public class PageIdTest {
    private static final String PAGE_ID_STRING = "1a";

    @Test
    public void testDefaultConstructor() {
        final PageId pageId = new PageId();
        assertNotNull(pageId);
    }

    @Test
    public void testConstructorWithString() {
        final PageId pageId = new PageId(PAGE_ID_STRING);
        assertNotNull(pageId);
    }
}
