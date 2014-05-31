package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class NewView extends FormView {

	@Override
	String getUrl() {
		return "/new.jsp";
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("webPageTitle", "Dendrite - New Story");
		final HttpServletRequest request = this.getRequest();
		final String error = request.getParameter("error");
		this.setError(error);
	}
	
	
}
