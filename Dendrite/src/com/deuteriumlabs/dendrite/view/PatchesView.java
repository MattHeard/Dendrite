/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class PatchesView extends View {

	private static final String URL = "/patches";
	private static final String WEB_PG_TITLE_ATTR_NAME = "webPageTitle";
	private static final String WEB_PG_TITLE_ATTR_VAL = "Dendrite - Patches";

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute(WEB_PG_TITLE_ATTR_NAME, WEB_PG_TITLE_ATTR_VAL);
	}
}
