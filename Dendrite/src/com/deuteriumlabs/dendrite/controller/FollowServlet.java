/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class FollowServlet extends DendriteServlet {

	private static final long serialVersionUID = -6609016005526027490L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String targetId = req.getParameter("id");
		final boolean isTargetIdValid = isTargetIdValid(targetId);
		if (isTargetIdValid == true) {
			final FollowController controller = new FollowController();
			controller.setTargetId(targetId);
			final User source = User.getMyUser();
			final String sourceId = source.getId();
			controller.setSourceId(sourceId);
			controller.enableFollow();
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

	private boolean isTargetIdValid(final String id) {
		final User target = new User();
		target.setId(id);
		return target.isInStore();
	}
}
