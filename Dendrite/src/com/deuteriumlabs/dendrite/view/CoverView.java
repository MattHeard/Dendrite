package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class CoverView extends View {

    @Override
    String getUrl() {
        return "/cover";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite");
    }

    public void findThreeStories() {
        prepareStories();
    }

    private void prepareStories() {
        this.prepareFirstStory();
        this.prepareSecondStory();
        this.prepareThirdStory();
    }

    private void prepareThirdStory() {
        final PageContext pageContext = this.getPageContext();
        final String thirdStoryTitle = getStoryTitle(1);
        pageContext.setAttribute("thirdStoryTitle", thirdStoryTitle);
        final String thirdStorySummary = "Once upon a time...";
        pageContext.setAttribute("thirdStorySummary", thirdStorySummary);
    }

    private String getStoryTitle(final int index) {
        return "Leah's adventures in Catland";
    }

    private void prepareSecondStory() {
        final PageContext pageContext = this.getPageContext();
        final String secondStoryTitle = "Leah's adventures in Flatland";
        pageContext.setAttribute("secondStoryTitle", secondStoryTitle);
        final String secondStorySummary = "Once upon a time...";
        pageContext.setAttribute("secondStorySummary", secondStorySummary);
    }

    private void prepareFirstStory() {
        final PageContext pageContext = this.getPageContext();
        final String firstStoryTitle = "Leah's adventures in Mattland";
        pageContext.setAttribute("firstStoryTitle", firstStoryTitle);
        final String firstStorySummary = "Once upon a time...";
        pageContext.setAttribute("firstStorySummary", firstStorySummary);
    }
}
