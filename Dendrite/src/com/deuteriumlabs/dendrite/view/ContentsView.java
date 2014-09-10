package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * Represents the table of contents from which the user can select a story to
 * read.
 */
public class ContentsView extends View {

    public class Link {
        public String number;
        public String text;
        public String url;
        public String version;
    }

    private static final String BODY_MAIN_TITLE = "Table of Contents";
    private static final String CONTENTS_PAGE_NUMBER_PARAMETER_NAME = "p";
    private static final int DEFAULT_CONTENTS_PAGE_NUMBER = 1;
    private static final int NUM_STORIES_DISPLAYED = 10;

    private static String convertPgNumAndVersionToUrl(final String num,
			final String version) {
		return "/read?p=" + num + version;
	}
    
    public static List<String> getTags(final Link link) {
    	final String num = link.number;
    	final String version = link.version;
    	final PageId id = new PageId(num + version);
    	final StoryPage pg = new StoryPage();
    	pg.setId(id);
    	pg.read();
    	return pg.getTags();
    }
    private List<StoryBeginning> beginnings;

    private int contentsPageNumber;

    private List<Link> links;

    /**
     * Default constructor. Sets the default page number to 1, which displays
     * the first page of story beginnings.
     */
    public ContentsView() {
        this.setBeginnings(null);
        this.setContentsPageNumber(1);
    }

    /**
     * Returns the list of all beginnings on this page of contents.
     * 
     * @return The list of all beginnings on this page of contents
     */
    private List<StoryBeginning> getBeginnings() {
        if (this.beginnings == null)
            this.readBeginnings();
        return this.beginnings;
    }

    // TODO: Create an abstract method in the parent `View` and implement in all
    // subclasses.
    public String getBodyMainTitle() {
        return BODY_MAIN_TITLE;
    }

    /**
     * Returns which page of beginnings are currently being displayed.
     * 
     * @return The page number of contents currently being displayed
     */
    private int getContentsPageNumber() {
        return this.contentsPageNumber;
    }

