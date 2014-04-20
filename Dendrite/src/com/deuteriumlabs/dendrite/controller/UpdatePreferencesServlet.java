package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class UpdatePreferencesServlet extends DendriteServlet {

	private static final long serialVersionUID = 8943369549424406002L;
	private static final boolean isDebug = false;

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
		final String alignment = req.getParameter("alignment");
		controller.setAlignment(alignment);
		final String spacing = req.getParameter("spacing");
		controller.setSpacing(spacing);
		final String theme = req.getParameter("theme");
		controller.setTheme(theme);
		if (controller.isNewPenNameBlank() == true)
			this.redirectFromBlankNewPenName();
		else {
			final boolean isUpdated = controller.updatePreferences();
			if (isUpdated == true)
				this.redirectToMyAuthorPage();
			else
				this.redirectFromUpdateFailure();
		}
		this.logParameters(resp, controller);
	}

	private void logParameters(final HttpServletResponse resp, 
			final UpdatePreferencesController controller) {
		if (this.isDebug == true) {
			List<String> parameterValues = controller.getParameterValues();
			for (String value : parameterValues) {
				try {
					resp.getWriter().println(value);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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

	@SuppressWarnings("unused")
	@Override
	protected void redirect(String url) {
		if (this.isDebug == false)
			super.redirect(url);
	}
	
	
}
