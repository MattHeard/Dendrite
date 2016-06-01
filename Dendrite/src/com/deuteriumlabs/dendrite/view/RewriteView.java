/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class RewriteView extends FormView {

    private String pageNum;

    public String getIncomingOptionText() {
        final StoryOption option = getIncomingOption();
        final String text = option.getText();
        return text;
    }

    public String getStoryTitle() {
        final StoryBeginning beginning = getBeginning();
        beginning.read();
        final String title = beginning.getTitle();
        return title;
    }

    @Override
    public String getUrl() {
        final String pageNumber = pageNum;
        return "/rewrite?p=" + pageNumber;
    }

    public boolean isBeginning() {
        final StoryBeginning beginning = getBeginning();
        return beginning.isInStore();
    }

    public boolean isExistingPage() {
        final String pageNumber = pageNum;
        final PageId id = new PageId(pageNumber);
        id.setVersion("a");
        final StoryPage page = new StoryPage();
        page.setId(id);
        return page.isInStore();
    }

    public void setPageNumber(final String pageNum) {
        this.pageNum = pageNum;
    }

    private StoryBeginning getBeginning() {
        final StoryBeginning beginning = new StoryBeginning();
        final int pageNumber = getPageNumberAsInt();
        beginning.setPageNumber(pageNumber);
        return beginning;
    }

    private StoryOption getIncomingOption() {
        final StoryOption option = new StoryOption();
        final int target = getPageNumberAsInt();
        option.setTarget(target);
        option.read();
        return option;
    }

    private int getPageNumberAsInt() {
        final String pageNumberString = pageNum;
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(pageNumberString);
        } catch (final NumberFormatException e) {
            pageNumber = 0;
        }
        return pageNumber;
    }

    @Override
    protected String getMetaDesc() {
        return "Rewrite the story at page " + pageNum;
    }
}
