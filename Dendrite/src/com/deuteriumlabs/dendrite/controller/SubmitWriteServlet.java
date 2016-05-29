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
import com.deuteriumlabs.dendrite.model.User;

public class SubmitWriteServlet extends SubmitServlet {

	private static final long serialVersionUID = -1895973678482433819L;
	private String authorId;
	private String authorName;
	private String content;
	private String from;
	private String linkIndex;
	private List<String> options;
	private HttpServletRequest req;

	@Override
	public String getUrl() {
		return "/write?from=" + from + "&linkIndex=" + linkIndex;
	}

	private int getConnectedOptionTarget() {
		return getIncomingOption().getTarget();
	}

	private StoryOption getIncomingOption() {
		final StoryOption option = new StoryOption();
		final PageId source = new PageId(from);
		option.setSource(source);
		final int listIndex = Integer.parseInt(linkIndex);
		option.setListIndex(listIndex);
		option.read();
		return option;
	}

	private boolean isIncomingOptionConnected() {
		final StoryOption option = getIncomingOption();
		return option.isConnected();
	}

	private void passToRewriteServlet(final User myUser) throws IOException {
		final SubmitRewriteServlet rewriteServlet = new SubmitRewriteServlet();
		rewriteServlet.setSession(req.getSession());
		int target = getConnectedOptionTarget();
		rewriteServlet.setPageNumber(Integer.toString(target));
		rewriteServlet.setContent(content);
		rewriteServlet.setAuthorName(authorName);
		rewriteServlet.setOptions(options);
		rewriteServlet.setAuthorId(authorId);
		rewriteServlet.setResponse(getResponse());
		rewriteServlet.processRewrite(myUser);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.req = req;
		setResponse(resp);
		final SubmitWriteController controller;
		controller = new SubmitWriteController();
		final HttpSession session = req.getSession();
		controller.setSession(session);
		controller.startDraft();
		from = req.getParameter("from");
		controller.setFrom(from);
		linkIndex = req.getParameter("linkIndex");
		controller.setLinkIndex(linkIndex);
		content = req.getParameter("content");
		controller.setContent(content);
		authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		final StoryPage parent = new StoryPage();
		parent.setId(new PageId(from));
		parent.read();
		controller.setParent(parent);
		options = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			options.add(option);
		}
		for (final String option : options) {
			if (option != null) {
				controller.addOption(option);
			}
		}
		authorId = req.getParameter("authorId");
		controller.setAuthorId(authorId);
		if (controller.isContentBlank() == true)
			redirectFromBlankContent();
		else if (controller.isContentTooLong() == true)
			redirectFromTooLongContent();
		else if (controller.isAnyOptionTooLong() == true)
			redirectFromTooLongOption();
		else if (controller.isAuthorNameBlank() == true)
			redirectFromBlankAuthorName();
		else if (controller.isAuthorNameTooLong() == true)
			redirectFromTooLongAuthorName();
		else {
            final User myUser = User.getMyUser();
			final boolean isIncomingOptionConnected;
			isIncomingOptionConnected = isIncomingOptionConnected();
			if (isIncomingOptionConnected) {
				passToRewriteServlet(myUser);
			} else {
				controller.buildNewPage(myUser);
				controller.connectIncomingOption();
				final PageId id = controller.getId();
				resp.sendRedirect("/read?p=" + id);
			}
		}
	}
}
