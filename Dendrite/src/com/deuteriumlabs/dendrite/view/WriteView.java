/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryOption;

public class WriteView extends FormView {

    private static int getListIndexValue(final String listIndex) {
        try {
            return Integer.parseInt(listIndex);
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private String from;
    private String linkIndex;
    private StoryOption option;
    private String optionText;

    public String getOptionText() {
        if (optionText == null) {
            final StoryOption option = new StoryOption();
            final String from = getFrom();
            final PageId source = new PageId(from);
            option.setSource(source);
            final String listIndex = getListIndex();
            final int listIndexValue = getListIndexValue(listIndex);
            option.setListIndex(listIndexValue);
            option.read();
            optionText = option.getText();
        }
        return optionText;
    }

    public int getTarget() {
        final StoryOption option = getOption();
        return option.getTarget();
    }

    @Override
    public String getUrl() {
        final String from = getFrom();
        String url = "/write?from=" + from;
        final String linkIndex = getListIndex();
        if (linkIndex != null) {
            url += "&linkIndex=" + linkIndex;
        }
        return url;
    }

    public boolean isNewStory() {
        final String from = getFrom();
        return ("0".equals(from));
    }

    public boolean isOptionConnected() {
        final StoryOption option = getOption();
        return option.isConnected();
    }

    public boolean isValidOption() {
        final StoryOption option = new StoryOption();
        final String from = getFrom();
        final PageId source = new PageId(from);
        option.setSource(source);
        final String listIndex = getListIndex();
        final int listIndexValue = getListIndexValue(listIndex);
        option.setListIndex(listIndexValue);
        return option.isInStore();
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public void setLinkIndex(final String linkIndex) {
        this.linkIndex = linkIndex;
    }

    private String getFrom() {
        return from;
    }

    private String getListIndex() {
        return linkIndex;
    }

    private StoryOption getOption() {
        if (option == null) {
            option = new StoryOption();
            final String from = getFrom();
            final PageId source = new PageId(from);
            option.setSource(source);
            final String listIndex = getListIndex();
            final int listIndexValue = getListIndexValue(listIndex);
            option.setListIndex(listIndexValue);
            option.read();
        }
        return option;
    }

    @Override
    protected String getMetaDesc() {
        final String text = getOptionText();
        return text + " — What happens next?";
    }
}
