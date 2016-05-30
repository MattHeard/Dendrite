/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.User;

/**
 * Presents a list of pages written by a particular user and presents user
 * preferences visible only to the matching user.
 */
public class FollowersView extends View {

    private User author;
    private String id;

    public int getAuthorAvatarId() {
        return author.getAvatarId();
    }

    public String getAuthorId() {
        final User author = getAuthor();
        return author.getId();
    }

    public List<String> getFollowerIds() {
        final User author = getAuthor();
        final List<String> ids = author.getFollowers();
        if (ids == null) {
            return new ArrayList<String>();
        }
        return ids;
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
        return "/followers?id=" + id;
    }

    @Override
    public void initialise() {
        final HttpServletRequest request = getRequest();
        final String id = request.getParameter("id");
        setId(id);
        final String penName = getPenName();

        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("penName", penName);
        pageContext.setAttribute("webPageTitle", "Dendrite - " + penName);

        prepareAvatarId();
    }

    public boolean isAuthorAvatarAvailable() {
        final User author = this.author;
        return author.isAvatarAvailable();
    }

    public boolean isAuthorPageOfUser() {
        final String myUserId = View.getMyUserId();
        final String id = getId();
        return id.equals(myUserId);
    }

    public boolean isUserFollowingAuthor() {
        final User author = getAuthor();
        final List<String> followers = author.getFollowers();
        final String myUserId = User.getMyUserId();
        return followers.contains(myUserId);
    }

    public void prepareAuthorId() {
        final PageContext pageContext = getPageContext();
        final String id = getAuthorId();
        pageContext.setAttribute("authorId", id);
    }

    public void prepareAuthorPageUrl() {
        final PageContext pageContext = getPageContext();
        final String id = getId();
        final String url = "/author?id=" + id;
        pageContext.setAttribute("authorPageUrl", url);
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

    public void prepareFollower(final String id) {
        final User follower = new User();
        follower.setId(id);
        follower.read();
        final String name = follower.getDefaultPenName();

        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("followerName", name);

        final String url = "/author?id=" + id;
        pageContext.setAttribute("followerProfileUrl", url);

        final int avatarId = follower.getAvatarId();
        pageContext.setAttribute("followerAvatarId", avatarId);

        final String avatarDesc = User.getAvatarDesc(avatarId);
        pageContext.setAttribute("followerAvatarDesc", avatarDesc);
    }

    public void prepareTitle(final String title) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("title", title);
    }

    public void setId(final String id) {
        this.id = id;
        final User user = new User();
        user.setId(id);
        user.read();
        setAuthor(user);
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
    }

    private User getAuthor() {
        return author;
    }

    private void setAuthor(final User author) {
        this.author = author;
    }
}
