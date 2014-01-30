package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.User;

/**
 * Presents a list of pages written by a particular user and presents user
 * preferences visible only to the matching user.
 */
public class AuthorView extends View {

	private String id;
	private User user;

	@Override
	String getUrl() {
		final String id = this.getId();
		return "/author.jsp?id=" + id;
	}

	private String getId() {
		return this.id;
	}
	
	public void setId(final String id) {
		this.id = id;
		final User user = new User();
		user.setId(id);
		user.read();
		this.setUser(user);
	}
	
	private void setUser(final User user) {
		this.user = user;
	}

	public String getPenName() {
		final User user = this.getUser();
		final String penName = user.getDefaultPenName();
		return penName;
	}

	private User getUser() {
		return this.user;
	}
	
}
