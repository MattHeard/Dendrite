package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Represents the visible face of the website. The view gets data from the model
 * and displays it to the user in JSPs.
 */
public abstract class View {

	/**
	 * Returns the URL to the author page of the logged-in user, using the App
	 * Engine user ID.
	 * 
	 * @return The URL to the author page of the logged-in user
	 */
	public static String getAuthorLink() {
		final User myUser = User.getMyUser();
		final String userId = myUser.getId();
		return "author.jsp?id=" + userId;
	}

	public static String getMyUserId() {
		final User myUser = User.getMyUser();
		if (myUser != null)
			return myUser.getId();
		else
			return null;
	}

	/**
	 * Returns the default pen name of the logged-in user.
	 * 
	 * @return The default pen name of the logged-in user
	 */
	public static String getMyUserName() {
		final User myUser = User.getMyUser();
		return myUser.getDefaultPenName();
	}

	public static String getUserAlignment() {
		final User myUser = User.getMyUser();
		return myUser.getAlignment();
	}

	public static String getUserFontColour() {
		final User myUser = User.getMyUser();
		return myUser.getFontColour();
	}

	public static double getUserFontSize() {
		final User myUser = User.getMyUser();
		return myUser.getFontSize();
	}

	public static String getUserFontType() {
		final User myUser = User.getMyUser();
		return myUser.getFontType();
	}

	public static double getUserSpacing() {
		final User myUser = User.getMyUser();
		return myUser.getSpacing();
	}

	public static String getUserTheme() {
		final User myUser = User.getMyUser();
		return myUser.getTheme();
	}

	public static boolean isUserFontSizeSet() {
		final User myUser = User.getMyUser();
		return myUser.isFontSizeSet();
	}

	/**
	 * Returns whether the visitor is logged in or not.
	 * 
	 * @return <code>true</code> if the visitor is logged in, <code>false</code>
	 *         otherwise
	 */
	public static boolean isUserLoggedIn() {
		return User.isMyUserLoggedIn();
	}

	private PageContext pageContext;

	public void setPageContext(final PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	protected PageContext getPageContext() {
		return this.pageContext;
	}

	/**
	 * Returns a link for logging in, with a redirect back to this page after
	 * the login has completed.
	 * 
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
	 * 
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
	 * 
	 * @return The URL to this page
	 */
	abstract String getUrl();
	
	/**
	 * Returns the title of the web page displaying this View.
	 * 
	 * @return The title of the web page displaying this View.
	 */
	public String getWebPageTitle() {
		return "Dendrite";
	}
}
