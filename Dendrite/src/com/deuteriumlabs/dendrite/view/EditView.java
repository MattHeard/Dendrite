package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * Represents a story page.
 */
public class EditView extends FormView {

	private String pageNumber;

	private String getPageNumber() {
		return this.pageNumber;
	}

	@Override
	String getUrl() {
		String pageNumber = this.getPageNumber();
		return "/edit.jsp?p=" + pageNumber;
	}

	public boolean isExistingPage() {
		final String pageNumber = this.getPageNumber();
		final PageId id = new PageId(pageNumber);
		id.setVersion("a");
		final StoryPage page = new StoryPage();
		page.setId(id);
		return page.isInStore();
	}

	public void setPageNumber(final String pageNumber) {
		this.pageNumber = pageNumber;
	}
}
