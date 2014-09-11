package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class CoverView extends View {

	@Override
	String getUrl() {
		return "/cover";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite");
	}
}
