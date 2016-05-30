/* © 2013-2015 Deuterium Labs Limited */
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

    private boolean isAuthorValid;

    private boolean isInvalidPgNum;

    public AuthorView() {
        initialiseBibilographyView();
        setAuthorPageNumber(DEF_AUTHOR_PG_NUM);
    }

    public int getAuthorAvatarId() {
        return author.getAvatarId();
    }

    public String getAuthorId() {
        final User author = getAuthor();
        return author.getId();
    }

    public String getId() {
        return id;
    }

    public String getPenName() {
        final User user = author;
        final String penName = user.getDefaultPenName();
        return penName;
    }

    @Override
    public String getUrl() {
        final String id = getId();
        return "/author?id=" + id;
    }

    public boolean hasAnotherStoryPage() {
        final BibliographyView bibliographyView = getBibiliographyView();
        return bibliographyView.hasAnotherStoryPage();
    }

    @Override
    public void initialise() {
        final HttpServletRequest request = getRequest();
        final String id = request.getParameter("id");
        if (id != null) {
            setId(id);
        } else {
            final String myUserId = User.getMyUserId();
            setId(myUserId);
        }

        extractAuthorPgNum();

        final String penName = getPenName();

        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("penName", penName);
        pageContext.setAttribute("webPageTitle", "Dendrite - " + penName);

        prepareAvatarId();
        prepareAvatarDesc();

        super.initialise();
    }

    public boolean isAuthorAvatarAvailable() {
        return author.isAvatarAvailable();
    }

    public boolean isAuthorPageOfUser() {
        final String myUserId = View.getMyUserId();
        final String id = getId();
        return id.equals(myUserId);
    }

    public boolean isAuthorValid() {
        return isAuthorValid;
    }

    public boolean isFirstPage() {
        final BibliographyView bibliographyView = getBibiliographyView();
        return bibliographyView.isFirstPage();
    }

    public boolean isInvalidPgNum() {
        return isInvalidPgNum;
    }

    public boolean isLastPage() {
        final BibliographyView bibliographyView = getBibiliographyView();
        return bibliographyView.isLastPage();
    }

    public boolean isThisStoryPageCreditedDifferently() {
        final BibliographyView bibliographyView = getBibiliographyView();
        final String penName = getPenName();
        return bibliographyView.isCurrPageCreditedDifferently(penName);
    }

    public boolean isThisStoryPageInADifferentStory() {
        final BibliographyView bibliographyView = getBibiliographyView();
        return bibliographyView.isThisStoryPageInADifferentStory();
    }

    public boolean isUserFollowingAuthor() {
        final User author = getAuthor();
        final List<String> followers = author.getFollowers();
        if (followers != null) {
            final String myUserId = User.getMyUserId();
            return followers.contains(myUserId);
        } else {
            return false;
        }
    }

    public void prepareAuthorId() {
        final PageContext pageContext = getPageContext();
        final String id = getAuthorId();
        pageContext.setAttribute("authorId", id);
    }

    public void prepareAvatarId() {
        final int avatarId;
        if (isAuthorAvatarAvailable() == true) {
            avatarId = getAuthorAvatarId();
        } else {
            avatarId = 1;
        }
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("avatarId", avatarId);
    }

    public void prepareFollowersPageUrl() {
        final PageContext pageContext = getPageContext();
        final String id = getId();
        final String url = "/followers?id=" + id;
        pageContext.setAttribute("followersPageUrl", url);
    }

    public void prepareNextAuthorPageLink() {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.prepareNextAuthorPageLink();
    }

    public void prepareNextStoryPage() {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.prepareNextStoryPage();
    }

    public void preparePg1Url() {
        final PageContext pageContext = getPageContext();
        final String url = getUrl();
        pageContext.setAttribute("pg1Url", url);
    }

    public void preparePrevAuthorPageLink() {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.preparePrevAuthorPageLink();
    }

    public void prepareTitle(final String title) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("title", title);
    }

    public void setAuthorPageNumber(final int authorPageNumber) {
        final BibliographyView bibliographyView = getBibiliographyView();
        final int previousPageNumber = bibliographyView.getAuthorPageNumber();
        if (authorPageNumber > 1) {
            bibliographyView.setAuthorPageNumber(authorPageNumber);
        } else {
            bibliographyView.setDefaultAuthorPageNumber();
        }
        if (authorPageNumber != previousPageNumber) {
            bibliographyView.setTitles(null);
            setSummaries(null);
            setPageIds(null);
        }
    }

    public void setId(final String id) {
        this.id = id;
        final User author = new User();
        author.setId(id);

        final boolean isInStore = author.isInStore();
        isAuthorValid = isInStore;

        author.read();
        setAuthor(author);
        final BibliographyView bibliographyView = getBibiliographyView();
        if (bibliographyView != null) {
            bibliographyView.setAuthorId(id);
        }
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.setPageContext(pageContext);
    }

    public void setPrevTitle(final String prevTitle) {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.setPrevTitle(prevTitle);
    }

    private void extractAuthorPgNum() {
        final HttpServletRequest req = getRequest();
        final String pParameter = req.getParameter("p");
        int authorPageNumber;
        try {
            authorPageNumber = Integer.parseInt(pParameter);
        } catch (final NumberFormatException e) {
            authorPageNumber = 1;
        }
        final int lastPgNum = getLastPgNum();
        if (authorPageNumber > lastPgNum) {
            isInvalidPgNum = true;
        } else {
            isInvalidPgNum = false;
        }
        setAuthorPageNumber(authorPageNumber);
    }

    private User getAuthor() {
        return author;
    }

    private BibliographyView getBibiliographyView() {
        return bibliographyView;
    }

    private int getLastPgNum() {
        return bibliographyView.getLastPageNumber();
    }

    private void initialiseBibilographyView() {
        final BibliographyView view = new BibliographyView();
        view.setNumStoryPagesAlreadyDisplayed(0);
        view.setPrevTitle(null);
        view.setCurrTitle(null);
        setBibiliographyView(view);
    }

    private void prepareAvatarDesc() {
        final int id = getAuthorAvatarId();
        final String desc = User.getAvatarDesc(id);
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("avatarDesc", desc);
    }

    private void setAuthor(final User author) {
        this.author = author;
    }

    private void setBibiliographyView(
            final BibliographyView bibiliographyView) {
        bibliographyView = bibiliographyView;
    }

    private void setPageIds(final List<String> pageIds) {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.setPageIds(pageIds);
    }

    private void setSummaries(final List<String> summaries) {
        final BibliographyView bibliographyView = getBibiliographyView();
        bibliographyView.setSummaries(summaries);
    }

    @Override
    protected String getMetaDesc() {
        final String name = getPenName();
        return name + " is an author on Dendrite.";
    }

}
