package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitNewPageController {
	private static final int MAX_EXPONENT = 30;

	private PageId from;
	private int linkIndex;
	private String content;
	private List<String> options;

	private PageId id;
	
	public SubmitNewPageController() {
		this.options = new ArrayList<String>();
	}

	public void setFrom(final String from) {
		final PageId id = new PageId(from);
		this.from = id;
	}

	public void setLinkIndex(String linkIndex) {
		int indexValue;
		try {
			indexValue = Integer.parseInt(linkIndex);
		} catch (NumberFormatException e) {
			indexValue = -1;
		}
		this.linkIndex = indexValue;
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

	public void buildNewPage() {
		this.setNewPageId();
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
		final PageId beginning = this.getBeginning();
		page.setBeginning(beginning);
		final boolean isInStore = page.isInStore();
		if (isInStore == false)
			page.create();
	}

	private PageId getBeginning() {
		final StoryPage parent = new StoryPage();
		final PageId from = this.getFrom();
		parent.setId(from);
		parent.read();
		return parent.getBeginning();
	}

	private PageId getFrom() {
		return this.from;
	}

	private String getContent() {
		return this.content;
	}

	public PageId getId() {
		return this.id;
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

	public void connectIncomingOption() {
		final StoryOption incoming = new StoryOption();
		final PageId from = this.getFrom();
		incoming.setSource(from);
		final int linkIndex = this.getLinkIndex();
		incoming.setListIndex(linkIndex);
		incoming.read();
		final PageId targetId = this.getId();
		final int targetPageNumber = targetId.getNumber();
		incoming.setTarget(targetPageNumber);
		incoming.update();
	}

	private int getLinkIndex() {
		return this.linkIndex;
	}
}
