package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.User;

public class UpdatePreferencesController {

	private String newPenName;

	private String getNewPenName() {
		return newPenName;
	}

	public boolean isNewPenNameBlank() {
		final String newPenName = this.getNewPenName();
		return (newPenName == null || newPenName.equals(""));
	}

	public void setNewPenName(final String newPenName) {
		this.newPenName = newPenName;
	}

	public boolean updatePreferences() {
		final User myUser = User.getMyUser();
		if (myUser != null) {
			final String penName = this.getNewPenName();
			myUser.setDefaultPenName(penName);
			myUser.update();
			return true;
		} else
			return false;
	}

}
