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

	private User getAuthor() {
		return this.author;
	}

	public int getAuthorAvatarId() {
		return author.getAvatarId();
	}

	public String getAuthorId() {
		final User author = this.getAuthor();
		return author.getId();
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
		return "/followers?id=" + id;
	}

	@Override
	public void initialise() {
		final HttpServletRequest request = this.getRequest();
		final String id = request.getParameter("id");
		this.setId(id);
		final String penName = this.getPenName();

		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("penName", penName);
		pageContext.setAttribute("webPageTitle", "Dendrite - " + penName);

		this.prepareAvatarId();
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

	public boolean isUserFollowingAuthor() {
		final User author = this.getAuthor();
		final List<String> followers = author.getFollowers();
		final User myUser = User.getMyUser();
		final String myUserId = myUser.getId();
		return followers.contains(myUserId);
	}

	public void prepareAuthorPageUrl() {
		final PageContext pageContext = this.getPageContext();
		final String id = this.getId();
		String url = "/author?id=" + id;
		pageContext.setAttribute("authorPageUrl", url);
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

	public void prepareTitle(final String title) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("title", title);
	}

	private void setAuthor(final User author) {
		this.author = author;
	}

	public void setId(final String id) {
		this.id = id;
		final User user = new User();
		user.setId(id);
		user.read();
		this.setAuthor(user);
	}

	@Override
	public void setPageContext(final PageContext pageContext) {
		super.setPageContext(pageContext);
	}

	public List<String> getFollowerIds() {
		final User author = this.getAuthor();
		final List<String> ids = author.getFollowers();
		if (ids == null) {
			return new ArrayList<String>();
		}
		return ids;
	}

	public void prepareFollower(final String id) {
		final User follower = new User();
		follower.setId(id);
		follower.read();
		final String name = follower.getDefaultPenName();

		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("followerName", name);

		final String url = "/author?id=" + id;
		pageContext.setAttribute("followerProfileUrl", url);

		final int avatarId = follower.getAvatarId();
		pageContext.setAttribute("followerAvatarId", avatarId);

		final String avatarDesc = User.getAvatarDesc(avatarId);
		pageContext.setAttribute("followerAvatarDesc", avatarDesc);
	}

	public void prepareAuthorId() {
		final PageContext pageContext = this.getPageContext();
		final String id = this.getAuthorId();
		pageContext.setAttribute("authorId", id);
	}
}
