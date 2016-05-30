/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class BibliographyView {

    public class StoryPageEntry {
        String authorName;
        String pageId;
        String summary;
        String title;
    }

    private static final int DEFAULT_AUTHOR_PAGE_NUM = 1;
    private static final int NUM_PAGES_DISPLAYED = 10;
    private String authorId;
    private List<String> authorNames, titles, pageIds, summaries;
    private int authorPageNumber;
    private String currStoryPageAuthorName;
    private String currTitle, prevTitle;
    private List<StoryPageEntry> entries;
    private int numStoryPagesAlreadyDisplayed;
    private PageContext pageContext;
    private List<StoryPage> pages;

    public void generateEntries() {
        final List<String> titles = getTitles();
        final int length = titles.size();
        final List<String> summaries = getSummaries();
        final List<String> pageIds = getPageIds();
        final List<String> authorNames = getAuthorNames();

        final List<StoryPageEntry> pages = new ArrayList<StoryPageEntry>();
        for (int i = 0; i < length; i++) {
            final StoryPageEntry page = new StoryPageEntry();
            page.title = titles.get(i);
            page.summary = summaries.get(i);
            page.pageId = pageIds.get(i);
            page.authorName = authorNames.get(i);
            pages.add(page);
        }

        setEntries(pages);
    }

    public String getAuthorId() {
        return authorId;
    }

    public List<String> getAuthorNames() {
        if (authorNames == null) {
            readAuthorNames();
        }
        return authorNames;
    }

    public int getAuthorPageNumber() {
        return authorPageNumber;
    }

    public String getCurrStoryPageAuthorName() {
        return currStoryPageAuthorName;
    }

    public String getCurrTitle() {
        return currTitle;
    }

    public List<StoryPageEntry> getEntries() {
        if (entries == null) {
            generateEntries();
        }
        return entries;
    }

    public int getFirstIndex() {
        final int authorPageNumber = getAuthorPageNumber();
        return (authorPageNumber - 1) * NUM_PAGES_DISPLAYED;
    }

    public int getLastIndex() {
        final int firstIndex = getFirstIndex();
        return firstIndex + NUM_PAGES_DISPLAYED;
    }

    public int getLastPageNumber() {
        final int numStories = getNumPages();
        int lastPgNum = numStories / NUM_PAGES_DISPLAYED;
        if ((numStories % NUM_PAGES_DISPLAYED) != 0) {
            lastPgNum++;
        }
        if (lastPgNum < 1) {
            lastPgNum = 1;
        }
        return lastPgNum;
    }

    public String getNextPageNumber() {
        final int curr = getAuthorPageNumber();
        final int next = curr + 1;
        return Integer.toString(next);
    }

    public StoryPageEntry getNextStoryPage() {
        final List<StoryPageEntry> pages = getEntries();
        final int index = getNumStoryPagesAlreadyDisplayed();
        return pages.get(index);
    }

    public int getNumPages() {
        final String authorId = getAuthorId();
        return StoryPage.countAllPagesWrittenBy(authorId);
    }

    public int getNumStoryPagesAlreadyDisplayed() {
        return numStoryPagesAlreadyDisplayed;
    }

    public int getNumStoryPagesToDisplay() {
        final List<String> titles = getTitles();
        return titles.size();
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public List<String> getPageIds() {
        if (pageIds == null) {
            readPageIds();
        }
        return pageIds;
    }

    public List<StoryPage> getPages() {
        if (pages == null) {
            readPages();
        }
        return pages;
    }

    public String getPrevPageNumber() {
        final int curr = getAuthorPageNumber();
        int prev = curr - 1;
        if (prev < 1) {
            prev = 1;
        }
        return Integer.toString(prev);
    }

    public String getPrevTitle() {
        return prevTitle;
    }

    public List<String> getSummaries() {
        if (summaries == null) {
            readSummaries();
        }
        return summaries;
    }

    public List<String> getTitles() {
        if (titles == null) {
            readTitles();
        }
        return titles;
    }

    public boolean hasAnotherStoryPage() {
        final int numAlreadyDisplayed = getNumStoryPagesAlreadyDisplayed();
        final int numToDisplay = getNumStoryPagesToDisplay();
        return (numAlreadyDisplayed < numToDisplay);
    }

    public void incrementNumStoryPagesAlreadyDisplayed() {
        final int num = getNumStoryPagesAlreadyDisplayed();
        setNumStoryPagesAlreadyDisplayed(num + 1);
    }

    public boolean isCurrPageCreditedDifferently(final String penName) {
        final String authorName = getCurrStoryPageAuthorName();
        return ((authorName != null) && (authorName.equals(penName) == false));
    }

    public boolean isFirstPage() {
        final int number = getAuthorPageNumber();
        return (number == 1);
    }

    public boolean isLastPage() {
        final int curr = getAuthorPageNumber();
        final int last = getLastPageNumber();
        return (curr == last);
    }

    public boolean isThisStoryPageInADifferentStory() {
        final String prevTitle = getPrevTitle();
        final String currTitle = getCurrTitle();
        return ((prevTitle == null) || (prevTitle.equals(currTitle) == false));
    }

    public void prepareNextAuthorPageLink() {
        final PageContext pageContext = getPageContext();
        final String id = getAuthorId();
        pageContext.setAttribute("id", id);
        final String next = getNextPageNumber();
        pageContext.setAttribute("next", next);
    }

    public void prepareNextStoryPage() {
        savePrevTitle();
        final StoryPageEntry page = getNextStoryPage();
        setCurrTitle(page.title);
        prepareStoryPage(page);
        setCurrStoryPageAuthorName(page.authorName);
        incrementNumStoryPagesAlreadyDisplayed();
    }

    public void preparePrevAuthorPageLink() {
        final PageContext pageContext = getPageContext();
        final String authorId = getAuthorId();
        pageContext.setAttribute("id", authorId);
        final String prev = getPrevPageNumber();
        pageContext.setAttribute("prev", prev);
    }

    public void prepareStoryPage(final StoryPageEntry page) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("title", page.title);
        pageContext.setAttribute("summary", page.summary);
        pageContext.setAttribute("pageId", page.pageId);
        pageContext.setAttribute("authorName", page.authorName);
    }

    public void readAuthorNames() {
        final List<StoryPage> pages = getPages();
        final List<String> authorNames = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final String authorName = page.getAuthorName();
            authorNames.add(authorName);
        }
        setAuthorNames(authorNames);
    }

    public void readPageIds() {
        final List<StoryPage> pages = getPages();
        final List<String> pageIds = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final PageId id = page.getId();
            final String idString = id.toString();
            pageIds.add(idString);
        }
        setPageIds(pageIds);
    }

    public void readPages() {
        final int firstIndex = getFirstIndex();
        final int lastIndex = getLastIndex();
        final List<StoryPage> pages;
        final String authorId = getAuthorId();
        pages = StoryPage.getPagesWrittenBy(authorId, firstIndex, lastIndex);
        setPages(pages);
    }

    public void readSummaries() {
        final List<StoryPage> pages = getPages();
        final List<String> summaries = new ArrayList<String>();
        for (final StoryPage page : pages) {
            final String summary = getSummary(page);
            summaries.add(summary);
        }
        setSummaries(summaries);
    }

    public void readTitles() {
        final List<StoryPage> pages = getPages();
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
        setTitles(titles);
    }

    public void savePrevTitle() {
        final String currTitle = getCurrTitle();
        setPrevTitle(currTitle);
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorNames(final List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public void setAuthorPageNumber(final int authorPageNumber) {
        this.authorPageNumber = authorPageNumber;
    }

    public void setCurrStoryPageAuthorName(
            final String currStoryPageAuthorName) {
        this.currStoryPageAuthorName = currStoryPageAuthorName;
    }

    public void setCurrTitle(final String currTitle) {
        this.currTitle = currTitle;
    }

    public void setDefaultAuthorPageNumber() {
        setAuthorPageNumber(DEFAULT_AUTHOR_PAGE_NUM);
    }

    public void setEntries(final List<StoryPageEntry> entries) {
        this.entries = entries;
    }

    public void setNumStoryPagesAlreadyDisplayed(final int num) {
        numStoryPagesAlreadyDisplayed = num;
    }

    public void setPageContext(final PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void setPageIds(final List<String> pageIds) {
        this.pageIds = pageIds;
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

    private String getSummary(final StoryPage page) {
        return page.getSummary();
    }

}