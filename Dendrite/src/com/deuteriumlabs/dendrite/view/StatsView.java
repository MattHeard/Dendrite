/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.StoryPage;

public class StatsView extends View {

	private static final String URL = "/stats";
	private static final String WEB_PG_TITLE_ATTR_NAME = "webPageTitle";
	private static final String WEB_PG_TITLE_ATTR_VAL = "Dendrite - Stats";
	private static final String NUM_PGS_ATTR_NAME = "numPgs";

	@Override
	String getUrl() {
		return URL;
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute(WEB_PG_TITLE_ATTR_NAME, WEB_PG_TITLE_ATTR_VAL);
		super.initialise();
	}
	
	public void prepareNumPgs() {
		final PageContext pageContext = this.getPageContext();
		String numPgsAttrName = NUM_PGS_ATTR_NAME;
		int numPgsAttrVal = StoryPage.countAllPgs();
		pageContext.setAttribute(numPgsAttrName, numPgsAttrVal);
	}
}
