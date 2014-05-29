package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deuteriumlabs.dendrite.model.StoryBeginning;

/**
 * Represents the table of contents from which the user can select a story to
 * read.
 */
public class ContentsView extends View {
	
	private static final String CONTENTS_PAGE_NUMBER_PARAMETER_NAME = "p";
	private static final int DEFAULT_CONTENTS_PAGE_NUMBER = 1;

	public boolean isFirstPage() {
		final int number = this.getContentsPageNumber();
		return (number == 1);
	}
	
	public boolean isLastPage() {
		final int curr = this.getContentsPageNumber();
		final int last = this.getLastPageNumber();
		return (curr == last);
	}
	
	public String getPrevPageNumber() {
		final int curr = this.getContentsPageNumber();
		int prev = curr - 1;
		if (prev < 1)
			prev = 1;
		return Integer.toString(prev);
	}
	
	public String getNextPageNumber() {
		final int curr = this.getContentsPageNumber();
		int next = curr + 1;
		return Integer.toString(next);
	}

	private int getLastPageNumber() {
		final int numberOfStories = this.getNumberOfStories();
		if (numberOfStories == 0)
			return 1;
		else
			return ((numberOfStories - 1) / NUM_STORIES_DISPLAYED) + 1;
	}

	private int getNumberOfStories() {
		return StoryBeginning.countAllBeginnings();
	}

	private static final int NUM_STORIES_DISPLAYED = 10;
	private List<StoryBeginning> beginnings;
	private int contentsPageNumber;

	/**
	 * Default constructor. Sets the default page number to 1, which displays
	 * the first page of story beginnings.
	 */
	public ContentsView() {
		this.setBeginnings(null);
		this.setContentsPageNumber(1);
	}
	
	/**
	 * Returns the list of all beginnings on this page of contents.
	 * @return The list of all beginnings on this page of contents
	 */
	private List<StoryBeginning> getBeginnings() {
		if (this.beginnings == null)
			this.readBeginnings();
		return this.beginnings;
	}

	/**
	 * Returns which page of beginnings are currently being displayed.
	 * @return The page number of contents currently being displayed
	 */
	private int getContentsPageNumber() {
		return this.contentsPageNumber;
	}

	/**
	 * Returns the list of page numbers of all beginnings on this page of
	 * contents.
	 * @return The list of page numbers of all beginnings on this page of
	 * contents
	 */
	public List<String> getPageNumbers() {
		final List<String> numbers = new ArrayList<String>();
		List<StoryBeginning> beginnings = this.getBeginnings();
		for (final StoryBeginning beginning : beginnings) {
			final int numberValue = beginning.getPageNumber();
			final String numberString = Integer.toString(numberValue);
			numbers.add(numberString);
		}
		return numbers;
	}
	
	/**
	 * Returns the list of links to all beginnings on this page of contents.
	 * @return The list of links to all beginnings on this page of contents
	 */
	public List<String> getLinks() {
		final List<String> links = new ArrayList<String>();
		List<String> numbers = this.getPageNumbers();
		for (final String number : numbers) {
			final String link = "/read.jsp?p=" + number;
			links.add(link);
		}
		return links;
	}

	/**
	 * Returns the list of titles of all beginnings on this page of contents.
	 * @return The list of titles of all beginnings on this page of contents
	 */
	public List<String> getTitles() {
		final List<String> titles = new ArrayList<String>();
		List<StoryBeginning> beginnings = this.getBeginnings();
		for (final StoryBeginning beginning : beginnings) {
			final String title = beginning.getTitle();
			titles.add(title);
		}
		return titles;
	}
	
	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.view.View#getUrl()
	 */
	@Override
	String getUrl() {
		return "/";
	}

	/**
	 * Loads a page of beginnings from the datastore. The beginnings are
	 * paginated so that a limited number of beginnings are displayed at one
	 * time.
	 */
	private void readBeginnings() {
		final int contentsPageNumber = this.getContentsPageNumber();
		final int firstIndex = (contentsPageNumber - 1) * NUM_STORIES_DISPLAYED;
		final int lastIndex = firstIndex + NUM_STORIES_DISPLAYED;
		final List<StoryBeginning> beginnings;
		beginnings = StoryBeginning.getBeginnings(firstIndex, lastIndex);
		this.setBeginnings(beginnings);
	}

	/**
	 * Sets the list of beginnings to display.
	 * @param beginnings The list of beginnings to display
	 */
	private void setBeginnings(final List<StoryBeginning> beginnings) {
		this.beginnings = beginnings;
	}
	
	/**
	 * Sets the current contents page number. The page number must be positive.
	 * If the page number is changed successfully, the cached list of beginnings
	 * is nulled so that the new page is loaded the next time the getter for the
	 * beginnings is called.
	 * @param contentsPageNumber The contents page number to change to
	 */
	public void setContentsPageNumber(final int contentsPageNumber) {
		final int previousPageNum = this.getContentsPageNumber();
		if (contentsPageNumber > 1)
			this.contentsPageNumber = contentsPageNumber;
		else
			this.contentsPageNumber = 1;
		if (contentsPageNumber != previousPageNum)
			this.setBeginnings(null);
	}
	
	public void setContentsPageNumberFromRequest(final HttpServletRequest req) {
		final String parameterName = CONTENTS_PAGE_NUMBER_PARAMETER_NAME;
		final String parameter = req.getParameter(parameterName);
		final int number = getContentsPageNumberFromParameter(parameter);
		this.setContentsPageNumber(number);
	}

	private int getContentsPageNumberFromParameter(final String parameter) {
		try {
		    return Integer.parseInt(parameter);
		} catch (NumberFormatException e) {
		    return DEFAULT_CONTENTS_PAGE_NUMBER;
		}
	}
}
