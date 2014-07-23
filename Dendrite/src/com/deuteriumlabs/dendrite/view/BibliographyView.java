package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.google.appengine.api.datastore.Text;

class BibliographyView {

    public class StoryPageEntry {
        String authorName;
        String pageId;
        String summary;
        String title;
    }

    private static final int DEFAULT_AUTHOR_PAGE_NUM = 1;
    private static final String ELLIPSIS = "...";
    private static final int NUM_PAGES_DISPLAYED = 10;
    private static final int SUMMARY_LEN = 30;

    public static String summariseText(final Text text) {
        final String full = text.getValue();
        final int fullSize = full.length();
        if (fullSize < (SUMMARY_LEN - 3))
            return full;
        else {
            final String cropped = full.substring(0, (SUMMARY_LEN - 3));
            return cropped + ELLIPSIS;
        }
    }

    private int authorPageNumber;
    private String currTitle, prevTitle;
    private String authorId;
    private int numStoryPagesAlreadyDisplayed;
    private List<StoryPage> pages;
    private List<String> authorNames, titles, pageIds, summaries;
    private List<StoryPageEntry> entries;
    private PageContext pageContext;
    private String currStoryPageAuthorName;

    public int getAuthorPageNumber() {
        return authorPageNumber;
    }

    public String getCurrTitle() {
        return currTitle;
    }

    public int getFirstIndex() {
        final int authorPageNumber = this.getAuthorPageNumber();
        return (authorPageNumber - 1) * NUM_PAGES_DISPLAYED;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public int getLastIndex() {
        final int firstIndex = this.getFirstIndex();
        return firstIndex + NUM_PAGES_DISPLAYED;
    }

    public int getNumStoryPagesAlreadyDisplayed() {
        return numStoryPagesAlreadyDisplayed;
    }

    public int getNumStoryPagesToDisplay() {
        final List<String> titles = this.getTitles();
        return titles.size();
    }

    public List<StoryPage> getPages() {
        if (this.pages == null) {
            this.readPages();
        }
        return this.pages;
    }

    public String getPrevTitle() {
        return prevTitle;
    }

    public List<String> getSummaries() {
        if (this.summaries == null) {
            this.readSummaries();
        }
        return this.summaries;
    }

    public List<String> getTitles() {
        if (this.titles == null) {
            this.readTitles();
        }
        return this.titles;
    }

    public boolean hasAnotherStoryPage() {
        final int numAlreadyDisplayed = this.getNumStoryPagesAlreadyDisplayed();
        final int numToDisplay = this.getNumStoryPagesToDisplay();
        return (numAlreadyDisplayed < numToDisplay);
    }

    public void incrementNumStoryPagesAlreadyDisplayed() {
        final int num = this.getNumStoryPagesAlreadyDisplayed();
        this.setNumStoryPagesAlreadyDisplayed(num + 1);
    }

    public void readPages() {
        final int firstIndex = this.getFirstIndex();
        final int lastIndex = this.getLastIndex();
        final List<StoryPage> pages;
        final String authorId = this.getAuthorId();
        pages = StoryPage.getPagesWrittenBy(authorId, firstIndex, lastIndex);
        this.setPages(pages);
    }

    public void readSummaries() {
        final List<StoryPage> pages = this.getPages();
        final List<String> summaries = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final Text text = page.getText();
            final String summary = summariseText(text);
            summaries.add(summary);
        }
        this.setSummaries(summaries);
    }

