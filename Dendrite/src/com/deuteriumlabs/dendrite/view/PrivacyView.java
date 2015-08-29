/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

public class PrivacyView extends View {

	@Override
	public String getUrl() {
		return "/privacy";
	}

	@Override
	protected String getMetaDesc() {
		return "Your privacy is important to us. Our privacy policy is "
				+ "designed to protect your information.";
	}
}
