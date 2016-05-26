/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

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
	public void buildNewPage(final User myUser) {
		super.buildNewPage(myUser);
		recalculateStoryQuality();
	}

	@Override
	void buildStoryPage(final User myUser) {
		final StoryPage page = new StoryPage();
		addStoryPageValues(page);
		final PageId beginning = getBeginning();
		page.setBeginning(beginning);

		if (page.isInStore() == false) {
			page.create();
		}

		if (isParentNotificationNeeded()) {
			notifyParent();
		}

		notifyFollowers(myUser);
	}

	public void connectIncomingOption() {
		final StoryOption incoming = new StoryOption();
		incoming.setSource(from);
		incoming.setListIndex(linkIndex);
		incoming.read();
		final PageId targetId = getId();
		final int targetPageNumber = targetId.getNumber();
		incoming.setTarget(targetPageNumber);
		incoming.update();
	}

	private PageId getBeginning() {
		final StoryPage parent = new StoryPage();
		parent.setId(from);
		parent.read();
		return parent.getBeginning();
	}

	/**
	 * @return
	 */
	private boolean isAuthorNotifiable() {
		return (getParent().getAuthorId() != null);
	}

	/**
	 * @return
	 */
	private boolean isParentNotificationNeeded() {
		return (isAuthorNotifiable() && !isAuthorOfParentPage());
	}

	/**
	 * @return
	 */
	private boolean isAuthorOfParentPage() {
		final String childAuthorId = getAuthorId();
		final String parentAuthorId = getParent().getAuthorId();
		return (parentAuthorId != null && parentAuthorId.equals(childAuthorId));
	}

	/**
     * 
     */
	private void notifyParent() {
		final PgChildNotification notification = new PgChildNotification();
		final PageId childPgId = getId();
		notification.setPgId(childPgId);
		final String childAuthorId = getAuthorId();
		notification.setChildAuthorId(childAuthorId);
		final String parentAuthorId = getParent().getAuthorId();
		notification.setRecipientId(parentAuthorId);
		notification.create();
	}

	private void notifyFollowers(final User myUser) {
		if (myUser != null) {
			List<String> followerIds = myUser.getFollowers();
			if (followerIds != null) {
				for (final String followerId : followerIds) {
					if (isFollowerNeedingNotification(followerId)) {
						final FollowerWriteNotification notification;
						notification = new FollowerWriteNotification();
						notification.setPgId(getId());
						notification.setAuthorId(getAuthorId());
						notification.setAuthorName(getAuthorName());
						notification.setRecipientId(followerId);
						notification.create();
					}
				}
			}
		}
	}

	private boolean isFollowerNeedingNotification(final String followerId) {
		if (followerId == null) {
			return false;
		}

		String parentAuthorId = getParent().getAuthorId();
		if (parentAuthorId == null) {
			return true;
		} else {
			return followerId.equals(parentAuthorId);
		}
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
