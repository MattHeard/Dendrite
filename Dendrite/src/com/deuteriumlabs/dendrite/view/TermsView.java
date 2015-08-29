/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

public class TermsView extends View {

	@Override
	String getUrl() {
		return "/terms";
	}

	@Override
	protected String getMetaDesc() {
		return "We have a common set of community rules or terms that all our "
				+ "community members and users of this Website must adhere to "
				+ "at all times.";
	}
}
