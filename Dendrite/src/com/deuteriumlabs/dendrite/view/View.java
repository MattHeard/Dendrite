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
	 * Returns the URL to the author page of the logged-in user, using the App
	 * Engine user ID.
	 * @return The URL to the author page of the logged-in user 
	 */
	public static String getAuthorLink() {
		final User myUser = User.getMyUser();
		final String userId = myUser.getId();
		return "author.jsp?id=" + userId;
	}
	
	/**
	 * Returns the default pen name of the logged-in user.
	 * @return The default pen name of the logged-in user
	 */
	public static String getUserName() {
		final User myUser = User.getMyUser();
		return myUser.getDefaultPenName();
	}
	
	/**
	 * Returns whether the visitor is logged in or not.
	 * @return <code>true</code> if the visitor is logged in, <code>false</code>
	 * otherwise
	 */
	public static boolean isUserLoggedIn() {
		return User.isMyUserLoggedIn();
	}
	
	/**
	 * Returns a link for logging in, with a redirect back to this page after
	 * the login has completed.
	 * @return The URL to the login page
	 */
	public String getLoginLink() {
		final String returnUrl = this.getUrl();
		final UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(returnUrl);
	}
	
	/**
	 * Returns a link for logging out, with a redirect back to this page after
	 * the logout has completed.
	 * @return The URL to the logout page
	 */
	public String getLogoutLink() {
		final String returnUrl = this.getUrl();
		final UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL(returnUrl);
	}

	/**
	 * Returns a link to this page, which can be supplied for when the user
	 * should be redirected back to this page.
	 * @return The URL to this page
	 */
	abstract String getUrl();

}
