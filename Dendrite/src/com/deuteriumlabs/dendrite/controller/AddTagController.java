package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AddTagController {

	private PageId pgId;
	private String tag;

	public void addTag() {
		final StoryPage pg = this.getStoryPg();
		final String tag = this.getTag();
		final boolean isTagAdded = pg.addTag(tag);
		if (isTagAdded) {
			pg.update();
			final boolean isPgAuthorCurrentUser = isPgAuthorCurrentUser();
			if (isPgAuthorCurrentUser == false) {
				this.notifyAuthor();
			}
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
		final String myUserId = User.getMyUser().getId();
		final String authorId = this.getAuthorId();
		return myUserId.equals(authorId);
	}

	private void notifyAuthor() {
		// TODO
	}

	public void setPgId(final PageId pgId) {
		this.pgId = pgId;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}
}
