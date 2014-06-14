package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

class BibliographyView {
	private static final int DEFAULT_AUTHOR_PAGE_NUM = 1;

	private static final int NUM_PAGES_DISPLAYED = 10;
	
	private int authorPageNumber;
	private String id;
	private int numStoryPagesAlreadyDisplayed;
	private List<StoryPage> pages;

	private List<String> titles;

	public int getAuthorPageNumber() {
		return authorPageNumber;
	}

	public int getFirstIndex() {
		final int authorPageNumber = this.getAuthorPageNumber();
		return (authorPageNumber - 1) * NUM_PAGES_DISPLAYED;
	}

	public String getId() {
		return this.id;
	}

	public int getLastIndex() {
		final int firstIndex = this.getFirstIndex();
		return firstIndex + NUM_PAGES_DISPLAYED;
	}

	public int getNumStoryPagesAlreadyDisplayed() {
		return numStoryPagesAlreadyDisplayed;
	}

	public int getNumStoryPagesToDisplay() {
		final List<String> titles = this.getTitles();
		return titles.size();
	}

	public List<StoryPage> getPages() {
		if (pages == null) {
			this.readPages();
		}
		return pages;
	}

	public List<String> getTitles() {
		if (titles == null) {
			this.readTitles();
		}
		return titles;
	}

	public void incrementNumStoryPagesAlreadyDisplayed() {
		final int num = this.getNumStoryPagesAlreadyDisplayed();
		this.setNumStoryPagesAlreadyDisplayed(num + 1);
	}

	public void readPages() {
		final int firstIndex = this.getFirstIndex();
		final int lastIndex = this.getLastIndex();
		final List<StoryPage> pages;
		final String authorId = this.getId();
		pages = StoryPage.getPagesWrittenBy(authorId, firstIndex, lastIndex);
		this.setPages(pages);
	}

	public void readTitles() {
		final List<StoryPage> pages = this.getPages();
		final List<String> titles = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final PageId id = page.getBeginning();
			final int number = id.getNumber();
			final StoryBeginning beginning = new StoryBeginning();
			beginning.setPageNumber(number);
			beginning.read();
			final String title = beginning.getTitle();
			titles.add(title);
		}
		this.setTitles(titles);
	}

	public void setAuthorPageNumber(final int authorPageNumber) {
		this.authorPageNumber = authorPageNumber;
	}

	public void setDefaultAuthorPageNumber() {
		this.setAuthorPageNumber(DEFAULT_AUTHOR_PAGE_NUM);
	}

	public void setId(final String id) {
		this.id = id;
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