package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpServletRequest;

import com.deuteriumlabs.dendrite.model.User;

public class PreferencesView extends View {

	private static final String NEW_PEN_NAME_IS_BLANK = "blankNewPenName";

	private String error;

	@Override
	String getUrl() {
		return "/preferences";
	}

	public static String getAvatarDesc(final int id) {
		return User.getAvatarDesc(id);
	}

	public int getAvatarId() {
		final User user = User.getMyUser();
		return user.getAvatarId();
	}

	public boolean isNewPenNameBlank() {
		final String error = this.getError();
		return NEW_PEN_NAME_IS_BLANK.equals(error);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public void initialise() {
		this.initialiseError();

		super.initialise();
	}

	private void initialiseError() {
		final HttpServletRequest request = this.getRequest();
		final String error = request.getParameter("error");
		this.setError(error);
	}

	@Override
	protected String getMetaDesc() {
		return "Customise your reading experience and the look and feel of "
				+ "Dendrite.";
	}
}
