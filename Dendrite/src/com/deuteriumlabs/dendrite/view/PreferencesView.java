package com.deuteriumlabs.dendrite.view;

import com.deuteriumlabs.dendrite.model.User;

public class PreferencesView extends View {

	@Override
	String getUrl() {
		return "/preferences.jsp";
	}
	
	public int getAvatarId() {
		final User user = User.getMyUser();
		return user.getAvatarId();
	}
}
