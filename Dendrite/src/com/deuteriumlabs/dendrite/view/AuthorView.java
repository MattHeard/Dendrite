package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.datastore.Text;

/**
 * Presents a list of pages written by a particular user and presents user
 * preferences visible only to the matching user.
 */
public class AuthorView extends View {
	
	private class StoryPageEntry {
		String authorName;
		String pageId;
		String summary;
		String title;
	}

	private static final String ELLIPSIS = "...";
	private static final int NUM_PAGES_DISPLAYED = 10;

	private static final int SUMMARY_LEN = 30;

	private static String summariseText(final Text text) {
		final String full = text.getValue();
		final int fullSize = full.length();
		if (fullSize < (SUMMARY_LEN - 3))
			return full;
		else {
			final String cropped = full.substring(0, (SUMMARY_LEN - 3));
			return cropped + ELLIPSIS;
		}
	}

	private List<String> authorNames;
	private BibliographyView bibliographyView;
	private String currStoryPageAuthorName;
	private String currTitle;
	private List<StoryPageEntry> entries;
	private String id;
	private List<String> pageIds;
	private String prevTitle;
	private List<String> summaries;

	private User user;

	public AuthorView() {
		this.initialiseBibilographyView();
		this.setAuthorPageNumber(1);
		this.setNumStoryPagesAlreadyDisplayed(0);
		this.setPrevTitle(null);
		this.setCurrTitle(null);
	}

	private void generateEntries() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final List<String> titles = bibliographyView.getTitles();
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

	public int getAuthorAvatarId() {
		final User author = this.getUser();
		return author.getAvatarId();
	}

	public List<String> getAuthorNames() {
		if (this.authorNames == null)
			this.readAuthorNames();
		return this.authorNames;
	}

	private BibliographyView getBibiliographyView() {
		return bibliographyView;
	}

	private String getCurrStoryPageAuthorName() {
		return this.currStoryPageAuthorName;
	}

	public String getCurrTitle() {
		return currTitle;
	}

	private List<StoryPageEntry> getEntries() {
		if (this.entries == null) {
			this.generateEntries();
		}
		return this.entries;
	}

	public String getId() {
		return this.id;
	}

	private int getLastPageNumber() {
		final int numberOfStories = this.getNumberOfPages();
		return (numberOfStories / NUM_PAGES_DISPLAYED) + 1;
	}

