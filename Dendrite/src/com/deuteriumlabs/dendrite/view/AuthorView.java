package com.deuteriumlabs.dendrite.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.User;

/**
 * Presents a list of pages written by a particular user and presents user
 * preferences visible only to the matching user.
 */
public class AuthorView extends View {

    private static final int DEF_AUTHOR_PG_NUM = 1;

    private User author;
    private BibliographyView bibliographyView;
    private String id;

    public AuthorView() {
        this.initialiseBibilographyView();
        this.setAuthorPageNumber(DEF_AUTHOR_PG_NUM);
    }

    private User getAuthor() {
		return this.author;
	}
    
    public String getAuthorId() {
    	final User author = this.getAuthor();
    	return author.getId();
    }

    public int getAuthorAvatarId() {
        return author.getAvatarId();
    }

    private BibliographyView getBibiliographyView() {
        return bibliographyView;
    }

    public String getId() {
        return this.id;
    }

    public String getPenName() {
        final User user = this.author;
        final String penName = user.getDefaultPenName();
        return penName;
    }

    @Override
    String getUrl() {
        final String id = this.getId();
        return "/author?id=" + id;
    }

    public boolean hasAnotherStoryPage() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        return bibliographyView.hasAnotherStoryPage();
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
        view.setNumStoryPagesAlreadyDisplayed(0);
        view.setPrevTitle(null);
        view.setCurrTitle(null);
        this.setBibiliographyView(view);
    }

    public boolean isAuthorAvatarAvailable() {
        final User author = this.author;
        return author.isAvatarAvailable();
    }

    public boolean isAuthorPageOfUser() {
        final String myUserId = View.getMyUserId();
        final String id = this.getId();
        return id.equals(myUserId);
    }

    public boolean isFirstPage() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        return bibliographyView.isFirstPage();
    }

    public boolean isLastPage() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        return bibliographyView.isLastPage();
    }

    public boolean isThisStoryPageCreditedDifferently() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        final String penName = this.getPenName();
        return bibliographyView.isCurrPageCreditedDifferently(penName);
    }

    public boolean isThisStoryPageInADifferentStory() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        return bibliographyView.isThisStoryPageInADifferentStory();
    }

    public boolean isUserFollowingAuthor() {
    	final User author = this.getAuthor();
    	final List<String> followers = author.getFollowers();
    	final User myUser = User.getMyUser();
    	final String myUserId = myUser.getId();
    	return followers.contains(myUserId);
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
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.prepareNextAuthorPageLink();
    }

    public void prepareNextStoryPage() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.prepareNextStoryPage();
    }

    public void preparePrevAuthorPageLink() {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.preparePrevAuthorPageLink();
    }

    public void prepareTitle(final String title) {
        final PageContext pageContext = this.getPageContext();
        pageContext.setAttribute("title", title);
    }

    private void setAuthor(final User author) {
        this.author = author;
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

    public void setId(final String id) {
        this.id = id;
        final User user = new User();
        user.setId(id);
        user.read();
        this.setAuthor(user);
        final BibliographyView bibliographyView = this.getBibiliographyView();
        if (bibliographyView != null) {
            bibliographyView.setAuthorId(id);
        }
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.setPageContext(pageContext);
    }

    private void setPageIds(final List<String> pageIds) {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.setPageIds(pageIds);
    }
    
    public void setPrevTitle(final String prevTitle) {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.setPrevTitle(prevTitle);
    }

	private void setSummaries(final List<String> summaries) {
        final BibliographyView bibliographyView = this.getBibiliographyView();
        bibliographyView.setSummaries(summaries);
    }
}
