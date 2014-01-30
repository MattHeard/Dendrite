package com.deuteriumlabs.dendrite.view;

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
		final String linkIndex = this.getLinkIndex();
		if (linkIndex != null)
			url += "&linkIndex=" + linkIndex;
		return url;
	}

	private String getLinkIndex() {
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
}
