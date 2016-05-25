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
		final StoryPage pg = this.getStoryPg();
		final String tag = this.getTag();
		final boolean isTagAdded = pg.addTag(tag);
		if (isTagAdded) {
			pg.update();
			final boolean isPgAuthorCurrentUser = isPgAuthorCurrentUser();
			if (isPgAuthorCurrentUser == false) {
				this.notifyAuthor(myUser);
			}
			return true;
		} else {
			return false;
		}
	}

	private String getAuthorId() {
		final StoryPage pg = this.getStoryPg();
		return pg.getAuthorId();
	}

	public PageId getPgId() {
		return this.pgId;
	}

	private StoryPage getStoryPg() {
		final PageId id = this.getPgId();
		final StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.read();
		return pg;
	}

	public String getTag() {
		return this.tag;
	}

	private boolean isPgAuthorCurrentUser() {
		if (User.isMyUserLoggedIn()) {
			final String myUserId = User.getMyUserId();
			final String authorId = this.getAuthorId();
			return myUserId.equals(authorId);
		} else {
			return false;
		}
	}

	private void notifyAuthor(final User myUser) {
		final AuthorTagNotification notification = new AuthorTagNotification();
		notification.setPgId(this.getPgId());
		notification.setTaggerId(this.getTaggerId());
		notification.setTaggerName(this.getTaggerName(myUser));
		notification.setTag(this.getTag());
		notification.setRecipientId(this.getAuthorId());
		notification.create();
	}

	private String getTaggerName(final User myUser) {
		if (User.isMyUserLoggedIn()) {
			return myUser.getDefaultPenName();
		} else {
			return null;
		}
	}

	private String getTaggerId() {
	    return User.getMyUserId();
	}

	public void setPgId(final PageId pgId) {
		this.pgId = pgId;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}
}
