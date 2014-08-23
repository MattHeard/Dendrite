package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import com.deuteriumlabs.dendrite.model.FolloweeNewNotification;
import com.deuteriumlabs.dendrite.model.FollowerWriteNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.PgChildNotification;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

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
        
        if (page.isInStore() == false) {
            page.create();
        }
        
        if (this.isAuthorNotificationNeeded() == true) {
            this.notifyAboutExtension();
        }
        
        this.notifyFollowers();
    }

    private void notifyFollowers() {
    	final User myUser = User.getMyUser();
    	List<String> followerIds = myUser.getFollowers();
    	for (final String followerId : followerIds) {
    		final FollowerWriteNotification notification;
    		notification = new FollowerWriteNotification();
    		notification.setPgId(this.getId());
    		notification.setAuthorId(this.getAuthorId());
    		notification.setAuthorName(this.getAuthorName());
    		notification.setRecipientId(followerId);
    		notification.create();
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
    private boolean isAuthorNotificationNeeded() {
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
