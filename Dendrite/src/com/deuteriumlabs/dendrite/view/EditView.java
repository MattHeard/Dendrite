package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * Represents a story page.
 */
public class EditView extends View {

	private String error;
	private String pageNumber;

	private String getError() {
		return this.error;
	}

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

	public boolean isThereABlankAuthorError() {
		final String error = this.getError();
		return ("blankAuthor".equals(error)); // Yoda-style to avoid null
	}

	public boolean isThereABlankContentError() {
		final String error = this.getError();
		return ("blankContent".equals(error)); // Yoda-style to avoid null
	}

	public void setError(final String error) {
		this.error = error;
	}

	public void setPageNumber(final String pageNumber) {
		this.pageNumber = pageNumber;
	}
}
