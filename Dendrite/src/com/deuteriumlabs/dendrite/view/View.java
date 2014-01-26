package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.User;

/**
 * Represents the visible face of the website. The view gets data from the
 * model and displays it to the user in JSPs.
 */
public class View {
	
	/**
	 * Returns whether the visitor is logged in or not.
	 * @return <code>true</code> if the visitor is logged in, <code>false</code>
	 * otherwise
	 */
	public static boolean isUserLoggedIn() {
		return User.isMyUserLoggedIn();
	}

}
