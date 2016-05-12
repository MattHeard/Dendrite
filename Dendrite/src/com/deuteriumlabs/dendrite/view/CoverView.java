/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class CoverView extends View {
    public CoverView(final PageContext pageContext) {
        this.pageContext = pageContext;
    }

	@Override
	public String getUrl() {
		return "/cover";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite");
	}

	public void prepareTagName(final String tag) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("tagName", tag);
	}
}
