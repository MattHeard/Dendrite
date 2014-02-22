package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitNewController extends SubmitController {

	private static final int MAX_TITLE_LEN = 100;
	private String title;

	public void buildNewStory() {
		this.buildNewPage();
		this.buildStoryBeginning();
	}

	private void buildStoryBeginning() {
		final StoryBeginning beginning = new StoryBeginning();
		final PageId id = this.getId();
		final int pageNumber = id.getNumber();
		beginning.setPageNumber(pageNumber);
		final String title = this.getTitle();
		beginning.setTitle(title);
		final boolean isInStore = beginning.isInStore();
		if (isInStore == false)
			beginning.create();
	}

	@Override
	void buildStoryPage() {
		final StoryPage page = new StoryPage();
		final PageId id = this.getId();
		page.setId(id);
		final String text = this.getContent();
		page.setText(text);
		final String authorName = this.getAuthorName();
		page.setAuthorName(authorName);
		final String authorId = this.getAuthorId();
		page.setAuthorId(authorId);
		final boolean isInStore = page.isInStore();
		if (isInStore == false)
			page.create();
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
	}

}
