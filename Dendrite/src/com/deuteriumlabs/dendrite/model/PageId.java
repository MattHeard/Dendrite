/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

public class PageId {
    private static final int INVALID_PAGE_NUM = 0;
    private static final int MIN_PAGE_NUM = 1;

    private static String increment(final String version) {
        final String allButLast;
        if (version.length() > 1) {
            allButLast = version.substring(0, version.length() - 1);
        } else {
            allButLast = "";
        }
        final char last = version.charAt(version.length() - 1);
        final String incrementedVersion;
        if ((last >= 'a') && (last < 'z')) {
            incrementedVersion = allButLast + (char) (last + 1);
        } else {
            incrementedVersion = PageId.increment(allButLast) + 'a';
        }
        return incrementedVersion;
    }

    private static boolean isValidVersion(final String version) {
        if (version == null) {
            return false;
        }
        for (int i = 0; i < version.length(); i++) {
            char ch = version.charAt(i);
            ch = Character.toLowerCase(ch);
            final boolean isCharacterValid = ((ch >= 'a') && (ch <= 'z'));
            if (isCharacterValid == false) {
                return false;
            }
        }
        return true;
    }

    private int number = INVALID_PAGE_NUM;
    private String version = null;

    public PageId() { }

    public PageId(final String string) {
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
            setNumber(number);

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
            setVersion(letters);
        }
    }

    public int getNumber() {
        return number;
    }

    public String getVersion() {
        return version;
    }

    public void incrementVersion() {
        final String version = getVersion();
        if (version == null) {
            final String message = "A null version cannot be incremented.";
            throw new IllegalStateException(message);
        } else {
            final String incrementedVersion = PageId.increment(version);
            setVersion(incrementedVersion);
        }
    }

    public boolean isValid() {
        final int number = getNumber();
        final boolean isValidNumber = (number > INVALID_PAGE_NUM);
        final String version = getVersion();
        final boolean isValidVersion = isValidVersion(version);
        return (isValidNumber && isValidVersion);
    }

    public void setNumber(final int number) {
        this.number = (number > INVALID_PAGE_NUM) ? number : INVALID_PAGE_NUM;
    }

    public void setVersion(final String version) {
        final boolean isValid = isValidVersion(version);
        this.version = (isValid == true) ? version : null;
    }

    @Override
    public String toString() {
        final int number = getNumber();
        String version = getVersion();
        version = (version == null) ? "" : version;
        final String string = number + version;
        return string;
    }

    private int parseInt(final String digits) {
        int number;
        try {
            number = Integer.parseInt(digits);
        } catch (final NumberFormatException e) {
            number = INVALID_PAGE_NUM;
        }
        return number;
    }
}
