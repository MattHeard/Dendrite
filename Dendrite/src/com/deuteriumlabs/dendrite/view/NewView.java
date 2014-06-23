package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public class NewView extends FormView {

	private HttpSession session;

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
	
	public void setSession(final HttpSession session) {
		this.session = session;
	}
	
	public HttpSession getSession() {
		return this.session;
	}
	
	public boolean isDraftPending() {
		final HttpSession session = this.getSession();
		final Boolean isDraftPendingAttribute;
		isDraftPendingAttribute = (Boolean) session.getAttribute("isDraftPending");
		final boolean isDraftPending;
		if (isDraftPendingAttribute == null) {
			isDraftPending = false;
		} else {
			isDraftPending = isDraftPendingAttribute.booleanValue();
		}
		return isDraftPending;
	}
	
	public void prepareDraftTitle() {
		final PageContext pageContext = this.getPageContext();
		final HttpSession session = this.getSession();
		final String title = (String) session.getAttribute("title");
		pageContext.setAttribute("draftTitle", title);
	}
	
	public void prepareDraftContent() {
		final PageContext pageContext = this.getPageContext();
		final HttpSession session = this.getSession();
		final String content = (String) session.getAttribute("content");
		pageContext.setAttribute("draftContent", content);
	}
}
