/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.FolloweeNewNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitNewController extends SubmitController {

	private static final int MAX_TITLE_LEN = 100;
	private String title;

	public void buildNewStory() {
		this.buildNewPage();
		this.buildStoryBeginning();
		this.recalculateStoryQuality();
		this.notifyFollowers();
	}

	private void notifyFollowers() {
		final User myUser = User.getMyUser();
		if (myUser != null) {
			List<String> followerIds = myUser.getFollowers();
			if (followerIds != null) {
				for (final String followerId : followerIds) {
					final FolloweeNewNotification notification;
					notification = new FolloweeNewNotification();
					notification.setPgId(this.getId());
					notification.setAuthorId(this.getAuthorId());
					notification.setAuthorName(this.getAuthorName());
					notification.setTitle(this.getTitle());
					notification.setRecipientId(followerId);
					notification.create();
				}
			}
		}
	}

	private void buildStoryBeginning() {
		final StoryBeginning beginning = new StoryBeginning();
		final PageId id = this.getId();
		final int pageNumber = id.getNumber();
		beginning.setPageNumber(pageNumber);
		final String title = this.getTitle();
		beginning.setTitle(title);
		final boolean isInStore = beginning.isInStore();
		if (isInStore == false) {
			beginning.create();
		}
	}

	@Override
	void buildStoryPage() {
		final StoryPage page = new StoryPage();
		this.addStoryPageValues(page);
		final boolean isInStore = page.isInStore();
		if (isInStore == false) {
			page.create();
		}
	}

	private String getTitle() {
		return this.title;
	}

	public boolean isTitleBlank() {
		final String title = this.getTitle();
		if (title == null || title.equals(""))
			return true;
		else
			return false;
	}

	public boolean isTitleTooLong() {
		final String title = this.getTitle();
		if (title != null) {
			final int length = title.length();
			return (length > MAX_TITLE_LEN);
		} else
			return false;
	}

	public void setTitle(final String title) {
		this.title = title;
		final HttpSession session = this.getSession();
		session.setAttribute("title", title);
	}

}
