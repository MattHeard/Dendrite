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
	
	public boolean isValidOption() {
		final boolean isFromValid = this.isFromValid();
		final boolean isLinkIndexValid = this.isLinkIndexValid();
		return (isFromValid && isLinkIndexValid);
	}

	private boolean isLinkIndexValid() {
		final String linkIndex = this.getLinkIndex();
		int linkIndexValue;
		try {
			linkIndexValue = Integer.parseInt(linkIndex);
		} catch (NumberFormatException e) {
			return false;
		}
		final boolean isZeroOrGreater = (linkIndexValue >= 0);
		final boolean isLessThanFive = (linkIndexValue < 5);
		return (isZeroOrGreater && isLessThanFive);
	}

	private boolean isFromValid() {
		final String from = this.getFrom();
		int fromValue;
		try {
			fromValue = Integer.parseInt(from);
		} catch (NumberFormatException e) {
			return false;
		}
		return (fromValue > 0);
	}
}
