package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class ContributorsView extends View {

	@Override
	String getUrl() {
		return "/contributors.jsp";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite - Contributors");
	}
}
