package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;


public class EditPageController {

	private String authorId;
	private String authorName;
	private String content;
	private PageId id;
	private ArrayList<String> options;
	private int pageNumber;
	
	public EditPageController() {
		this.options = new ArrayList<String>();
	}

	public void addOption(final String option) {
		final List<String> options = this.getOptions();
		final int size = options.size();
		if (size < 5)
			options.add(option);
	}

	public void buildNewPage() {
		this.setNextPageId();
		this.buildStoryPage();
		this.buildStoryOptions();
	}

	private void buildStoryOptions() {
		final List<String> optionTexts = this.getOptions();
		for (int i = 0; i < 5; i++) {
			final String text = optionTexts.get(i);
			if (text == null || text.length() == 0)
				continue;
			final StoryOption option = new StoryOption();
			final PageId source = this.getId();
			option.setSource(source);
			option.setListIndex(i);
			option.setText(text);
			final boolean isInStore = option.isInStore();
			if (isInStore == false)
				option.create();
		}
	}

	private void buildStoryPage() {
		final StoryPage page = new StoryPage();
		final PageId id = this.getId();
		page.setId(id);
		final String text = this.getContent();
		page.setText(text);
		final String authorName = this.getAuthorName();
		page.setAuthorName(authorName);
		final String authorId = this.getAuthorId();
		page.setAuthorId(authorId);
		final PageId beginning = this.getBeginning();
		page.setBeginning(beginning);
		final boolean isInStore = page.isInStore();
		if (isInStore == false)
			page.create();
	}

	private String getAuthorId() {
		return this.authorId;
	}

	private String getAuthorName() {
		return this.authorName;
	}

	private PageId getBeginning() {
		final StoryPage alternative = new StoryPage();
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		id.setVersion("a");
		alternative.setId(id);
		alternative.read();
		return alternative.getBeginning();
	}

	private String getContent() {
		return this.content;
	}

	public PageId getId() {
		return this.id;
	}

	private String getNextVersion() {
		final int pageNumber = this.getPageNumber();
		final int count = StoryPage.countVersions(pageNumber);
		return StoryPage.convertNumberToVersion(count + 1);
	}

	private List<String> getOptions() {
		return this.options;
	}

	private int getPageNumber() {
		return this.pageNumber;
	}

	public void setAuthorId(final String authorId) {
		this.authorId = authorId;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	private void setId(final PageId id) {
		this.id = id;
	}

	private void setNextPageId() {
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		final String version = this.getNextVersion();
		id.setVersion(version);
		this.setId(id);
	}

	public void setPageNumber(final String pageNumber) {
		int pageNumberValue;
		try {
			pageNumberValue = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			pageNumberValue = 0;
		}
		if (pageNumberValue > 0)
			this.pageNumber = pageNumberValue;
		else
			this.pageNumber = 0;
	}
}
