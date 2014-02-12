package com.deuteriumlabs.dendrite.view;



public class NewView extends View {

	private String error;

	private String getError() {
		return this.error;
	}
	
	@Override
	String getUrl() {
		return "/new.jsp";
	}
	
	public boolean isThereABlankAuthorError() {
		final String error = this.getError();
		return ("blankAuthor".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereABlankContentError() {
		final String error = this.getError();
		return ("blankContent".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereABlankTitleError() {
		final String error = this.getError();
		return ("blankTitle".equals(error));	// Yoda-style to avoid null
	}
	
	public boolean isThereATooLongTitleError() {
		final String error = this.getError();
		return ("titleTooLong".equals(error));	// Yoda-style to avoid null
	}

	public void setError(final String error) {
		this.error = error;
	}
}
