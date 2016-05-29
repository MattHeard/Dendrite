/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.User;

public class LoveServlet extends DendriteServlet {
	private static final String IS_ADDED_PARAM_NAME = "isAdded";
	private static final String PAGE_ID_PARAM_NAME = "p";
	private static final long serialVersionUID = 7662290110231192243L;

	private int count;

	private boolean extractIsLoveAdded(final HttpServletRequest req) {
		return Boolean.valueOf(req.getParameter(IS_ADDED_PARAM_NAME));
	}

	private PageId extractPageId(final HttpServletRequest req) {
		return new PageId(req.getParameter(PAGE_ID_PARAM_NAME));
	}

	private void setInvalidPageIdError(final HttpServletResponse resp)
			throws IOException {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		resp.getWriter().println("PageId is invalid.");
	}

	private void setInvalidUserIdError(final HttpServletResponse resp)
			throws IOException {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		resp.getWriter().println("UserId is invalid.");
	}

	private void setLoveOkResponse(final HttpServletResponse resp)
			throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println(count);
	}

	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		final LoveController controller = new LoveController();

		final PageId pageId = extractPageId(req);
		final String userId = User.getMyUserId();
		if (!pageId.isValid()) {
			setInvalidPageIdError(resp);
		} else if (userId == null) {
			setInvalidUserIdError(resp);
		} else {
			controller.pageId = pageId;
			controller.loverId = userId;

			final boolean isLoveAdded = extractIsLoveAdded(req);
			if (isLoveAdded) {
				count = controller.addLove();
			} else {
				count = controller.removeLove();
			}
			setLoveOkResponse(resp);
		}
	}

}
