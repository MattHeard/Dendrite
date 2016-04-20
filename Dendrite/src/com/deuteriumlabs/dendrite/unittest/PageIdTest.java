package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.deuteriumlabs.dendrite.model.PageId;

public class PageIdTest {
    @Test
    public void testDefaultConstructor() {
        final PageId pageId = new PageId();
        assertNotNull(pageId);
    }
}
