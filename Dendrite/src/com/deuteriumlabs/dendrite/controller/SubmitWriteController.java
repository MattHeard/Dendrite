package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.PgChildNotification;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitWriteController extends SubmitController {
    private PageId from;
    private int linkIndex;

    @Override
	public void buildNewPage() {
		super.buildNewPage();
        this.recalculateStorySize();
	}

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
        final boolean isNotificationNeeded = this.isNotificationNeeded();
        if (isNotificationNeeded == true) {
            this.notifyAboutExtension();
        }
    }

    /**
     * 
     */
    private void notifyAboutExtension() {
        final PgChildNotification notification = new PgChildNotification();
        final PageId childPgId = this.getId();
        notification.setPgId(childPgId);
        final String childAuthorId = this.getAuthorId();
        notification.setChildAuthorId(childAuthorId);
        final StoryPage parentPg = this.getParent();
        final String parentAuthorId = parentPg.getAuthorId();
        notification.setRecipientId(parentAuthorId);
        notification.create();
    }

    /**
     * @return
     */
    private boolean isNotificationNeeded() {
        final boolean isAuthorNotifiable = this.isAuthorNotifiable();
        final boolean isAuthorOfParentPage = this.isAuthorOfParentPage();
        return (isAuthorNotifiable == true && isAuthorOfParentPage == false);
    }

    /**
     * @return
     */
    private boolean isAuthorOfParentPage() {
        final String childAuthorId = this.getAuthorId();
        final StoryPage parentPg = this.getParent();
        final String parentAuthorId = parentPg.getAuthorId();
        return (parentAuthorId != null && parentAuthorId.equals(childAuthorId));
    }

    /**
     * @return
     */
    private boolean isAuthorNotifiable() {
        final StoryPage parentPg = this.getParent();
        final String parentAuthorId = parentPg.getAuthorId();
        return (parentAuthorId != null);
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
