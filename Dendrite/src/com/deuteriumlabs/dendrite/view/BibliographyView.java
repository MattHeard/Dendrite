package com.deuteriumlabs.dendrite.view;

import java.util.List;

import com.deuteriumlabs.dendrite.model.StoryPage;

class BibliographyView {
	private static final int DEFAULT_AUTHOR_PAGE_NUM = 1;
	
	private int authorPageNumber;
	private int numStoryPagesAlreadyDisplayed;
	private List<StoryPage> pages;
	private List<String> titles;

	public int getAuthorPageNumber() {
		return authorPageNumber;
	}

	public int getNumStoryPagesAlreadyDisplayed() {
		return numStoryPagesAlreadyDisplayed;
	}

	public List<StoryPage> getPages() {
		return pages;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setAuthorPageNumber(final int authorPageNumber) {
		this.authorPageNumber = authorPageNumber;
	}

	public void setDefaultAuthorPageNumber() {
		this.setAuthorPageNumber(DEFAULT_AUTHOR_PAGE_NUM);
	}

	public void setNumStoryPagesAlreadyDisplayed(final int num) {
		this.numStoryPagesAlreadyDisplayed = num;
	}

	public void setPages(final List<StoryPage> pages) {
		this.pages = pages;
	}

	public void setTitles(final List<String> titles) {
		this.titles = titles;
	}
}