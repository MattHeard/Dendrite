package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * Represents a story page.
 */
public class RewriteView extends FormView {

	private String pageNumber;

	public String getIncomingOptionText() {
		final StoryOption option = this.getIncomingOption();
		final String text = option.getText();
		return text;
	}

	private StoryOption getIncomingOption() {
		final StoryOption option = new StoryOption();
		final int target = this.getPageNumberAsInt();
		option.setTarget(target);
		option.read();
		return option;
	}

	private String getPageNumber() {
		return this.pageNumber;
	}

	public String getStoryTitle() {
		final StoryBeginning beginning = this.getBeginning();
		beginning.read();
		final String title = beginning.getTitle();
		return title;
	}

	@Override
	String getUrl() {
		String pageNumber = this.getPageNumber();
		return "/rewrite?p=" + pageNumber;
	}
	
	public boolean isBeginning() {
		final StoryBeginning beginning = this.getBeginning();
		return beginning.isInStore();
	}

	private StoryBeginning getBeginning() {
		final StoryBeginning beginning = new StoryBeginning();
		int pageNumber = getPageNumberAsInt();
		beginning.setPageNumber(pageNumber);
		return beginning;
	}

	private int getPageNumberAsInt() {
		final String pageNumberString = this.getPageNumber();
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(pageNumberString);
		} catch (NumberFormatException e) {
			pageNumber = 0;
		}
		return pageNumber;
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
