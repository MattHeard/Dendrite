package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;

/**
 * Represents a story page.
 */
public class WriteView extends View {

	private String linkIndex;
	private String from;

	@Override
	String getUrl() {
		final String from = this.getFrom();
		String url = "/write.jsp?from=" + from;
		final String linkIndex = this.getListIndex();
		if (linkIndex != null)
			url += "&linkIndex=" + linkIndex;
		return url;
	}

	private String getListIndex() {
		return this.linkIndex;
	}

	private String getFrom() {
		return this.from;
	}
	
	public void setFrom(final String from) {
		this.from = from;
	}
	
	public void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}
	
	public boolean isNewStory() {
		final String from = this.getFrom();
		return ("0".equals(from));
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

	private static int getListIndexValue(final String listIndex) {
		try {
			return Integer.parseInt(listIndex);
		} catch (NumberFormatException e) {
			return -1;
		}
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
}
