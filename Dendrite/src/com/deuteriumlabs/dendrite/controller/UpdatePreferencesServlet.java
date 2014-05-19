package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		final String fontSize = req.getParameter("fontSize");
		controller.setFontSize(fontSize);
		final String fontType = req.getParameter("fontType");
		controller.setFontType(fontType);
		final String fontColour = req.getParameter("fontColour");
		controller.setFontColour(fontColour);
		final String spacing = req.getParameter("spacing");
		controller.setSpacing(spacing);
		final String alignment = req.getParameter("alignment");
		controller.setAlignment(alignment);
		final String theme = req.getParameter("theme");
		controller.setTheme(theme);
		
		int avatarId = this.getAvatarId(req);
		controller.setAvatarId(avatarId);
		
		if (controller.isNewPenNameBlank() == true)
			this.redirectFromBlankNewPenName();
		else {
			final boolean isUpdated = controller.updatePreferences();
			if (isUpdated == true)
				this.redirectToMyPreferencesPage();
			else
				this.redirectFromUpdateFailure();
		}
	}

	private int getAvatarId(final HttpServletRequest req) {
		final String avatarNumberParameter = req.getParameter("avatar");
		int avatarNumber;
		try {
			avatarNumber = Integer.parseInt(avatarNumberParameter);
		} catch (NumberFormatException e) {
			avatarNumber = 1;
		}
		return avatarNumber;
	}

	private void redirectFromBlankNewPenName() {
		final String url = "/preferences.jsp?error=blankNewPenName";
		this.redirect(url);
	}

	private void redirectFromUpdateFailure() {
		final String url = "/preferences.jsp?error=updateFailed";
		this.redirect(url);
	}

	private void redirectToMyPreferencesPage() {
		final String url = "/preferences.jsp";
		this.redirect(url);
	}

	@SuppressWarnings("all")
	@Override
	protected void redirect(String url) {
		super.redirect(url);
	}

}
