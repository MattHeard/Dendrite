package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;

/**
 * Represents a story page.
 */
public class WriteView extends View {

	private static int getListIndexValue(final String listIndex) {
		try {
			return Integer.parseInt(listIndex);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private String error;
	private String from;
	private String linkIndex;
	
	private String getError() {
		return this.error;
	}

	private String getFrom() {
		return this.from;
	}
	private String getListIndex() {
		return this.linkIndex;
	}

	public String getOptionText() {
		final StoryOption option = new StoryOption();
		final String from = this.getFrom();
		final PageId source = new PageId(from);
		option.setSource(source);
		final String listIndex = this.getListIndex();
		int listIndexValue = getListIndexValue(listIndex);
		option.setListIndex(listIndexValue);
		option.read();
		return option.getText();
	}

	@Override
	String getUrl() {
		final String from = this.getFrom();
		String url = "/write.jsp?from=" + from;
		final String linkIndex = this.getListIndex();
		if (linkIndex != null)
			url += "&linkIndex=" + linkIndex;
		return url;
	}

	public boolean isNewStory() {
		final String from = this.getFrom();
		return ("0".equals(from));
	}
	
	public boolean isThereABlankAuthorError() {
		final String error = this.getError();
		return ("blankAuthor".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereABlankContentError() {
		final String error = this.getError();
		return ("blankContent".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isValidOption() {
		final StoryOption option = new StoryOption();
		final String from = this.getFrom();
		final PageId source = new PageId(from);
		option.setSource(source);
		final String listIndex = this.getListIndex();
		int listIndexValue = getListIndexValue(listIndex);
		option.setListIndex(listIndexValue);
		return option.isInStore();
	}
	
	public void setError(final String error) {
		this.error = error;
	}

	public void setFrom(final String from) {
		this.from = from;
	}
	
	public void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}
}
