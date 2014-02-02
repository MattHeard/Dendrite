package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitNewStoryController {

	private static final int MAX_EXPONENT = 30;
	private String title;
	private String content;
	private List<String> options;
	private PageId id;
	
	public SubmitNewStoryController() {
		this.options = new ArrayList<String>();
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public void addOption(final String option) {
		final List<String> options = this.getOptions();
		final int size = options.size();
		if (size < 5)
			options.add(option);
	}

	private List<String> getOptions() {
		return this.options;
	}

	public void buildNewStory() {
		this.setNewPageId();
		this.buildStoryBeginning();
		this.buildStoryPage();
		this.buildStoryOptions();
	}

	private void setNewPageId() {
		final PageId id = this.findUnallocatedPageId();
		this.setPageId(id);
	}

	private void setPageId(final PageId id) {
		this.id = id;
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

	private int generateRandomNumber(final int exponent) {
		final int POWER_BASE = 2;
		double upper = Math.pow(POWER_BASE, exponent);
		Random generator = new Random();
		double randomDouble = generator.nextDouble();
		double newNumber = (upper * randomDouble) + 1;
		return (int) newNumber;
	}

	private void buildStoryOptions() {
		final List<String> optionTexts = this.getOptions();
		for (final String optionText : optionTexts) {
			final StoryOption option = new StoryOption();
			final PageId id = this.getId();
			option.setSource(id);
			final int index = optionTexts.indexOf(option);
			option.setListIndex(index);
			option.setText(optionText);
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
		final boolean isInStore = page.isInStore();
		if (isInStore == false)
			page.create();
	}

	private String getContent() {
		return this.content;
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

	public PageId getId() {
		return this.id;
	}

	private String getTitle() {
		return this.title;
	}

}
