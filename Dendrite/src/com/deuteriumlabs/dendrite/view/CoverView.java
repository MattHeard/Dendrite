package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

public class CoverView extends View {

    private class Story {
        String title;
        String summary;
    }

    private List<Story> stories;

    @Override
    String getUrl() {
        return "/cover";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite");
        this.initialiseStories();
    }

    private void initialiseStories() {
        this.setStories(new ArrayList<Story>());
    }

    private void setStories(final List<Story> stories) {
        this.stories = stories;
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
