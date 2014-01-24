package com.deuteriumlabs.dendrite.model;

/**
 *	Represents a page of a story. <code>StoryPage</code> instances act as nodes
 *	in a tree to form a complete story. The unordered nature of the page IDs of
 *	the pages in a story causes the branches of different stories to be
 *	interwoven.
 */
public class StoryPage extends Model {
	private PageId id;

	/**
	 * Returns the ID of this story page.
	 * @return the ID of this story page
	 */
	public PageId getId() {
		return id;
	}

	/**
	 * Sets the ID of the story page.
	 * @param id the new id for the story page
	 */
	public void setId(final PageId id) {
		this.id = id;
	}
}
