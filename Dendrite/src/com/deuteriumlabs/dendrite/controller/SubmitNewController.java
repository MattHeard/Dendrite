/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.FolloweeNewNotification;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitNewController extends SubmitController {
	private static final int MAX_TITLE_LEN = 100;
	
	private String title;

	public void buildNewStory(final User myUser) {
		buildNewPage(myUser);
		buildStoryBeginning();
		recalculateStoryQuality();
		notifyFollowers(myUser);
	}

	public boolean isTitleBlank() {
		return (title == null || title.equals(""));
	}

	public boolean isTitleTooLong() {
	    return (title != null && (title.length() > MAX_TITLE_LEN));
	}

	public void setTitle(final String title) {
		this.title = title;
		final HttpSession session = getSession();
		session.setAttribute("title", title);
	}

	private void buildStoryBeginning() {
		final StoryBeginning beginning = new StoryBeginning();
		final int pageNumber = getId().getNumber();
		beginning.setPageNumber(pageNumber);
		beginning.setTitle(title);
		final boolean isInStore = beginning.isInStore();
		if (isInStore == false) {
			beginning.create();
		}
	}

	private void notifyFollowers(final User myUser) {
		if (myUser != null) {
			List<String> followerIds = myUser.getFollowers();
			if (followerIds != null) {
				for (final String followerId : followerIds) {
					final FolloweeNewNotification notification;
					notification = new FolloweeNewNotification();
					notification.setPgId(getId());
					notification.setAuthorId(getAuthorId());
					notification.setAuthorName(getAuthorName());
					notification.setTitle(title);
					notification.setRecipientId(followerId);
					notification.create();
				}
			}
		}
	}

	@Override
	void buildStoryPage(final User myUser) {
		final StoryPage page = new StoryPage();
		addStoryPageValues(page);
		final boolean isInStore = page.isInStore();
		if (isInStore == false) {
			page.create();
		}
	}

}
