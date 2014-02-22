package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class UpdatePreferencesServlet extends DendriteServlet {

	private static final long serialVersionUID = 8943369549424406002L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final UpdatePreferencesController controller;
		controller = new UpdatePreferencesController();
		final String newPenName = req.getParameter("newPenName");
		controller.setNewPenName(newPenName);
		if (controller.isNewPenNameBlank() == true)
			this.redirectFromBlankNewPenName();
		else {
			final boolean isUpdated = controller.updatePreferences();
			if (isUpdated == true)
				this.redirectToMyAuthorPage();
			else
				this.redirectFromUpdateFailure();
		}
	}

	private void redirectFromBlankNewPenName() {
		final String url = "/preferences.jsp?error=blankNewPenName";
		this.redirect(url);
	}

	private void redirectFromUpdateFailure() {
		final String url = "/preferences.jsp?error=updateFailed";
		this.redirect(url);
	}

	private void redirectToMyAuthorPage() {
		final User myUser = User.getMyUser();
		final String id = myUser.getId();
		final String url = "/author.jsp?id=" + id;
		this.redirect(url);
	}
}
