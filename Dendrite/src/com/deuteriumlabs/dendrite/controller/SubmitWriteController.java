package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitWriteController extends SubmitController {
    private PageId from;
    private int linkIndex;

    @Override
    void buildStoryPage() {
        final StoryPage page = new StoryPage();
        this.addStoryPageValues(page);
        final PageId beginning = this.getBeginning();
        page.setBeginning(beginning);
        final boolean isInStore = page.isInStore();
        if (isInStore == false) {
            page.create();
        }
    }

    public void connectIncomingOption() {
        final StoryOption incoming = new StoryOption();
        final PageId from = this.getFrom();
        incoming.setSource(from);
        final int linkIndex = this.getLinkIndex();
        incoming.setListIndex(linkIndex);
        incoming.read();
        final PageId targetId = this.getId();
        final int targetPageNumber = targetId.getNumber();
        incoming.setTarget(targetPageNumber);
        incoming.update();
    }

    private PageId getBeginning() {
        final StoryPage parent = new StoryPage();
        final PageId from = this.getFrom();
        parent.setId(from);
        parent.read();
        return parent.getBeginning();
    }

    private PageId getFrom() {
        return this.from;
    }

    private int getLinkIndex() {
        return this.linkIndex;
    }

    public void setFrom(final String from) {
        final PageId id = new PageId(from);
        this.from = id;
    }

    public void setLinkIndex(String linkIndex) {
        int indexValue;
        try {
            indexValue = Integer.parseInt(linkIndex);
        } catch (NumberFormatException e) {
            indexValue = -1;
        }
        this.linkIndex = indexValue;
    }
}
