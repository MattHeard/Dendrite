package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class AboutView extends View {

	@Override
	String getUrl() {
		return "/about.jsp";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite - About");
	}
}
