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
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitWriteServlet extends SubmitServlet {

	private static final long serialVersionUID = -1895973678482433819L;
	private String from;
	private String linkIndex;
	private HttpServletRequest req;
	private String content;
	private String authorName;
	private List<String> options;
	private String authorId;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setReq(req);
		this.setResponse(resp);
		final SubmitWriteController controller;
		controller = new SubmitWriteController();
		final HttpSession session = req.getSession();
		controller.setSession(session);
		controller.startDraft();
		final String from = req.getParameter("from");
		this.setFrom(from);
		controller.setFrom(from);
		final String linkIndex = req.getParameter("linkIndex");
		this.setLinkIndex(linkIndex);
		controller.setLinkIndex(linkIndex);
		this.content = req.getParameter("content");
		controller.setContent(this.content);
		this.authorName = req.getParameter("authorName");
		controller.setAuthorName(this.authorName);
		final StoryPage parent = new StoryPage();
		parent.setId(new PageId(from));
		parent.read();
		controller.setParent(parent);
		this.options = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			this.options.add(option);
		}
		for (final String option : this.options) {
			if (option != null) {
				controller.addOption(option);
			}
		}
		this.authorId = req.getParameter("authorId");
		controller.setAuthorId(this.authorId);
		if (controller.isContentBlank() == true)
			this.redirectFromBlankContent();
		else if (controller.isContentTooLong() == true)
			this.redirectFromTooLongContent();
		else if (controller.isAnyOptionTooLong() == true)
			this.redirectFromTooLongOption();
		else if (controller.isAuthorNameBlank() == true)
			this.redirectFromBlankAuthorName();
		else if (controller.isAuthorNameTooLong() == true)
			this.redirectFromTooLongAuthorName();
		else {
			final boolean isIncomingOptionConnected;
			isIncomingOptionConnected = this.isIncomingOptionConnected();
			if (isIncomingOptionConnected) {
				this.passToRewriteServlet();
			} else {
				controller.buildNewPage();
				controller.connectIncomingOption();
				final PageId id = controller.getId();
				resp.sendRedirect("/read?p=" + id);
			}
		}
	}

	private void setReq(final HttpServletRequest req) {
		this.req = req;
	}

	private void passToRewriteServlet() throws IOException {
		final SubmitRewriteServlet rewriteServlet = new SubmitRewriteServlet();
		rewriteServlet.session = req.getSession();
		int target = this.getConnectedOptionTarget();
		rewriteServlet.pageNumber = Integer.toString(target);
		rewriteServlet.content = this.content;
		rewriteServlet.authorName = this.authorName;
		rewriteServlet.options = this.options;
		rewriteServlet.authorId = this.authorId;
		rewriteServlet.setResponse(this.getResponse());
		rewriteServlet.processRewrite();
	}

	private int getConnectedOptionTarget() {
		return this.getIncomingOption().getTarget();
	}

	private boolean isIncomingOptionConnected() {
		final StoryOption option = this.getIncomingOption();
		return option.isConnected();
	}

	private StoryOption getIncomingOption() {
		final StoryOption option = new StoryOption();
		final PageId source = new PageId(this.getFrom());
		option.setSource(source);
		final int listIndex = Integer.parseInt(this.getLinkIndex());
		option.setListIndex(listIndex);
		option.read();
		return option;
	}

	private String getFrom() {
		return this.from;
	}

	private String getLinkIndex() {
		return this.linkIndex;
	}

	@Override
	String getUrl() {
		final String from = this.getFrom();
		String url = "/write?from=" + from;
		final String linkIndex = this.getLinkIndex();
		url += "&linkIndex=" + linkIndex;
		return url;
	}

	private void setFrom(final String from) {
		this.from = from;
	}

	private void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}
}
