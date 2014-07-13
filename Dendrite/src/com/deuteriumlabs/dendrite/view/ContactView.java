package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class ContactView extends View {

	@Override
	String getUrl() {
		return "/contact";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite - Contact us");
	}
	
	
}
