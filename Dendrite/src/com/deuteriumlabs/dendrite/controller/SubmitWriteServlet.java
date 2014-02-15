package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitWriteServlet extends SubmitServlet {

	private static final long serialVersionUID = -1895973678482433819L;
	private String from;
	private String linkIndex;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitWriteController controller;
		controller = new SubmitWriteController();
		
		final String from = req.getParameter("from");
		this.setFrom(from);
		controller.setFrom(from);
		
		final String linkIndex = req.getParameter("linkIndex");
		this.setLinkIndex(linkIndex);
		controller.setLinkIndex(linkIndex);
		
		final String content = req.getParameter("content");
		controller.setContent(content);
		final boolean isBodyValid = controller.isContentValid();
		
		final String authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		final boolean isAuthorNameValid = controller.isAuthorNameValid();
		if (isBodyValid == false)
			this.redirectFromInvalidBody();
		else if (isAuthorNameValid == false)
			this.redirectFromInvalidAuthorName();
		else {
			for (int i = 0; i < 5; i++) {
				final String option = req.getParameter("option" + i);
				if (option != null)
					controller.addOption(option);
			}
			
			final String authorId = req.getParameter("authorId");
			controller.setAuthorId(authorId);
			
			controller.buildNewPage();
			
			controller.connectIncomingOption();
			
			final PageId id = controller.getId();
			
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private String getFrom() {
		return this.from;
	}

	private String getLinkIndex() {
		return this.linkIndex;
	}

	private String getWriteUrl() {
		final String from = this.getFrom();
		String url = "/write.jsp?from=" + from;
		final String linkIndex = this.getLinkIndex();
		url += "&linkIndex=" + linkIndex;
		return url;
	}

	private void redirectFromInvalidAuthorName() {
		String url = getWriteUrl();
		url += "&error=blankAuthor";
		this.redirect(url);
	}

	private void redirectFromInvalidBody() {
		String url = getWriteUrl();
		url += "&error=blankContent";
		this.redirect(url);
	}

	private void setFrom(final String from) {
		this.from = from;
	}
	
	private void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}
}
