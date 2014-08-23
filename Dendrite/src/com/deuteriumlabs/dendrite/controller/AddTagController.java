package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.AuthorTagNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AddTagController {

	private PageId pgId;
	private String tag;

	public boolean addTag() {
		final StoryPage pg = this.getStoryPg();
		final String tag = this.getTag();
		final boolean isTagAdded = pg.addTag(tag);
		if (isTagAdded) {
			pg.update();
			final boolean isPgAuthorCurrentUser = isPgAuthorCurrentUser();
			if (isPgAuthorCurrentUser == false) {
				this.notifyAuthor();
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

	private PageId getPgId() {
		return this.pgId;
	}

	private StoryPage getStoryPg() {
		final PageId id = this.getPgId();
		final StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.read();
		return pg;
	}

	private String getTag() {
		return this.tag;
	}

	private boolean isPgAuthorCurrentUser() {
		if (User.isMyUserLoggedIn()) {
			final String myUserId = User.getMyUser().getId();
			final String authorId = this.getAuthorId();
			return myUserId.equals(authorId);
		} else {
			return false;
		}
	}

	private void notifyAuthor() {
		final AuthorTagNotification notification = new AuthorTagNotification();
		notification.setPgId(this.getPgId());
		notification.setTaggerId(this.getTaggerId());
		notification.setTaggerName(this.getTaggerName());
		notification.setTag(this.getTag());
		notification.setRecipientId(this.getAuthorId());
		notification.create();
	}

	private String getTaggerName() {
		if (User.isMyUserLoggedIn()) {
			return User.getMyUser().getDefaultPenName();
		} else {
			return null;
		}
	}

	private String getTaggerId() {
		if (User.isMyUserLoggedIn()) {
			return User.getMyUser().getId();
		} else {
			return null;
		}
	}

	public void setPgId(final PageId pgId) {
		this.pgId = pgId;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}
}
