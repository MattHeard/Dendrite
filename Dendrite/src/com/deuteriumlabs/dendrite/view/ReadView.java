package com.deuteriumlabs.dendrite.view;

import java.util.Arrays;
import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.api.datastore.Text;

/**
 * Represents a story page.
 */
public class ReadView extends View {

	private StoryBeginning beginning;
	private StoryPage page;
	private PageId pageId;
	
	public ReadView() {
	}
	
	public String getAuthorId() {
		final StoryPage page = this.getPage();
		return page.getAuthorId();
	}
	
	public String getAuthorName() {
		final StoryPage page = this.getPage();
		return page.getAuthorName();
	}

	private StoryBeginning getBeginning() {
		return this.beginning;
	}
	
	public String getFirstUrl() {
		final StoryPage page = this.getPage();
		final PageId firstPageId = page.getBeginning();
		return "read.jsp?p=" + firstPageId;
	}
	
	public int getNumberOfOptions() {
		final PageId source = this.getPageId();
		return StoryOption.countOptions(source);
	}
	
	public String getOptionLink(final int index) {
		final StoryOption option = new StoryOption();
		final PageId source = this.getPageId();
		option.setSource(source);
		option.setListIndex(index);
		option.read();
		final int target = option.getTarget();
		if (target == 0) {
			final String from = source.toString();
			return "/write.jsp?from=" + from + "&linkIndex=" + index;
		} else {
			return "/read.jsp?p=" + target;
		}
	}

	public String getOptionText(final int index) {
		final StoryOption option = new StoryOption();
		final PageId source = this.getPageId();
		option.setSource(source);
		option.setListIndex(index);
		option.read();
		return option.getText();
	}

	private StoryPage getPage() {
		return this.page;
	}
	
	private PageId getPageId() {
		return this.pageId;
	}
	
	public String getPageNumber() {
		final PageId id = this.getPageId();
		final int number = id.getNumber();
		return Integer.toString(number);
	}

	public String getPageText() {
		final StoryPage page = this.getPage();
		final Text text = page.getText();
		if (text != null)
			return text.getValue();
		else
			return null;
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

	public String getTitle() {
		final StoryBeginning beginning = this.getBeginning();
		return beginning.getTitle();
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

	public boolean isAuthorAnonymous() {
		final StoryPage page = this.getPage();
		final String authorId = page.getAuthorId();
		return (authorId == null);
	}
	
	public boolean isBeginning() {
		final PageId currPageId = this.getPageId();
		final int currPageNumber = currPageId.getNumber();
		final StoryBeginning beginning = this.getBeginning();
		final int beginningPageNumber = beginning.getPageNumber();
		return (currPageNumber == beginningPageNumber);
	}
	
	public boolean isPageInStore() {
		final StoryPage page = this.getPage();
		return page.isInStore();
	}
	
	private void setBeginning(final StoryBeginning beginning) {
		this.beginning = beginning;
	}
	
	private void setPage(final StoryPage page) {
		this.page = page;
	}
	
	private void setPageId(final PageId id) {
		this.pageId = id;
		final StoryPage page = new StoryPage();
		page.setId(id);
		page.read();
		this.setPage(page);
		final PageId beginningId = page.getBeginning();
		final StoryBeginning beginning = new StoryBeginning();
		final int pageNumber = beginningId.getNumber();
		beginning.setPageNumber(pageNumber);
		beginning.read();
		this.setBeginning(beginning);
	}
	
	public void setPageId(final String idString) {
		final PageId id = getSpecificPageId(idString);
		this.setPageId(id);
	}
	
	public List<String> getParagraphs() {
		final String text = this.getPageText();
		String[] array = text.split("\n");
		return Arrays.asList(array);
	}
	
	public enum Format {
		BOLD, BOLD_ITALIC, ITALIC, NONE
	}
}
