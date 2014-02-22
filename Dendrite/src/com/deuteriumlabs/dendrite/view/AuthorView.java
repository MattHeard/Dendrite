package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.datastore.Text;

/**
 * Presents a list of pages written by a particular user and presents user
 * preferences visible only to the matching user.
 */
public class AuthorView extends View {

	private static final String ELLIPSIS = "...";
	private static final int NUM_PAGES_DISPLAYED = 10;
	private static final int SUMMARY_LEN = 30;
	private static String summariseText(final Text text) {
		final String full = text.getValue();
		final int fullSize = full.length();
		if (fullSize < (SUMMARY_LEN - 3))
			return full;
		else {
			final String cropped = full.substring(0, (SUMMARY_LEN - 3));
			return cropped + ELLIPSIS;
		}
	}
	private List<String> authorNames;
	private int authorPageNumber;
	private String id;
	private List<String> pageIds;
	private List<StoryPage> pages;
	private List<String> summaries;
	private List<String> titles;
	
	private User user;

	public AuthorView() {
		this.setAuthorPageNumber(1);
	}

	public List<String> getAuthorNames() {
		if (this.authorNames == null)
			this.readAuthorNames();
		return this.authorNames;
	}

	private int getAuthorPageNumber() {
		return this.authorPageNumber;
	}

	private String getId() {
		return this.id;
	}

	private int getLastPageNumber() {
		final int numberOfStories = this.getNumberOfPages();
		return (numberOfStories / NUM_PAGES_DISPLAYED) + 1;
	}
	
	public String getNextPageNumber() {
		final int curr = this.getAuthorPageNumber();
		int next = curr + 1;
		return Integer.toString(next);
	}
	
	private int getNumberOfPages() {
		final String authorId = this.getId();
		return StoryPage.countAllPagesWrittenBy(authorId);
	}

	public List<String> getPageIds() {
		if (this.pageIds == null)
			this.readPageIds();
		return this.pageIds;
	}

	private List<StoryPage> getPages() {
		if (this.pages == null)
			this.readPages();
		return this.pages;
	}

	public String getPenName() {
		final User user = this.getUser();
		final String penName = user.getDefaultPenName();
		return penName;
	}

	public String getPrevPageNumber() {
		final int curr = this.getAuthorPageNumber();
		int prev = curr - 1;
		if (prev < 1)
			prev = 1;
		return Integer.toString(prev);
	}

	public List<String> getSummaries() {
		if (this.summaries == null)
			this.readSummaries();
		return this.summaries;
	}

	public List<String> getTitles() {
		if (this.titles == null)
			this.readTitles();
		return this.titles;
	}

	@Override
	String getUrl() {
		final String id = this.getId();
		return "/author.jsp?id=" + id;
	}

	private User getUser() {
		return this.user;
	}

	public boolean isFirstPage() {
		final int number = this.getAuthorPageNumber();
		return (number == 1);
	}

	public boolean isLastPage() {
		final int curr = this.getAuthorPageNumber();
		final int last = this.getLastPageNumber();
		return (curr == last);
	}

	private void readAuthorNames() {
		final List<StoryPage> pages = this.getPages();
		final List<String> authorNames = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final String authorName = page.getAuthorName();
			authorNames.add(authorName);
		}
		this.setAuthorNames(authorNames);
	}

	private void setAuthorNames(final List<String> authorNames) {
		this.authorNames = authorNames;
	}

	private void readPageIds() {
		final List<StoryPage> pages = this.getPages();
		final List<String> pageIds = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final PageId id = page.getId();
			final String idString = id.toString();
			pageIds.add(idString);
		}
		this.setPageIds(pageIds);
	}

	private void readPages() {
		final int authorPageNumber = this.getAuthorPageNumber();
		final int firstIndex = (authorPageNumber - 1) * NUM_PAGES_DISPLAYED;
		final int lastIndex = firstIndex + NUM_PAGES_DISPLAYED;
		final List<StoryPage> pages;
		final String authorId = this.getId();
		pages = StoryPage.getPagesWrittenBy(authorId, firstIndex, lastIndex);
		this.setPages(pages);
	}

	private void readSummaries() {
		final List<StoryPage> pages = this.getPages();
		final List<String> summaries = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final Text text = page.getText();
			final String summary = summariseText(text);
			summaries.add(summary);
		}
		this.setSummaries(summaries);
	}

	private void readTitles() {
		final List<StoryPage> pages = this.getPages();
		final List<String> titles = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final PageId id = page.getBeginning();
			final int number = id.getNumber();
			final StoryBeginning beginning = new StoryBeginning();
			beginning.setPageNumber(number);
			beginning.read();
			final String title = beginning.getTitle();
			titles.add(title);
		}
		this.setTitles(titles);
	}

	public void setAuthorPageNumber(final int authorPageNumber) {
		final int previousPageNumber = this.getAuthorPageNumber();
		if (authorPageNumber > 1)
			this.authorPageNumber = authorPageNumber;
		else
			this.authorPageNumber = 1;
		if (authorPageNumber != previousPageNumber) {
			this.setTitles(null);
			this.setSummaries(null);
			this.setPageIds(null);
		}
	}
	
	public void setId(final String id) {
		this.id = id;
		final User user = new User();
		user.setId(id);
		user.read();
		this.setUser(user);
	}
	
	private void setPageIds(final List<String> pageIds) {
		this.pageIds = pageIds;
	}
	
	private void setPages(final List<StoryPage> pages) {
		this.pages = pages;

	}
	
	private void setSummaries(final List<String> summaries) {
		this.summaries = summaries;
	}

	private void setTitles(final List<String> titles) {
		this.titles = titles;
	}

	private void setUser(final User user) {
		this.user = user;
	}
}
