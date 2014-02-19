package com.deuteriumlabs.dendrite.view;

public abstract class FormView extends View {

	private String error;

	private String getError() {
		return this.error;
	}

	public boolean isThereABlankAuthorError() {
		final String error = this.getError();
		return ("blankAuthorName".equals(error));
	}

	public boolean isThereABlankContentError() {
		final String error = this.getError();
		return ("blankContent".equals(error));
	}

	public boolean isThereABlankTitleError() {
		final String error = this.getError();
		return ("blankTitle".equals(error));
	}

	public boolean isThereATooLongContentError() {
		final String error = this.getError();
		return ("contentTooLong".equals(error));
	}

	public boolean isThereATooLongOptionError() {
		final String error = this.getError();
		return ("optionTooLong".equals(error));
	}

	public boolean isThereATooLongTitleError() {
		final String error = this.getError();
		return ("titleTooLong".equals(error));
	}

	public void setError(final String error) {
		this.error = error;
	}

}
