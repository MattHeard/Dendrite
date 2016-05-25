/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitRewriteServlet extends SubmitServlet {

	private static final long serialVersionUID = 6369008865421800462L;
	String pageNumber;
	private HttpServletRequest req;
	HttpSession session;
	String content;
	String authorName;
	List<String> options;
	String authorId;

	@Override
	protected final void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		this.req = req;
		this.setResponse(resp);
		final User myUser = User.getMyUser();
		this.handleReq(myUser);
	}

	private void handleReq(final User myUser) throws IOException {
		final HttpServletRequest req = this.req;
		final HttpSession session = req.getSession();
		this.session = session;
		final String pageNumber = req.getParameter("pageNumber");
		this.pageNumber = pageNumber;
		final String content = req.getParameter("content");
		this.content = content;
		final String authorName = req.getParameter("authorName");
		this.authorName = authorName;
		final List<String> options = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			options.add(option);
		}
		this.options = options;
		final String authorId = req.getParameter("authorId");
		this.authorId = authorId;

		processRewrite(myUser);
	}

	void processRewrite(final User myUser) throws IOException {
		final SubmitRewriteController controller;
		controller = new SubmitRewriteController();
		controller.setSession(this.session);
		controller.startDraft();
		controller.setPageNumber(this.pageNumber);
		controller.setContent(this.content);
		controller.setAuthorName(this.authorName);
		final StoryPage parent = StoryPage.getParentOf(new PageId(
				this.pageNumber));
		controller.setParent(parent);
		for (final String option : this.options) {
			if (option != null) {
				controller.addOption(option);
			}
		}
		controller.setAuthorId(this.authorId);
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
			controller.buildNewPage(myUser);
			final PageId id = controller.getId();
			final HttpServletResponse resp = this.getResponse();
			resp.sendRedirect("/read?p=" + id);
		}
	}

	@Override
	final public String getUrl() {
		final String p = this.pageNumber;
		return "/rewrite?p=" + p;
	}
}
