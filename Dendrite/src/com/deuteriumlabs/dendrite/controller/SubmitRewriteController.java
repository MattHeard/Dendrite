package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;


public class SubmitRewriteController extends SubmitController {

	protected int pageNumber;

	@Override
	void buildStoryPage() {
		final StoryPage page = new StoryPage();
		this.addStoryPageValues(page);
		final PageId beginning = this.getBeginning();
		page.setBeginning(beginning);
		page.setParent(this.getParent());
		final boolean isInStore = page.isInStore();
		if (isInStore == false) {
			page.create();
		}
	}

	private PageId getBeginning() {
		final StoryPage alternative = new StoryPage();
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		id.setVersion("a");
		alternative.setId(id);
		alternative.read();
		final PageId alternativeBeginning = alternative.getBeginning();
		final int altBeginningNumber = alternativeBeginning.getNumber();
		if (altBeginningNumber != number) {
			return alternativeBeginning;
		} else {
			return this.getId();
		}
	}

	private String getNextVersion() {
		final int pageNumber = this.getPageNumber();
		final int count = StoryPage.countVersions(pageNumber);
		return StoryPage.convertNumberToVersion(count + 1);
	}

	private int getPageNumber() {
		return this.pageNumber;
	}

	@Override
	protected void setNewPageId() {
		this.setNextPageId();
	}

	private void setNextPageId() {
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		final String version = this.getNextVersion();
		id.setVersion(version);
		this.setPageId(id);
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
