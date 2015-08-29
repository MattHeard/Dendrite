/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;

/**
 * Represents a story page.
 */
public class WriteView extends FormView {

	private static int getListIndexValue(final String listIndex) {
		try {
			return Integer.parseInt(listIndex);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private String from;
	private String linkIndex;
	private StoryOption option;
	private String optionText;

	private String getFrom() {
		return this.from;
	}

	private String getListIndex() {
		return this.linkIndex;
	}

	public String getOptionText() {
		if (this.optionText == null) {
			final StoryOption option = new StoryOption();
			final String from = this.getFrom();
			final PageId source = new PageId(from);
			option.setSource(source);
			final String listIndex = this.getListIndex();
			int listIndexValue = getListIndexValue(listIndex);
			option.setListIndex(listIndexValue);
			option.read();
			this.optionText = option.getText();
		}
		return this.optionText;
	}

	@Override
	public String getUrl() {
		final String from = this.getFrom();
		String url = "/write?from=" + from;
		final String linkIndex = this.getListIndex();
		if (linkIndex != null)
			url += "&linkIndex=" + linkIndex;
		return url;
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

	public void setFrom(final String from) {
		this.from = from;
	}

	public void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}

	public boolean isOptionConnected() {
		final StoryOption option = this.getOption();
		return option.isConnected();
	}

	public int getTarget() {
		final StoryOption option = this.getOption();
		return option.getTarget();
	}

	private StoryOption getOption() {
		StoryOption option = this.option;
		if (option == null) {
			option = new StoryOption();
			final String from = this.getFrom();
			final PageId source = new PageId(from);
			option.setSource(source);
			final String listIndex = this.getListIndex();
			int listIndexValue = getListIndexValue(listIndex);
			option.setListIndex(listIndexValue);
			option.read();
		}
		return option;
	}

	@Override
	protected String getMetaDesc() {
		final String text = this.getOptionText();
		return text + " — What happens next?";
	}
}
