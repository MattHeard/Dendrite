package com.deuteriumlabs.dendrite.view;

public abstract class FormView extends View {

	private String error;

	private String getError() {
		return this.error;
	}

	public boolean isAnOptionTooLong() {
		final String error = this.getError();
		return ("optionTooLong".equals(error));
	}

	public boolean isAuthorNameBlank() {
		final String error = this.getError();
		return ("blankAuthorName".equals(error));
	}

	public boolean isAuthorNameTooLong() {
		final String error = this.getError();
		return ("authorNameTooLong".equals(error));
	}

	public boolean isContentBlank() {
		final String error = this.getError();
		return ("blankContent".equals(error));
	}

	public boolean isContentTooLong() {
		final String error = this.getError();
		return ("contentTooLong".equals(error));
	}

	public boolean isTitleBlank() {
		final String error = this.getError();
		return ("blankTitle".equals(error));
	}

	public boolean isTitleTooLong() {
		final String error = this.getError();
		return ("titleTooLong".equals(error));
	}

	public void setError(final String error) {
		this.error = error;
	}

}
