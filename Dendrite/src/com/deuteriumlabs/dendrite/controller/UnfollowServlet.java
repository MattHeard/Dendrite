/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class UnfollowServlet extends DendriteServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1671751304334555912L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String targetId = req.getParameter("id");
		final boolean isTargetIdValid = isTargetIdValid(targetId);
		if (isTargetIdValid == true) {
			final UnfollowController controller = new UnfollowController();
			controller.setTargetId(targetId);
			final User source = User.getMyUser();
			final String sourceId = source.getId();
			controller.setSourceId(sourceId);
			controller.enableUnfollow();
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private boolean isTargetIdValid(final String id) {
		final User target = new User();
		target.setId(id);
		return target.isInStore();
	}
}
