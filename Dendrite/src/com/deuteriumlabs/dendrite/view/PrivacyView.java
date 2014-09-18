package com.deuteriumlabs.dendrite.view;

public class PrivacyView extends View {

	@Override
	String getUrl() {
		return "/privacy";
	}

	@Override
	protected String getMetaDesc() {
		return "Your privacy is important to us. Our privacy policy is "
				+ "designed to protect your information.";
	}
}
