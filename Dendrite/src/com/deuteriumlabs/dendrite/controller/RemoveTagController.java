/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class RemoveTagController {
    private PageId pageId;
    private String tag;

    public void removeTag() {
        final StoryPage page = getStoryPage();
        final boolean isTagRemoved = page.removeTag(tag);
        if (isTagRemoved) {
            page.update();
        }
    }

    public void setPageId(final PageId pageId) {
        this.pageId = pageId;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    private StoryPage getStoryPage() {
        final StoryPage page = new StoryPage();
        page.setId(pageId);
        page.read();
        return page;
    }
}
