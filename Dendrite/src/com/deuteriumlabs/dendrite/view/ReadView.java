package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * Represents a story page.
 */
public class ReadView extends View {

	private PageId pageId;
	private StoryPage page;
	private StoryBeginning beginning;
	
	public String getTitle() {
		final StoryBeginning beginning = this.getBeginning();
		return beginning.getTitle();
	}
	
	private StoryBeginning getBeginning() {
		return this.beginning;
	}

	public ReadView() {
	}

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.view.View#getUrl()
	 */
	@Override
	String getUrl() {
		final PageId idValue = this.getPageId();
		final String idString = idValue.toString();
		return "/read.jsp?p=" + idString;
	}

	private PageId getPageId() {
		return this.pageId;
	}
	
	private void setPageId(final PageId id) {
		this.pageId = id;
		final StoryPage page = new StoryPage();
		page.setId(id);
		page.read();
		this.setPage(page);
		final StoryBeginning beginning = new StoryBeginning();
		final int pageNumber = id.getNumber();
		beginning.setPageNumber(pageNumber);
		beginning.read();
		this.setBeginning(beginning);
	}
	
	private void setBeginning(final StoryBeginning beginning) {
		this.beginning = beginning;
	}

	public void setPageId(final String idString) {
		final PageId id = getSpecificPageId(idString);
		this.setPageId(id);
	}

	private PageId getSpecificPageId(final String string) {
		final PageId id = new PageId(string);
		String version = id.getVersion();
		if (version == null) {
			version = StoryPage.getRandomVersion(id);
			id.setVersion(version);
		}
		return id;
	}

	private void setPage(final StoryPage page) {
		this.page = page;
	}
	
	public boolean isPageInStore() {
		final StoryPage page = this.getPage();
		return page.isInStore();
	}

	private StoryPage getPage() {
		return this.page;
	}
	
	public String getPageText() {
		final StoryPage page = this.getPage();
		return page.getText();
	}
}
