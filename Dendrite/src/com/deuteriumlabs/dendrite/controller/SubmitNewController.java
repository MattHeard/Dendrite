package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitNewController {

	private static final int MAX_EXPONENT = 30;
	private String authorId;
	private String authorName;
	private String content;
	private PageId id;
	private List<String> options;
	private String title;
	
	public SubmitNewController() {
		this.options = new ArrayList<String>();
	}

	public void addOption(final String option) {
		final List<String> options = this.getOptions();
		final int size = options.size();
		if (size < 5)
			options.add(option);
	}

	public void buildNewStory() {
		this.setNewPageId();
		this.buildStoryBeginning();
		this.buildStoryPage();
		this.buildStoryOptions();
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

	private void buildStoryOptions() {
		final List<String> optionTexts = this.getOptions();
		for (int i = 0; i < 5; i++) {
			final String text = optionTexts.get(i);
			if (text == null || text.length() == 0)
				continue;
			final StoryOption option = new StoryOption();
			final PageId id = this.getId();
			option.setSource(id);
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
		final boolean isInStore = page.isInStore();
		if (isInStore == false)
			page.create();
	}

	private PageId findUnallocatedPageId() {
		final PageId id = new PageId();
		final int number = this.findUnallocatedPageNumber();
		id.setNumber(number);
		final String version = "a";
		id.setVersion(version);
		return id;
	}

	private int findUnallocatedPageNumber() {
		int exponent = 0;
		while (exponent <= MAX_EXPONENT) {
			int randomNumber = generateRandomNumber(exponent);
			final boolean isUnallocated = isUnallocated(randomNumber);
			if (isUnallocated)
				return randomNumber;
			else if (exponent != MAX_EXPONENT)
				exponent++;
		}
		return -1; // Theoretically unreachable.
	}

	private int generateRandomNumber(final int exponent) {
		final int POWER_BASE = 2;
		double upper = Math.pow(POWER_BASE, exponent);
		Random generator = new Random();
		double randomDouble = generator.nextDouble();
		double newNumber = (upper * randomDouble) + 1;
		return (int) newNumber;
	}

	private String getAuthorId() {
		return this.authorId;
	}

	private String getAuthorName() {
		return this.authorName;
	}

	private String getContent() {
		return this.content;
	}

	public PageId getId() {
		return this.id;
	}

	private List<String> getOptions() {
		return this.options;
	}

	private String getTitle() {
		return this.title;
	}

	public boolean isAuthorNameValid() {
		final String authorName = this.getAuthorName();
		final boolean isValid;
		if (authorName == null)
			isValid = false;
		else if (authorName.equals(""))
			isValid = false;
		else
			isValid = true;
		return isValid;
	}

	public boolean isContentValid() {
		final String content = this.getContent();
		final boolean isValid;
		if (content == null)
			isValid = false;
		else if (content.equals(""))
			isValid = false;
		else
			isValid = true;
		return isValid;
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
			return (length > 100);
		} else
			return false;
	}

	private boolean isUnallocated(final int candidate) {
		final PageId id = new PageId();
		id.setNumber(candidate);
		final String version = "a";
		id.setVersion(version);
		final StoryPage page = new StoryPage();
		page.setId(id);
		final boolean isInStore = page.isInStore();
		return (isInStore == false);
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

	private void setNewPageId() {
		final PageId id = this.findUnallocatedPageId();
		this.setPageId(id);
	}

	private void setPageId(final PageId id) {
		this.id = id;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
