package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitRewriteServlet extends SubmitServlet {

	private static final long serialVersionUID = 6369008865421800462L;
	private String pageNumber;
	
	@Override
	protected final void doPost(final HttpServletRequest req,
			final HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitRewriteController controller = new SubmitRewriteController();
		final HttpSession session = req.getSession();
		controller.setSession(session);
		controller.startDraft();
		final String pageNumber = req.getParameter("pageNumber");
		this.setPageNumber(pageNumber);
		controller.setPageNumber(pageNumber);
		final String content = req.getParameter("content");
		controller.setContent(content);
		final String authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		final StoryPage parent = StoryPage.getParentOf(new PageId(pageNumber));
		controller.setParent(parent);
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			if (option != null) {
				controller.addOption(option);
			}
		}
		final String authorId = req.getParameter("authorId");
		controller.setAuthorId(authorId);
		if (controller.isContentBlank()) {
			this.redirectFromBlankContent();
		} else if (controller.isContentTooLong()) {
			this.redirectFromTooLongContent();
		} else if (controller.isAnyOptionTooLong()) {
			this.redirectFromTooLongOption();
		} else if (controller.isAuthorNameBlank()) {
			this.redirectFromBlankAuthorName();
		} else if (controller.isAuthorNameTooLong()) {
			this.redirectFromTooLongAuthorName();
		} else {
			controller.buildNewPage();
			final PageId id = controller.getId();
			resp.sendRedirect("/read?p=" + id);
		}
	}

	private String getPageNumber() {
		return this.pageNumber;
	}

	@Override
	final
	String getUrl() {
		final String p = this.getPageNumber();
		return "/rewrite?p=" + p;
	}

	private void setPageNumber(final String pageNumber) {
		this.pageNumber = pageNumber;
	}
}
