package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitEditServlet extends SubmitServlet {

	private static final long serialVersionUID = 6369008865421800462L;
	private String pageNumber;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitEditController controller = new SubmitEditController();
		final String pageNumber = req.getParameter("pageNumber");
		this.setPageNumber(pageNumber);
		controller.setPageNumber(pageNumber);
		final String content = req.getParameter("content");
		controller.setContent(content);
		final String authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		final boolean isAuthorNameValid = controller.isAuthorNameValid();
		if (controller.isContentBlank() == true)
			this.redirectFromBlankContent();
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
			final PageId id = controller.getId();
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private String getEditUrl() {
		final String p = this.getPageNumber();
		return "/edit.jsp?p=" + p;
	}

	private String getPageNumber() {
		return this.pageNumber;
	}

	private void redirectFromInvalidAuthorName() {
		String url = getEditUrl();
		url += "&error=blankAuthor";
		this.redirect(url);
	}

	private void redirectFromBlankContent() {
		String url = getEditUrl();
		url += "&error=blankContent";
		this.redirect(url);
	}

	private void setPageNumber(final String pageNumber) {
		this.pageNumber = pageNumber;
	}
}