    public void readTitles() {
        final List<StoryPage> pages = this.getPages();
        final List<String> titles = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final PageId id = page.getBeginning();
            final int number = id.getNumber();
            final StoryBeginning beginning = new StoryBeginning();
            beginning.setPageNumber(number);
            beginning.read();
            final String title = beginning.getTitle();
            titles.add(title);
        }
        this.setTitles(titles);
    }

    public void savePrevTitle() {
        final String currTitle = this.getCurrTitle();
        this.setPrevTitle(currTitle);
    }

    public void setAuthorPageNumber(final int authorPageNumber) {
        this.authorPageNumber = authorPageNumber;
    }

    public void setCurrTitle(final String currTitle) {
        this.currTitle = currTitle;
    }

    public void setDefaultAuthorPageNumber() {
        this.setAuthorPageNumber(DEFAULT_AUTHOR_PAGE_NUM);
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public void setNumStoryPagesAlreadyDisplayed(final int num) {
        this.numStoryPagesAlreadyDisplayed = num;
    }

    public void setPages(final List<StoryPage> pages) {
        this.pages = pages;
    }

    public void setPrevTitle(final String prevTitle) {
        this.prevTitle = prevTitle;
    }

    public void setSummaries(final List<String> summaries) {
        this.summaries = summaries;
    }

    public void setTitles(final List<String> titles) {
        this.titles = titles;
    }

    public List<String> getPageIds() {
        if (this.pageIds == null) {
            this.readPageIds();
        }
        return this.pageIds;
    }

    public void setPageIds(final List<String> pageIds) {
        this.pageIds = pageIds;
    }

    public void readPageIds() {
        final List<StoryPage> pages = this.getPages();
        final List<String> pageIds = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final PageId id = page.getId();
            final String idString = id.toString();
            pageIds.add(idString);
        }
        this.setPageIds(pageIds);		
    }

    public List<String> getAuthorNames() {
        if (this.authorNames == null) {
            this.readAuthorNames();
        }
        return this.authorNames;
    }

    public void setAuthorNames(final List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public void readAuthorNames() {
        final List<StoryPage> pages = this.getPages();
        final List<String> authorNames = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final String authorName = page.getAuthorName();
            authorNames.add(authorName);
        }
        this.setAuthorNames(authorNames);
    }

    public List<StoryPageEntry> getEntries() {
        if (this.entries == null) {
            this.generateEntries();
        }
        return this.entries;
    }

    public void setEntries(final List<StoryPageEntry> entries) {
        this.entries = entries;
    }

    public void generateEntries() {
        final List<String> titles = this.getTitles();
        final int length = titles.size();
        final List<String> summaries = this.getSummaries();
        final List<String> pageIds = this.getPageIds();
        final List<String> authorNames = this.getAuthorNames();

        final List<StoryPageEntry> pages = new ArrayList<StoryPageEntry>();
        for (int i = 0; i < length; i++) {
            final StoryPageEntry page = new StoryPageEntry();
            page.title = titles.get(i);
            page.summary = summaries.get(i);
            page.pageId = pageIds.get(i);
            page.authorName = authorNames.get(i);
            pages.add(page);
        }

        this.setEntries(pages);
    }

    public StoryPageEntry getNextStoryPage() {
        final List<StoryPageEntry> pages = this.getEntries();
        final int index = this.getNumStoryPagesAlreadyDisplayed();
        return pages.get(index);
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public void setPageContext(final PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void prepareStoryPage(final StoryPageEntry page) {
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("title", page.title);
        pageContext.setAttribute("summary", page.summary);
        pageContext.setAttribute("pageId", page.pageId);
        pageContext.setAttribute("authorName", page.authorName);
    }

    public String getCurrStoryPageAuthorName() {
        return currStoryPageAuthorName;
    }

    public void setCurrStoryPageAuthorName(String currStoryPageAuthorName) {
        this.currStoryPageAuthorName = currStoryPageAuthorName;
    }

    public void prepareNextStoryPage() {
        this.savePrevTitle();
        final StoryPageEntry page = this.getNextStoryPage();
        this.setCurrTitle(page.title);
        this.prepareStoryPage(page);
        this.setCurrStoryPageAuthorName(page.authorName);
        this.incrementNumStoryPagesAlreadyDisplayed();
    }

    public boolean isThisStoryPageInADifferentStory() {
        final String prevTitle = this.getPrevTitle();
        final String currTitle = this.getCurrTitle();
        return (prevTitle == null || prevTitle.equals(currTitle) == false);
    }

    public boolean isCurrPageCreditedDifferently(final String penName) {
        final String authorName = this.getCurrStoryPageAuthorName();
        return (authorName != null && authorName.equals(penName) == false);
    }

    public boolean isFirstPage() {
        final int number = this.getAuthorPageNumber();
        return (number == 1);
    }

    public String getPrevPageNumber() {
        final int curr = this.getAuthorPageNumber();
        int prev = curr - 1;
        if (prev < 1) {
            prev = 1;
        }
        return Integer.toString(prev);
    }

    public void preparePrevAuthorPageLink() {
        final PageContext pageContext = this.getPageContext();
        final String authorId = this.getAuthorId();
        pageContext.setAttribute("id", authorId);
        final String prev = this.getPrevPageNumber();
        pageContext.setAttribute("prev", prev);
    }

    public int getNumPages() {
        final String authorId = this.getAuthorId();
        return StoryPage.countAllPagesWrittenBy(authorId);
    }

    public int getLastPageNumber() {
        final int numStories = this.getNumPages();
        return (numStories / NUM_PAGES_DISPLAYED) + 1;
    }

    public boolean isLastPage() {
        final int curr = this.getAuthorPageNumber();
        final int last = this.getLastPageNumber();
        return (curr == last);
    }

    public String getNextPageNumber() {
        final int curr = this.getAuthorPageNumber();
        int next = curr + 1;
        return Integer.toString(next);
    }

    public void prepareNextAuthorPageLink() {
        final PageContext pageContext = this.getPageContext();
        final String id = this.getAuthorId();
        pageContext.setAttribute("id", id);
        final String next = this.getNextPageNumber();
        pageContext.setAttribute("next", next);
    }

}