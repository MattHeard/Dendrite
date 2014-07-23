package com.deuteriumlabs.dendrite.model;

/**
 *Represents a unique identifier for a story page. The IDs are two-
 *dimensional, where differing page numbers represent different points in the
 *story (or possibly even different stories) while differing versions of the
 *same page number represent different alternatives of the same point in the
 *  story. Both dimensions are theoretically unlimited, intending for
 *  unrestricted expansion of stories by increasing the page numbers and for
 *  unrestricted rewriting of stories by increasing the page versions. As
 *currently implemented, there is an upper limit on the page number because
 *  of the <code>int</code> representation.
 */
public class PageId {
    private static final int MIN_PAGE_NUM = 1;
    private static final int INVALID_PAGE_NUM = 0;
    private int number = -1;
    private String version = null;

    public PageId(String string) {
        if (string != null) {
            String digits = "";
            int i = 0;
            for (; i < string.length(); i++) {
                final char curr = string.charAt(i);
                final boolean isDigit = Character.isDigit(curr);
                if (isDigit == true) {
                    digits += curr;
                } else {
                    break;
                }
            }
            int number = parseInt(digits);
            number = (number < MIN_PAGE_NUM) ? INVALID_PAGE_NUM : number;
            this.setNumber(number);

            String letters = "";
            if (number > 0) {
                for (; i < string.length(); i++) {
                    final char curr = string.charAt(i);
                    final boolean isLetter = Character.isLetter(curr);
                    if (isLetter == true) {
                        letters += curr;
                    } else {
                        break;
                    }
                }
                if (letters.isEmpty() == true) {
                    letters = null;
                }
            } else {
                letters = null;
            }
            this.setVersion(letters);
        }
    }

    private int parseInt(String digits) {
        int number;
        try {
            number = Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            number = INVALID_PAGE_NUM;
        }
        return number;
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
        this.number = (number > INVALID_PAGE_NUM) ? number : INVALID_PAGE_NUM;
    }

    /**
     * Sets the version component of the ID. Only a String of letters is valid.
     * @param version the new version component for the ID
     */
    public void setVersion(String version) {
        final boolean isValid = isValidVersion(version);
        this.version = (isValid == true) ? version : null;
    }

    /**
     * Determines whether a version String is "valid" by checking whether all of
     * the characters are letters.
     * @param version the candidate version to validate
     * @return true if the version is valid, false otherwise
     */
    private static boolean isValidVersion(final String version) {
        if (version == null) {
            return false;
        }
        for (int i = 0; i < version.length(); i++) {
            char ch = version.charAt(i);
            ch = Character.toLowerCase(ch);
            final boolean isCharacterValid = (ch >= 'a' && ch <= 'z');
            if (isCharacterValid == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final int number = this.getNumber();
        String version = this.getVersion();
        version = (version == null) ? "" : version;
        final String string = number + version;
        return string;
    }

    public boolean isValid() {
        final int number = this.getNumber();
        final boolean isValidNumber = (number > INVALID_PAGE_NUM);
        final String version = this.getVersion();
        final boolean isValidVersion = isValidVersion(version);
        return (isValidNumber && isValidVersion);
    }

    /**
     * 
     */
    public void incrementVersion() {
        final String version = this.getVersion();
        final String incrementedVersion = PageId.increment(version);
        this.setVersion(incrementedVersion);
    }

    /**
     * @param version
     * @return
     */
    private static String increment(final String version) {
        final String allButLast;
        if (version.length() > 1) {
            allButLast = version.substring(0, version.length() - 1);
        } else {
            allButLast = "";
        }
        char last = version.charAt(version.length() - 1);
        final String incrementedVersion;
        if (last >= 'a' && last < 'z') {
            incrementedVersion = allButLast + (char) (last + 1);
        } else {
            incrementedVersion = PageId.increment(allButLast) + 'a';
        }
        return incrementedVersion;
    }


}
