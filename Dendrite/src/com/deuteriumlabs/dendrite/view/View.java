/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.Notification;
import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Represents the visible face of the website. The view gets data from the model
 * and displays it to the user in JSPs.
 */
public abstract class View {

	/**
	 * Lists the links in the footer bar.
	 */
	public class FooterLinkList {

		/**
		 * The index used for iterating over the list.
		 */
		private int currIndex;

		/**
		 * The list of link texts to display.
		 */
		private final String[] list = { "About", "Terms", "Privacy", "Contact" };

		public FooterLinkList() {
			this.currIndex = 0;
		}

		public final String getCurrText() {
			return list[currIndex];
		}

		public final String getCurrUrl() {
			return "/" + list[currIndex].toLowerCase();
		}

		/**
		 * Returns whether the end of the list has not yet finished iterating.
		 * 
		 * @return true if there are remaining links to display
		 */
		public final boolean hasAnotherLink() {
			return currIndex < list.length;
		}

		public final void next() {
			++currIndex;
		}
	}
	
	private static String getAlignmentClass(final User myUser) {
		final String alignmentSetting = getUserAlignment(myUser);
		if (alignmentSetting.equals("Left")) {
			return "leftAlignment";
		} else if (alignmentSetting.equals("Right")) {
			return "rightAlignment";
		} else if (alignmentSetting.equals("Center")) {
			return "centreAlignment";
		} else {
			return "justifyAlignment";
		}
	}

	/**
	 * Returns the URL to the author page of the logged-in user, using the App
	 * Engine user ID.
	 * 
	 * @return The URL to the author page of the logged-in user
	 */
	// TODO Make private and non-static
	public static String getAuthorLink(final User myUser) {
		if (myUser != null) {
			final String userId = myUser.getId();
			return "author?id=" + userId;
		} else {
			return null;
		}
	}

	private static String getColourClass(final User myUser) {
		final String colourSetting = getUserFontColour(myUser);
		if (colourSetting.equals("Charcoal")) {
			return "charcoalColour";
		} else if (colourSetting.equals("Black")) {
			return "blackColour";
		} else if (colourSetting.equals("Grey")) {
			return "greyColour";
		} else if (colourSetting.equals("Blue")) {
			return "blueColour";
		} else if (colourSetting.equals("Green")) {
			return "greenColour";
		} else if (colourSetting.equals("Red")) {
			return "redColour";
		} else {
			return "defaultColour";
		}
	}

	public static String getMyUserId() {
	    return User.getMyUserId();
	}

	/**
	 * Returns the default pen name of the logged-in user.
	 * 
	 * @return The default pen name of the logged-in user
	 */
	public static String getMyUserName(final User myUser) {
		if (myUser != null) {
			return myUser.getDefaultPenName();
		} else {
			return null;
		}
	}

	private static String getSizeClass(final User myUser) {
		final double sizeSetting = getUserFontSize(myUser);
		if (sizeSetting == 2.0) {
			return "hugeSize";
		} else if (sizeSetting == 1.5) {
			return "largeSize";
		} else if (sizeSetting == 0.8) {
			return "smallSize";
		} else {
			return "mediumSize";
		}
	}

	private static String getSpacingClass(final User myUser) {
		final double spacingSetting = getUserSpacing(myUser);
		if (spacingSetting == 3.0) {
			return "hugeSpacing";
		} else if (spacingSetting == 2.0) {
			return "largeSpacing";
		} else if (spacingSetting == 1.0) {
			return "smallSpacing";
		} else {
			return "mediumSpacing";
		}
	}

	private static String getThemeClass(final User myUser) {
		final String themeSetting = getUserTheme(myUser);
		if (themeSetting.equals("Dark")) {
			return "darkTheme";
		} else if (themeSetting.equals("Sepia")) {
			return "sepiaTheme";
		} else if (themeSetting.equals("Lovely")) {
			return "lovelyTheme";
		} else {
			return "lightTheme";
		}
	}

	private static String getThemeClasses(final User myUser) {
		final List<String> themeClassList = new ArrayList<String>();
		themeClassList.add(getSizeClass(myUser));
		themeClassList.add(getTypefaceClass(myUser));
		themeClassList.add(getColourClass(myUser));
		themeClassList.add(getSpacingClass(myUser));
		themeClassList.add(getAlignmentClass(myUser));
		themeClassList.add(getThemeClass(myUser));
		String themeClassStr = "";
		for (final String themeClass : themeClassList) {
			themeClassStr += themeClass + " ";
		}
		return themeClassStr;
	}

