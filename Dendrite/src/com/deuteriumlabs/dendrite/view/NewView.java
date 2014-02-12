package com.deuteriumlabs.dendrite.view;



public class NewView extends View {

	private String error;

	@Override
	String getUrl() {
		return "/new.jsp";
	}
	
	public void setError(final String error) {
		this.error = error;
	}
	
	public boolean isThereABlankTitleError() {
		final String error = this.getError();
		return ("blankTitle".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereABlankContentError() {
		final String error = this.getError();
		return ("blankContent".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereABlankAuthorError() {
		final String error = this.getError();
		return ("blankAuthor".equals(error));	// Yoda-style to avoid null
	}

	private String getError() {
		return this.error;
	}
}
