/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.AuthorTagNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AddTagController {

	private PageId pgId;
	private String tag;

	public boolean addTag(final User myUser) {
		final StoryPage pg = getStoryPg();
		final String tag = getTag();
		final boolean isTagAdded = pg.addTag(tag);
		if (isTagAdded) {
			pg.update();
			final boolean isPgAuthorCurrentUser = isPgAuthorCurrentUser();
			if (isPgAuthorCurrentUser == false) {
				notifyAuthor(myUser);
			}
			return true;
		} else {
			return false;
		}
	}

	public PageId getPgId() {
		return pgId;
	}

	public String getTag() {
		return tag;
	}

	public void setPgId(final PageId pgId) {
		this.pgId = pgId;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}

	private String getAuthorId() {
		final StoryPage pg = getStoryPg();
		return pg.getAuthorId();
	}

	private StoryPage getStoryPg() {
		final PageId id = getPgId();
		final StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.read();
		return pg;
	}

	private String getTaggerId() {
	    return User.getMyUserId();
	}

	private String getTaggerName(final User myUser) {
		if (User.isMyUserLoggedIn()) {
			return myUser.getDefaultPenName();
		} else {
			return null;
		}
	}

	private boolean isPgAuthorCurrentUser() {
		if (User.isMyUserLoggedIn()) {
			final String myUserId = User.getMyUserId();
			final String authorId = getAuthorId();
			return myUserId.equals(authorId);
		} else {
			return false;
		}
	}

	private void notifyAuthor(final User myUser) {
		final AuthorTagNotification notification = new AuthorTagNotification();
		notification.setPgId(getPgId());
		notification.setTaggerId(getTaggerId());
		notification.setTaggerName(getTaggerName(myUser));
		notification.setTag(getTag());
		notification.setRecipientId(getAuthorId());
		notification.create();
	}
}