	private static String getTypefaceClass(final User myUser) {
		final String typefaceSetting = getUserFontType(myUser);
		if (typefaceSetting.equals("Serif")) {
			return "serifTypeface";
		} else if (typefaceSetting.equals("Monospace")) {
			return "monospaceTypeface";
		} else if (typefaceSetting.equals("Cursive")) {
			return "cursiveTypeface";
		} else if (typefaceSetting.equals("Fantasy")) {
			return "fantasyTypeface";
		} else {
			return "sansSerifTypeface";
		}
	}

	public static String getUserAlignment(final User myUser) {
		if (myUser != null) {
			return myUser.getAlignment();
		} else {
			return "";
		}
	}

	public static String getUserFontColour(final User myUser) {
		if (myUser != null) {
			return myUser.getFontColour();
		} else {
			return "";
		}
	}

	public static double getUserFontSize(final User myUser) {
		if (myUser != null) {
			return myUser.getFontSize();
		} else {
			return -1.0;
		}
	}

	public static String getUserFontType(final User myUser) {
		if (myUser != null) {
			return myUser.getFontType();
		} else {
			return "";
		}
	}

	public static double getUserSpacing(final User myUser) {
		if (myUser != null) {
			return myUser.getSpacing();
		} else {
			return -1.0;
		}
	}

	public static String getUserTheme(final User myUser) {
		if (myUser != null) {
			return myUser.getTheme();
		} else {
			return "";
		}
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

	private final FooterLinkList footerLinks;
	protected PageContext pageContext;
	private HttpServletRequest request;

	/**
	 * Displays content to the user.
	 */
	protected View() {
		this.footerLinks = new FooterLinkList();
	}

	public boolean areThereNewNotifications() {
		final int numNewNotifications = this.countNewNotifications();
		return (numNewNotifications > 0);
	}

	public int countNewNotifications() {
		final String id = View.getMyUserId();
		return Notification.countNewNotificationsForRecipient(id);
	}

	/**
	 * Returns a link for logging in, with a redirect back to this page after
	 * the login has completed.
	 * 
	 * @return The URL to the login page
	 */
	public final String getLoginLink() {
		final String returnUrl = this.getUrl();
		final UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(returnUrl);
	}

	/**
	 * Returns a link for logging out, with a redirect back to this page after
	 * the logout has completed.
	 * 
	 * TODO: Figure out how to test this method.
	 * 
	 * @return The URL to the logout page
	 */
	public final String getLogoutLink() {
		final String returnUrl = this.getUrl();
		final UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL(returnUrl);
	}

	protected final PageContext getPageContext() {
		return this.pageContext;
	}

	public final HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Returns a link to this page, which can be supplied for when the user
	 * should be redirected back to this page.
	 * 
	 * @return The URL to this page
	 */
	abstract public String getUrl();

	/**
	 * Returns the title of the web page displaying this View.
	 * 
	 * @return The title of the web page displaying this View.
	 */
	public final String getWebPageTitle() {
		return "Dendrite";
	}

	public final boolean hasAnotherFooterLink() {
		return footerLinks.hasAnotherLink();
	}

	public void initialise() {
		this.prepareMetaDesc();
	}

	public final void prepareNextFooterLink() {
		pageContext.setAttribute("url", footerLinks.getCurrUrl());
		pageContext.setAttribute("text", footerLinks.getCurrText());
		footerLinks.next();
	}

	public void prepareThemeClasses(final User myUser) {
		final String themeClasses = getThemeClasses(myUser);
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("themeClasses", themeClasses);
	}

	public void prepareUserHeader(final User myUser) {
		final PageContext pageContext = this.getPageContext();
		final String authorLink = getAuthorLink(myUser);
		pageContext.setAttribute("authorLink", authorLink);
		final String userName = getMyUserName(myUser);
		pageContext.setAttribute("userName", userName);
		final String logoutLink = this.getLogoutLink();
		pageContext.setAttribute("logoutLink", logoutLink);
	}

	public void setPageContext(final PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public final void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public void prepareThemeClass(final User myUser) {
		final String themeClass = getThemeClass(myUser);
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("themeClass", themeClass);
	}

	public void prepareNumNotifications() {
		final int num = this.countNewNotifications();
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("numNotifications", num);
	}

	public void prepareFmtDimension(final String dimension) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("lowerFmtDimension", dimension.toLowerCase());
		pageContext.setAttribute("upperFmtDimension", dimension.toUpperCase());
	}

	public void prepareUpperCaseFmtVal(final String val) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("upperFmtVal", val.toUpperCase());
	}

	public void prepareMetaDesc() {
		final PageContext pageContext = this.getPageContext();
		final String attrName = "metaDesc";
		final String metaDesc = this.getMetaDesc();
		final String attrVal = metaDesc;
		pageContext.setAttribute(attrName, attrVal);
	}

	protected String getMetaDesc() {
		return "Dendrite is an online adventure game book where you can "
				+ "choose the story path, or write your own. Be a spy, a "
				+ "princess, a pirate, a rockstar, a ninja...";
	}
}
