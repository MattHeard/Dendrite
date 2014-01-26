package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Represents the visible face of the website. The view gets data from the
 * model and displays it to the user in JSPs.
 */
public abstract class View {
	
	/**
	 * Returns whether the visitor is logged in or not.
	 * @return <code>true</code> if the visitor is logged in, <code>false</code>
	 * otherwise
	 */
	public static boolean isUserLoggedIn() {
		return User.isMyUserLoggedIn();
	}
	
	public String getLoginLink() {
		final String returnUrl = this.getUrl();
		final UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(returnUrl);
	}

	abstract String getUrl();

}
