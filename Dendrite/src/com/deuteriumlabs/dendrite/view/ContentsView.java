/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.deuteriumlabs.dendrite.dependencies.Store;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class ContentsView extends View {
    public class Link {
        public String number;
        public PageId pageId;
        public String text;
        public String url;
        public String version;
    }

    private static final String BODY_MAIN_TITLE = "Table of Contents";
    private static final String CONTENTS_PAGE_NUMBER_PARAMETER_NAME = "p";
    private static final int DEFAULT_CONTENTS_PAGE_NUMBER = 1;
    private static final String FILTER_PARAMETER_NAME = "filter";
    private static final int NUM_STORIES_DISPLAYED = 10;

    // TODO OPTIMISE
    // This function is called once per contents page link and each call makes
    // a datastore query. This might be able to be optimised into a single
    // query.
    public static List<String> getTags(final Link link) {
        final StoryPage page = new StoryPage();
        page.setId(new PageId(link.number + link.version));
        page.read();
        return page.getTags();
    }

    private static String convertPageNumAndVersionToUrl(final String num,
            final String version) {
        return "/read?p=" + num + version;
    }

    private List<StoryBeginning> beginnings;
    private int contentsPageNumber;
    private String filter;
    private List<Link> links;
    private int numFilteredBeginnings;
    private Map<Integer, List<StoryPage>> pagesMap;

    public ContentsView() {
        setBeginnings(null);
        setContentsPageNumber(1);
    }

    // TODO: Create an abstract method in the parent `View` and implement in all
    // subclasses.
    public String getBodyMainTitle() {
        return BODY_MAIN_TITLE;
    }

    public List<Link> getLinks(final Store store, final DatastoreQuery query) {
        if (links == null) {
            final List<String> texts = getTitles(store, query);
            final List<String> numbers = getPageNumbers(store, query);
            final List<String> versions = getPageVersions(store, query);
            final List<String> urls = getUrls(numbers, versions);
            final int length = texts.size();
            links = new ArrayList<Link>();
            for (int i = 0; i < length; i++) {
                final Link link = new Link();
                link.url = urls.get(i);
                link.text = texts.get(i);
                link.number = numbers.get(i);
                link.version = versions.get(i);
                links.add(link);
            }
        }
        return links;
    }

    public String getNextPageNumber() {
        final int curr = getContentsPageNumber();
        final int next = curr + 1;
        return Integer.toString(next);
    }

    public String getPrevPageNumber() {
        final int curr = getContentsPageNumber();
        int prev = curr - 1;
        if (prev < 1) {
            prev = 1;
        }
        return Integer.toString(prev);
    }

    @Override
    public String getUrl() {
        return "/contents";
    }

    public List<String> getUrls(final List<String> numbers,
            final List<String> versions) {
        final List<String> urls = new ArrayList<String>();
        for (int i = 0; i < numbers.size(); i++) {
            final String number = numbers.get(i);
            final String version = versions.get(i);
            final String url = convertPageNumAndVersionToUrl(number, version);
            urls.add(url);
        }
        return urls;
    }

    @Override
    public void initialise() {
        final String webPageTitle = getWebPageTitle();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", webPageTitle);
        final HttpServletRequest req = getRequest();
        setContentsPageNumberFromRequest(req);
        setFilterFromRequest(req);
        final String bodyMainTitle = getBodyMainTitle();
        pageContext.setAttribute("bodyMainTitle", bodyMainTitle);
        numFilteredBeginnings = -1;
    }

    public boolean isFirstPage() {
        final int number = getContentsPageNumber();
        return (number == 1);
    }

    public boolean isLastPage() {
        final int curr = getContentsPageNumber();
        final int last = getLastPageNumber();
        return (curr == last);
    }

    public boolean isPastTheLastPage() {
        final int curr = getContentsPageNumber();
        final int last = getLastPageNumber();
        return (curr > last);
    }

    public void prepareLastPageLink() {
        final int numContentPages = getLastPageNumber();
        final String lastNum = Integer.toString(numContentPages);
        String link = "/contents?p=" + lastNum;
        if (isFiltered()) {
            link += "&filter=" + filter;
        }
        pageContext.setAttribute("lastLink", link);
    }

    public void prepareLink(final Link link) {
        final PageContext pageContext = getPageContext();
        final String url = link.url;
        pageContext.setAttribute("link", url);
        final String text = link.text;
        pageContext.setAttribute("title", text);
        final String number = link.number;
        pageContext.setAttribute("pageNumber", number);
    }
    
    public void prepareFirstPageLink() {
        String link = "/contents?p=1";
        if (isFiltered()) {
            link += "&filter=" + filter;
        }
        pageContext.setAttribute("firstLink", link);
    }

    public void prepareNextPageLink() {
        final PageContext pageContext = getPageContext();
        final String nextNum = getNextPageNumber();
        String link = "/contents?p=" + nextNum;
        if (isFiltered()) {
            link += "&filter=" + filter;
        }
        pageContext.setAttribute("nextLink", link);
    }

    public void preparePrevPageLink() {
        final PageContext pageContext = getPageContext();
        final String prevNum = getPrevPageNumber();
        String link = "/contents?p=" + prevNum;
        if (isFiltered()) {
            link += "&filter=" + filter;
        }
        pageContext.setAttribute("prevLink", link);
    }

    public void prepareRomanPageNum() {
        final int arabicPageNum = getContentsPageNumber();
        final String romanPageNum;
        if (arabicPageNum > 0) {
            romanPageNum = convertArabicNumToRoman(arabicPageNum);
        } else {
            romanPageNum = "?";
        }
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("romanPageNum", romanPageNum);
    }

    public void prepareTag(final String tag) {
        final PageContext pageContext = getPageContext();
        final String tagClass = tag.toLowerCase();
        pageContext.setAttribute("tagClass", tagClass);
        final String tagName = tag.toUpperCase();
        pageContext.setAttribute("tagName", tagName);
    }

    public void setContentsPageNumber(final int contentsPageNumber) {
        final int previousPageNum = getContentsPageNumber();
        if (contentsPageNumber > 1) {
            this.contentsPageNumber = contentsPageNumber;
        } else {
            this.contentsPageNumber = 1;
        }
        if (contentsPageNumber != previousPageNum) {
            setBeginnings(null);
        }
    }

    public void setContentsPageNumberFromRequest(final HttpServletRequest req) {
        final String parameterName = CONTENTS_PAGE_NUMBER_PARAMETER_NAME;
        final String parameter = req.getParameter(parameterName);
        final int number = getContentsPageNumberFromParameter(parameter);
        setContentsPageNumber(number);
    }

    private List<Integer> buildContentsPageBeginningNums(
            final Map<Integer, List<Integer>> storyMap) {
        final List<Integer> flattenedNums = new ArrayList<Integer>();
        for (final List<Integer> unflattenedNums : storyMap.values()) {
            for (int i = unflattenedNums.size() - 1; i >= 0; i--) {
                final int idNum = unflattenedNums.get(i);
                flattenedNums.add(0, idNum);
            }
        }

        setNumFilteredBeginnings(flattenedNums.size());

        final int first = getFirstIndex();
        int last = first + NUM_STORIES_DISPLAYED;
        if (last > flattenedNums.size()) {
            last = flattenedNums.size();
        }
        return flattenedNums.subList(first, last);
    }

    private Map<Integer, List<Integer>> buildStoryMap(
            final List<StoryPage> pages) {
        final Map<Integer, Integer> unsortedMap;
        unsortedMap = new TreeMap<Integer, Integer>();
        for (final StoryPage page : pages) {
            final int idNum = page.getId().getNumber();
            final int numLovers = page.getNumLovingUsers();
            Integer total = unsortedMap.remove(idNum);
            if (total != null) {
                total += numLovers;
            } else {
                total = numLovers;
            }
            unsortedMap.put(idNum, total);
        }

        final Map<Integer, List<Integer>> sortedMap = new TreeMap<>();
        for (final int idNum : unsortedMap.keySet()) {
            final int weight = unsortedMap.get(idNum);
            List<Integer> idNums = sortedMap.remove(weight);
            if (idNums == null) {
                idNums = new ArrayList<Integer>();
            }
            idNums.add(idNum);
            sortedMap.put(weight, idNums);
        }
        return sortedMap;
    }

    private String convertArabicNumToRoman(int arabicPageNum) {
        if (arabicPageNum < 1) {
            final String msg = "Input must be greater than 0.";
            throw new IllegalArgumentException(msg);
        }
        final int[] keys = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4,
                1 };
        final String[] vals = { "m", "cm", "d", "cd", "c", "xc", "l", "xl", "x",
                "ix", "v", "iv", "i" };
        String romanPageNum = "";
        for (int i = 0; i < keys.length; i++) {
            while ((arabicPageNum / keys[i]) > 0) {
                romanPageNum += vals[i];
                arabicPageNum -= keys[i];
            }
        }
        return romanPageNum;
    }

    private int countFilteredBeginnings() {
        return StoryPage.countFirstPagesMatchingTag(filter);
    }

    private List<StoryBeginning> getBeginnings(final Store store,
            final DatastoreQuery query) {
        if (beginnings == null) {
            readBeginnings(store, query);
        }
        return beginnings;
    }

    private int getContentsPageNumber() {
        return contentsPageNumber;
    }

    private int getContentsPageNumberFromParameter(final String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (final NumberFormatException e) {
            return DEFAULT_CONTENTS_PAGE_NUMBER;
        }
    }

    private List<StoryBeginning> getFilteredBeginnings() {
        final String tag = filter;
        final List<StoryPage> pages = StoryPage.getFirstPagesMatchingTag(tag);
        setFirstPagesMatchingTag(pages);

        final Map<Integer, List<Integer>> storyMap = buildStoryMap(pages);

        final List<Integer> storyBeginningNums;
        storyBeginningNums = buildContentsPageBeginningNums(storyMap);

        final List<StoryBeginning> beginnings = new ArrayList<StoryBeginning>();
        for (final int num : storyBeginningNums) {
            final StoryBeginning beginning = new StoryBeginning();
            beginning.setPageNumber(num);
            beginning.read();
            beginnings.add(beginning);
        }
        return beginnings;
    }

    private int getFirstIndex() {
        return (getContentsPageNumber() - 1) * NUM_STORIES_DISPLAYED;
    }

    private int getLastPageNumber() {
        final int numberOfStories = getNumberOfStories();
        if (numberOfStories == 0) {
            return 1;
        } else {
            return ((numberOfStories - 1) / NUM_STORIES_DISPLAYED) + 1;
        }
    }

    private int getNumberOfStories() {
        final boolean isFiltered = isFiltered();
        if (isFiltered) {
            return getNumFilteredBeginnings();
        } else {
            return StoryBeginning.countAllBeginnings();
        }
    }

    private int getNumFilteredBeginnings() {
        if (numFilteredBeginnings < 0) {
            numFilteredBeginnings = countFilteredBeginnings();
        }
        return numFilteredBeginnings;
    }

    private List<String> getPageNumbers(final Store store,
            final DatastoreQuery query) {
        final List<String> numbers = new ArrayList<String>();
        final List<StoryBeginning> beginnings = getBeginnings(store, query);
        for (final StoryBeginning beginning : beginnings) {
            final int numberValue = beginning.getPageNumber();
            final String numberString = Integer.toString(numberValue);
            numbers.add(numberString);
        }
        return numbers;
    }

    private List<String> getPageVersions(final Store store,
            final DatastoreQuery query) {
        final List<String> versions = new ArrayList<String>();
        final List<String> numbers = getPageNumbers(store, query);
        for (final String number : numbers) {
            final String version = specifyVersion(number);
            versions.add(version);
        }
        return versions;
    }

    private List<StoryPage> getPagesList(final int number) {
        return pagesMap.get(number);
    }

    private List<String> getTitles(final Store store,
            final DatastoreQuery query) {
        final List<String> titles = new ArrayList<String>();
        final List<StoryBeginning> beginnings = getBeginnings(store, query);
        for (final StoryBeginning beginning : beginnings) {
            final String title = beginning.getTitle();
            titles.add(title);
        }
        return titles;
    }

    private boolean isFiltered() {
        return (filter != null) && (filter.length() > 0);
    }

    private void readBeginnings(final Store store, final DatastoreQuery query) {
        final List<StoryBeginning> beginnings;
        if (isFiltered() == false) {
            final int first = getFirstIndex();
            final int last = first + NUM_STORIES_DISPLAYED;
            beginnings = StoryBeginning.getBeginnings(first, last, store,
                    query);
        } else {
            beginnings = getFilteredBeginnings();
        }
        setBeginnings(beginnings);
    }

    private String selectByWeight(final List<StoryPage> pages) {
        int totalWeight = 0;
        for (final StoryPage page : pages) {
            totalWeight += page.getNumLovingUsers();
        }
        totalWeight += pages.size();

        final Random generator = new Random();
        int randomNum = generator.nextInt(totalWeight) + 1;
        int i = 0;
        while ((randomNum > 0) && (i < pages.size())) {
            final StoryPage page = pages.get(i);
            final int weight = page.getNumLovingUsers() + 1;

            randomNum -= weight;
            if (randomNum <= 0) {
                return page.getId().getVersion();
            }
            i++;
        }

        if (pages.size() > 0) {
            return pages.get(0).getId().getVersion();
        } else {
            return "a";
        }
    }

    private void setBeginnings(final List<StoryBeginning> beginnings) {
        this.beginnings = beginnings;
    }

    private void setFilter(final String filter) {
        this.filter = (filter == null ? "" : filter.toLowerCase());
    }

    private void setFilterFromRequest(final HttpServletRequest req) {
        final String parameterName = FILTER_PARAMETER_NAME;
        final String filter = req.getParameter(parameterName);
        setFilter(filter);
    }

    private void setFirstPagesMatchingTag(final List<StoryPage> pages) {
        final Map<Integer, List<StoryPage>> map;
        map = new TreeMap<Integer, List<StoryPage>>();

        for (final StoryPage page : pages) {
            final int idNum = page.getId().getNumber();
            List<StoryPage> alts = map.remove(idNum);
            if (alts == null) {
                alts = new ArrayList<StoryPage>();
            }
            alts.add(page);
            map.put(idNum, alts);
        }

        setPagesMap(map);
    }

    private void setNumFilteredBeginnings(final int size) {
        numFilteredBeginnings = size;
    }

    private void setPagesMap(final Map<Integer, List<StoryPage>> map) {
        pagesMap = map;
    }

    private String specifyVersion(final String number) {
        final boolean isFiltered = isFiltered();
        if (isFiltered == false) {
            final PageId id = new PageId(number);
            final Random generator = new Random();
            final String version = StoryPage.getRandomVersion(id, generator);
            return version;
        } else {
            final int num = Integer.parseInt(number);
            final List<StoryPage> pages = getPagesList(num);
            final String version = selectByWeight(pages);
            return version;
        }
    }
}
