package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public abstract class SubmitController {

	static final int MAX_EXPONENT = 30;
	protected String authorId;
	protected String authorName;
	protected String content;
	protected PageId id;
	protected List<String> options;
	
	public SubmitController() {
		this.options = new ArrayList<String>();
	}

	public void addOption(final String option) {
		final List<String> options = this.getOptions();
		final int size = options.size();
		if (size < 5)
			options.add(option);
	}

	public void buildNewPage() {
		this.setNewPageId();
		this.buildStoryPage();
		this.buildStoryOptions();
	}

	protected void buildStoryOptions() {
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
	
	abstract void buildStoryPage();
	
	protected String getAuthorId() {
		return this.authorId;
	}

	protected String getAuthorName() {
		return this.authorName;
	}

	protected String getContent() {
		return this.content;
	}

	public PageId getId() {
		return this.id;
	}

	protected List<String> getOptions() {
		return this.options;
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

	public void setAuthorId(final String authorId) {
		this.authorId = authorId;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	protected void setPageId(final PageId id) {
		this.id = id;
	}

	protected void setNewPageId() {
		final PageId id = this.findUnallocatedPageId();
		this.setPageId(id);
	}

	private PageId findUnallocatedPageId() {
		final PageId id = new PageId();
		final int number = this.findUnallocatedPageNumber();
		id.setNumber(number);
		final String version = "a";
		id.setVersion(version);
		return id;
	}

	int findUnallocatedPageNumber() {
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

	int generateRandomNumber(final int exponent) {
		final int POWER_BASE = 2;
		double upper = Math.pow(POWER_BASE, exponent);
		Random generator = new Random();
		double randomDouble = generator.nextDouble();
		double newNumber = (upper * randomDouble) + 1;
		return (int) newNumber;
	}

	boolean isUnallocated(final int candidate) {
		final PageId id = new PageId();
		id.setNumber(candidate);
		final String version = "a";
		id.setVersion(version);
		final StoryPage page = new StoryPage();
		page.setId(id);
		final boolean isInStore = page.isInStore();
		return (isInStore == false);
	}
}
