/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class RemoveTagController {
    private PageId pgId;
    private String tag;

    public void removeTag() {
        final StoryPage pg = getStoryPg();
        final boolean isTagRemoved = pg.removeTag(tag);
        if (isTagRemoved) {
            pg.update();
        }
    }

    public void setPgId(final PageId pgId) {
        this.pgId = pgId;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    private StoryPage getStoryPg() {
        final StoryPage pg = new StoryPage();
        pg.setId(pgId);
        pg.read();
        return pg;
    }
}
