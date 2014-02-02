package com.deuteriumlabs.dendrite.model;

/**
 *	Represents a unique identifier for a story page. The IDs are two-
 *	dimensional, where differing page numbers represent different points in the
 *	story (or possibly even different stories) while differing versions of the
 *	same page number represent different alternatives of the same point in the
 *	story. Both dimensions are theoretically unlimited, intending for
 *	unrestricted expansion of stories by increasing the page numbers and for
 *	unrestricted rewriting of stories by increasing the page versions. As
 *	currently implemented, there is an upper limit on the page number because
 *	of the <code>int</code> representation.
 */
public class PageId {
	private int number;
	private String version;
	
	public PageId(String string) {
		String digits = "";
		int i = 0;
		for (; i < string.length(); i++) {
			final char curr = string.charAt(i);
			final boolean isDigit = Character.isDigit(curr);
			if (isDigit == true)
				digits += curr;
			else
				break;
		}
		int number;
		try {
			number = Integer.parseInt(digits);
		} catch (NumberFormatException e) {
			number = 0;
		}
		if (number < 1)
			number = 0;
		this.setNumber(number);
		String letters = "";
		if (number > 0) {
			for (; i < string.length(); i++) {
				final char curr = string.charAt(i);
				final boolean isLetter = Character.isLetter(curr);
				if (isLetter == true)
					letters += curr;
				else
					break;
			}
			if (letters.isEmpty() == true)
				letters = null;
		} else
			letters = null;
		this.setVersion(letters);
	}
	
	public PageId() { }

	/**
	 * Returns the number component of the ID.
	 * @return the number component
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Returns the version component of the ID.
	 * @return the version component
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Sets the number component of the ID. Only positive numbers are valid.
	 * @param number the new number component for the ID
	 */
	public void setNumber(int number) {
		if (number > 0)
			this.number = number;
		else
			this.number = 0;
	}
	
	/**
	 * Sets the version component of the ID. Only a String of letters is valid.
	 * @param version the new version component for the ID
	 */
	public void setVersion(String version) {
		final boolean isValid = isValidVersion(version);
		if (isValid == true)
			this.version = version;
		else
			this.version = null;
	}

	/**
	 * Determines whether a version String is "valid" by checking whether all of
	 * the characters are letters.
	 * @param version the candidate version to validate
	 * @return true if the version is valid, false otherwise
	 */
	private static boolean isValidVersion(final String version) {
		if (version == null)
			return false;
		for (int i = 0; i < version.length(); i++) {
			char ch = version.charAt(i);
			ch = Character.toLowerCase(ch);
			final boolean isCharacterValid = (ch >= 'a' && ch <= 'z');
			if (isCharacterValid == false)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final int number = this.getNumber();
		String version = this.getVersion();
		if (version == null)
			version = "";
		final String string = number + version;
		return string;
	}

	public boolean isValid() {
		final int number = this.getNumber();
		final boolean isValidNumber = (number > 0);
		final String version = this.getVersion();
		final boolean isValidVersion = isValidVersion(version);
		return (isValidNumber && isValidVersion);
	}
	
	
}