    private int getContentsPageNumberFromParameter(final String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            return DEFAULT_CONTENTS_PAGE_NUMBER;
        }
    }

    private int getLastPageNumber() {
        final int numberOfStories = this.getNumberOfStories();
        if (numberOfStories == 0)
            return 1;
        else
            return ((numberOfStories - 1) / NUM_STORIES_DISPLAYED) + 1;
    }

    public List<Link> getLinks() {
        if (this.links == null) {
            final List<String> texts = this.getTitles();
            final List<String> numbers = this.getPageNumbers();
            final List<String> versions = this.getPageVersions();
            final List<String> urls = this.getUrls(numbers, versions);
            final int length = texts.size();

            final List<Link> links = new ArrayList<Link>();
            for (int i = 0; i < length; i++) {
                final Link link = new Link();
                link.url = urls.get(i);
                link.text = texts.get(i);
                link.number = numbers.get(i);
                link.version = versions.get(i);
                links.add(link);
            }

            this.setLinks(links);
        }

        return this.links;
    }

    public String getNextPageNumber() {
        final int curr = this.getContentsPageNumber();
        int next = curr + 1;
        return Integer.toString(next);
    }

    private int getNumberOfStories() {
        return StoryBeginning.countAllBeginnings();
    }

    /**
     * Returns the list of page numbers of all beginnings on this page of
     * contents.
     * 
     * @return The list of page numbers of all beginnings on this page of
     *         contents
     */
    public List<String> getPageNumbers() {
        final List<String> numbers = new ArrayList<String>();
        List<StoryBeginning> beginnings = this.getBeginnings();
        for (final StoryBeginning beginning : beginnings) {
            final int numberValue = beginning.getPageNumber();
            final String numberString = Integer.toString(numberValue);
            numbers.add(numberString);
        }
        return numbers;
    }

    private List<String> getPageVersions() {
		final List<String> versions = new ArrayList<String>();
		List<String> numbers = this.getPageNumbers();
		for (final String number : numbers) {
			final String version = specifyVersion(number);
			versions.add(version);
		}
		return versions;
	}

    public String getPrevPageNumber() {
        final int curr = this.getContentsPageNumber();
        int prev = curr - 1;
        if (prev < 1)
            prev = 1;
        return Integer.toString(prev);
    }

	/**
     * Returns the list of titles of all beginnings on this page of contents.
     * 
     * @return The list of titles of all beginnings on this page of contents
     */
    public List<String> getTitles() {
        final List<String> titles = new ArrayList<String>();
        List<StoryBeginning> beginnings = this.getBeginnings();
        for (final StoryBeginning beginning : beginnings) {
            final String title = beginning.getTitle();
            titles.add(title);
        }
        return titles;
    }

	/*
     * (non-Javadoc)
     * 
     * @see com.deuteriumlabs.dendrite.view.View#getUrl()
     */
    @Override
    String getUrl() {
        return "/";
    }

	/**
     * Returns the list of links to all beginnings on this page of contents.
	 * @param versions 
	 * @param numbers 
     * 
     * @return The list of links to all beginnings on this page of contents
     */
    public List<String> getUrls(List<String> numbers, List<String> versions) {
        final List<String> urls = new ArrayList<String>();
        for (int i = 0; i < numbers.size(); i++) {
        	final String number = numbers.get(i);
        	final String version = versions.get(i);
        	final String url = convertPgNumAndVersionToUrl(number, version);
        	urls.add(url);
        }
        return urls;
    }

    @Override
    public void initialise() {
        final String webPageTitle = this.getWebPageTitle();
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("webPageTitle", webPageTitle);
        final HttpServletRequest request = this.getRequest();
        this.setContentsPageNumberFromRequest(request);
        final String bodyMainTitle = this.getBodyMainTitle();
        pageContext.setAttribute("bodyMainTitle", bodyMainTitle);
    }

    public boolean isFirstPage() {
        final int number = this.getContentsPageNumber();
        return (number == 1);
    }

    public boolean isLastPage() {
        final int curr = this.getContentsPageNumber();
        final int last = this.getLastPageNumber();
        return (curr == last);
    }

    public void prepareLink(final Link link) {
        final PageContext pageContext = this.getPageContext();
        final String url = link.url;
        pageContext.setAttribute("link", url);
        final String text = link.text;
        pageContext.setAttribute("title", text);
        final String number = link.number;
        pageContext.setAttribute("pageNumber", number);
    }

    public void prepareNextPageNum() {
        final PageContext pageContext = this.getPageContext();
        final String next = this.getNextPageNumber();
        pageContext.setAttribute("next", next);
    }

    public void preparePrevPageNum() {
        final PageContext pageContext = this.getPageContext();
        final String prev = this.getPrevPageNumber();
        pageContext.setAttribute("prev", prev);
    }

    public void prepareTag(final String tag) {
    	final PageContext pageContext = this.getPageContext();
    	final String tagClass = tag.toLowerCase();
    	pageContext.setAttribute("tagClass", tagClass);
    	final String tagName = tag.toUpperCase();
    	pageContext.setAttribute("tagName", tagName);
    }

    /**
     * Loads a page of beginnings from the datastore. The beginnings are
     * paginated so that a limited number of beginnings are displayed at one
     * time.
     */
    private void readBeginnings() {
        final int contentsPageNumber = this.getContentsPageNumber();
        final int firstIndex = (contentsPageNumber - 1) * NUM_STORIES_DISPLAYED;
        final int lastIndex = firstIndex + NUM_STORIES_DISPLAYED;
        final List<StoryBeginning> beginnings;
        beginnings = StoryBeginning.getBeginnings(firstIndex, lastIndex);
        this.setBeginnings(beginnings);
    }

    /**
     * Sets the list of beginnings to display.
     * 
     * @param beginnings
     *            The list of beginnings to display
     */
    private void setBeginnings(final List<StoryBeginning> beginnings) {
        this.beginnings = beginnings;
    }

    /**
     * Sets the current contents page number. The page number must be positive.
     * If the page number is changed successfully, the cached list of beginnings
     * is nulled so that the new page is loaded the next time the getter for the
     * beginnings is called.
     * 
     * @param contentsPageNumber
     *            The contents page number to change to
     */
    public void setContentsPageNumber(final int contentsPageNumber) {
        final int previousPageNum = this.getContentsPageNumber();
        if (contentsPageNumber > 1)
            this.contentsPageNumber = contentsPageNumber;
        else
            this.contentsPageNumber = 1;
        if (contentsPageNumber != previousPageNum)
            this.setBeginnings(null);
    }

    public void setContentsPageNumberFromRequest(final HttpServletRequest req) {
        final String parameterName = CONTENTS_PAGE_NUMBER_PARAMETER_NAME;
        final String parameter = req.getParameter(parameterName);
        final int number = getContentsPageNumberFromParameter(parameter);
        this.setContentsPageNumber(number);
    }
    
    private void setLinks(final List<Link> links) {
        this.links = links;
    }
    
    private String specifyVersion(final String number) {
		final PageId id = new PageId(number);
		final String version = StoryPage.getRandomVersion(id);
		return version;
	}
}
