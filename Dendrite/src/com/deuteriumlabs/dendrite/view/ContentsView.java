package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.StoryBeginning;

/**
 * Represents the table of contents from which the user can select a story to
 * read.
 */
public class ContentsView extends View {

	private static final int NUM_STORIES_DISPLAYED = 10;
	private List<StoryBeginning> beginnings;
	private int contentsPageNumber;

	/* (non-Javadoc)
	 * @see com.deuteriumlabs.dendrite.view.View#getUrl()
	 */
	@Override
	String getUrl() {
		return "/";
	}
	
	/**
	 * Returns the list of titles of all beginnings on this page of contents.
	 * @return The list of titles of all beginnings on this page of contents
	 */
	public List<String> getTitles() {
		List<String> titles = new ArrayList<String>();
		List<StoryBeginning> beginnings = this.getBeginnings();
		for (StoryBeginning beginning : beginnings) {
			final String title = beginning.getTitle();
			titles.add(title);
		}
		return titles;
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
	 * Returns which page of beginnings are currently being displayed.
	 * @return The page number of contents currently being displayed
	 */
	private int getContentsPageNumber() {
		return this.contentsPageNumber;
	}
	
	/**
	 * Default constructor. Sets the default page number to 1, which displays
	 * the first page of story beginnings.
	 */
	public ContentsView() {
		this.setBeginnings(null);
		this.setContentsPageNumber(1);
	}

	/**
	 * Sets the current contents page number. The page number must be positive.
	 * If the page number is changed successfully, the cached list of beginnings
	 * is nulled so that the new page is loaded the next time the getter for the
	 * beginnings is called.
	 * @param contentsPageNumber The contents page number to change to
	 */
	private void setContentsPageNumber(final int contentsPageNumber) {
		final int previousPageNum = this.getContentsPageNumber();
		if (contentsPageNumber > 1)
			this.contentsPageNumber = contentsPageNumber;
		else
			this.contentsPageNumber = 1;
		if (contentsPageNumber != previousPageNum)
			this.setBeginnings(null);
	}

	/**
	 * Sets the list of beginnings to display.
	 * @param beginnings The list of beginnings to display
	 */
	private void setBeginnings(final List<StoryBeginning> beginnings) {
		this.beginnings = beginnings;
	}
}