	public String getNextPageNumber() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int curr = bibliographyView.getAuthorPageNumber();
		int next = curr + 1;
		return Integer.toString(next);
	}

	private StoryPageEntry getNextStoryPage() {
		final List<StoryPageEntry> pages = this.getEntries();
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int index = bibliographyView.getNumStoryPagesAlreadyDisplayed();
		return pages.get(index);
	}

	private int getNumberOfPages() {
		final String authorId = this.getId();
		return StoryPage.countAllPagesWrittenBy(authorId);
	}

	// TODO: Extract into BibliographyView
	private int getNumStoryPagesToDisplay() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final List<String> titles = bibliographyView.getTitles();
		return titles.size();
	}

	public List<String> getPageIds() {
		if (this.pageIds == null)
			this.readPageIds();
		return this.pageIds;
	}

	public String getPenName() {
		final User user = this.getUser();
		final String penName = user.getDefaultPenName();
		return penName;
	}

	public String getPrevPageNumber() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int curr = bibliographyView.getAuthorPageNumber();
		int prev = curr - 1;
		if (prev < 1)
			prev = 1;
		return Integer.toString(prev);
	}

	public String getPrevTitle() {
		return prevTitle;
	}

	public List<String> getSummaries() {
		if (this.summaries == null)
			this.readSummaries();
		return this.summaries;
	}

	@Override
	String getUrl() {
		final String id = this.getId();
		return "/author.jsp?id=" + id;
	}

	private User getUser() {
		return this.user;
	}

	// TODO: Extract into BibliographyView
	public boolean hasAnotherStoryPage() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int numAlreadyDisplayed =
				bibliographyView.getNumStoryPagesAlreadyDisplayed();
		final int numToDisplay = this.getNumStoryPagesToDisplay();
		if (numAlreadyDisplayed < numToDisplay) {
			return true;
		} else {
			return false;
		}
	}

	private void incrementNumStoryPagesAlreadyDisplayed() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int num = bibliographyView.getNumStoryPagesAlreadyDisplayed();
		this.setNumStoryPagesAlreadyDisplayed(num + 1);
	}

	@Override
	public void initialise() {
		final HttpServletRequest request = this.getRequest();
		final String id = request.getParameter("id");
		this.setId(id);
		final String pParameter = request.getParameter("p");
		int authorPageNumber;
		try {
			authorPageNumber = Integer.parseInt(pParameter);
		} catch (NumberFormatException e) {
			authorPageNumber = 1;
		}
		this.setAuthorPageNumber(authorPageNumber);
		final String penName = this.getPenName();

		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("penName", penName);
		pageContext.setAttribute("webPageTitle", "Dendrite - " + penName);

		this.prepareAvatarId();
	}

	private void initialiseBibilographyView() {
		final BibliographyView view = new BibliographyView();
		this.setBibiliographyView(view);
	}

	public boolean isAuthorAvatarAvailable() {
		final User author = this.getUser();
		return author.isAvatarAvailable();
	}

	public boolean isAuthorPageOfUser() {
		final String myUserId = View.getMyUserId();
		final String id = this.getId();
		return id.equals(myUserId);
	}

	public boolean isFirstPage() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int number = bibliographyView.getAuthorPageNumber();
		return (number == 1);
	}

	public boolean isLastPage() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int curr = bibliographyView.getAuthorPageNumber();
		final int last = this.getLastPageNumber();
		return (curr == last);
	}

	public boolean isThisStoryPageCreditedDifferently() {
		final String authorName = this.getCurrStoryPageAuthorName();
		final String penName = this.getPenName();
		return (authorName != null && authorName.equals(penName) == false);
	}

	public boolean isThisStoryPageInADifferentStory() {
		final String prevTitle = this.getPrevTitle();
		final String currTitle = this.getCurrTitle();
		return (prevTitle == null || prevTitle.equals(currTitle) == false);
	}

	public void prepareAvatarId() {
		final int avatarId;
		if (this.isAuthorAvatarAvailable() == true) {
			avatarId = this.getAuthorAvatarId();
		} else {
			avatarId = 1;
		}
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("avatarId", avatarId);
	}

	public void prepareNextAuthorPageLink() {
		final PageContext pageContext = this.getPageContext();
		final String id = this.getId();
		pageContext.setAttribute("id", id);
		final String next = this.getNextPageNumber();
		pageContext.setAttribute("next", next);
	}

	public void prepareNextStoryPage() {
		this.savePrevTitle();
		final StoryPageEntry page = this.getNextStoryPage();
		this.setCurrTitle(page.title);
		this.prepareStoryPage(page);
		this.setCurrStoryPageAuthorName(page.authorName);
		this.incrementNumStoryPagesAlreadyDisplayed();
	}

	public void preparePrevAuthorPageLink() {
		final PageContext pageContext = this.getPageContext();
		final String id = this.getId();
		pageContext.setAttribute("id", id);
		final String prev = this.getPrevPageNumber();
		pageContext.setAttribute("prev", prev);
	}

	private void prepareStoryPage(final StoryPageEntry page) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("title", page.title);
		pageContext.setAttribute("summary", page.summary);
		pageContext.setAttribute("pageId", page.pageId);
		pageContext.setAttribute("authorName", page.authorName);
	}

	public void prepareTitle(final String title) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("title", title);
	}

	private void readAuthorNames() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final List<StoryPage> pages = bibliographyView.getPages();
		final List<String> authorNames = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final String authorName = page.getAuthorName();
			authorNames.add(authorName);
		}
		this.setAuthorNames(authorNames);
	}

	private void readPageIds() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final List<StoryPage> pages = bibliographyView.getPages();
		final List<String> pageIds = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final PageId id = page.getId();
			final String idString = id.toString();
			pageIds.add(idString);
		}
		this.setPageIds(pageIds);
	}

	private void readSummaries() {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final List<StoryPage> pages = bibliographyView.getPages();
		final List<String> summaries = new ArrayList<String>();
		for (final StoryPage page : pages) {
			final Text text = page.getText();
			final String summary = summariseText(text);
			summaries.add(summary);
		}
		this.setSummaries(summaries);
	}

	private void savePrevTitle() {
		final String currTitle = this.getCurrTitle();
		this.setPrevTitle(currTitle);
	}

	private void setAuthorNames(final List<String> authorNames) {
		this.authorNames = authorNames;
	}

	public void setAuthorPageNumber(final int authorPageNumber) {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		final int previousPageNumber = bibliographyView.getAuthorPageNumber();
		if (authorPageNumber > 1) {
			bibliographyView.setAuthorPageNumber(authorPageNumber);
		} else {
			bibliographyView.setDefaultAuthorPageNumber();
		}
		if (authorPageNumber != previousPageNumber) {
			bibliographyView.setTitles(null);
			this.setSummaries(null);
			this.setPageIds(null);
		}
	}

	private void setBibiliographyView(BibliographyView bibiliographyView) {
		this.bibliographyView = bibiliographyView;
	}

	private void setCurrStoryPageAuthorName(final String authorName) {
		this.currStoryPageAuthorName = authorName;
	}

	public void setCurrTitle(final String currTitle) {
		this.currTitle = currTitle;
	}

	private void setEntries(final List<StoryPageEntry> entries) {
		this.entries = entries;
	}

	public void setId(final String id) {
		this.id = id;
		final User user = new User();
		user.setId(id);
		user.read();
		this.setUser(user);
		final BibliographyView bibliographyView = this.getBibiliographyView();
		if (bibliographyView != null) {
			bibliographyView.setId(id);
		}
	}

	private void setNumStoryPagesAlreadyDisplayed(final int num) {
		final BibliographyView bibliographyView = this.getBibiliographyView();
		bibliographyView.setNumStoryPagesAlreadyDisplayed(num);
	}

	private void setPageIds(final List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setPrevTitle(final String prevTitle) {
		this.prevTitle = prevTitle;
	}

	private void setSummaries(final List<String> summaries) {
		this.summaries = summaries;
	}

	private void setUser(final User user) {
		this.user = user;
	}
}
