/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

public class FormattedText {

    public enum Format {
        BOLD, BOLD_ITALIC, ITALIC, NONE
    }

    public static List<FormattedText> extractFormattedText(final String input) {
        final List<String> firstList = splitIntoFormatList(input);
        final List<FormattedText> secondList = new ArrayList<FormattedText>();
        for (int i = 0; i < firstList.size(); i++) {
            final FormattedText text = new FormattedText();
            text.setText(firstList.get(i));
            text.setFormat(Format.NONE);
            secondList.add(text);
        }
        for (int i = 0; i < secondList.size(); i++) {
            if (secondList.get(i).getText().equals("**")) {
                int j;
                boolean match = false;
                for (j = i + 2; j < secondList.size(); j++) {
                    if (secondList.get(j).getText().equals("**")) {
                        for (int k = i + 1; k < j; k++) {
                            secondList.get(k).toggleBold();
                        }
                        secondList.remove(j);
                        match = true;
                        break;
                    }
                }
                if (match) {
                    secondList.remove(i);
                }
            }
        }
        for (int i = 0; i < secondList.size(); i++) {
            if (secondList.get(i).getText().equals("**")) {
                secondList.get(i).setText("*");
                final FormattedText duplicate = new FormattedText(
                        secondList.get(i));
                secondList.add(i, duplicate);
            }
        }
        for (int i = 0; i < secondList.size(); i++) {
            if (secondList.get(i).getText().equals("*")) {
                int j;
                boolean match = false;
                for (j = i + 2; j < secondList.size(); j++) {
                    if (secondList.get(j).getText().equals("*")) {
                        for (int k = i + 1; k < j; k++) {
                            secondList.get(k).toggleItalic();
                        }
                        secondList.remove(j);
                        match = true;
                        break;
                    }
                }
                if (match) {
                    secondList.remove(i);
                }
            }
        }

        for (int i = 0; i < (secondList.size() - 1); i++) {
            if (secondList.get(i).getFormat() == secondList.get(i + 1)
                    .getFormat()) {
                final String combined = secondList.get(i).getText()
                        + secondList.get(i + 1).getText();
                secondList.get(i).setText(combined);
                secondList.remove(i + 1);
            }
        }
        return secondList;
    }

    public static void main() {
        test();
    }

    public static FormattedText[] parseFormattedText(final String input) {
        final List<FormattedText> list = extractFormattedText(input);
        final FormattedText[] array = new FormattedText[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static void printTestResults(final String string) {
        final List<FormattedText> list = extractFormattedText(string);
        System.out.print(string + " > ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print("[");
            final FormattedText text = list.get(i);
            if (text.getFormat() == Format.BOLD) {
                System.out.print("B ");
            } else if (text.getFormat() == Format.BOLD_ITALIC) {
                System.out.print("BI ");
            } else if (text.getFormat() == Format.ITALIC) {
                System.out.print("I ");
            }
            System.out.print(text.getText() + "] ");
        }
        System.out.println();
    }

    private static List<String> splitIntoFormatList(final String input) {
        final List<String> list = new ArrayList<String>();
        if (input != null) {
            final char NULL_CHAR = '0';
            char prev = NULL_CHAR;
            String word = "";
            for (int i = 0; i < input.length(); i++) {
                final char curr = input.charAt(i);
                word += curr;
                if (curr == '*') {
                    if (prev == '*') {
                        list.add(word);
                        word = "";
                    } else {
                        word = word.substring(0, word.length() - 1);
                        if (word.equals("") == false) {
                            list.add(word);
                        }
                        word = "*";
                    }
                } else {
                    if (prev == '*') {
                        word = word.substring(0, word.length() - 1);
                        if (word.equals("") == false) {
                            list.add(word);
                        }
                        word = "" + curr;
                    }
                }
                prev = curr;
            }
            if (word.equals("") == false) {
                list.add(word);
            }
        }
        return list;
    }

    private static void test() {
        printTestResults("");			// >
        printTestResults("a");			// a > [a]
        printTestResults("ab"); 		// ab > [ab]
        printTestResults("*a*");		// *a* > [I a]
        printTestResults("*a*b");		// *a*b > [I a] [b]
        printTestResults("*a*b*c*");	// *a*b*c* > [I a] [b] [I c]
        printTestResults("*a");			// *a > [*a]
        printTestResults("**a**");		// **a** > [B a]
        printTestResults("***a***");	// ***a*** > [BI a]
        printTestResults("**a*b**c*");	// **a*b**c* > [B a] [BI b] [I c]
        printTestResults("**a*");		// **a* > [I *a]
        printTestResults("**");			// ** > [**]
        printTestResults("*****");		// ***** > [B *]
    }

    private static Format toggleBoldFormat(final Format format) {
        switch (format) {
        case BOLD:
            return Format.NONE;
        case BOLD_ITALIC:
            return Format.ITALIC;
        case ITALIC:
            return Format.BOLD_ITALIC;
        default:	// Assume Format.NONE
            return Format.BOLD;
        }
    }

    private static Format toggleItalicFormat(final Format format) {
        switch (format) {
        case BOLD:
            return Format.BOLD_ITALIC;
        case BOLD_ITALIC:
            return Format.BOLD;
        case ITALIC:
            return Format.NONE;
        default:	// Assume Format.NONE
            return Format.ITALIC;
        }
    }

    private Format format;

    private String text;

    private FormattedText() {
    }

    private FormattedText(final FormattedText deepCopy) {
        format = deepCopy.getFormat();
        text = deepCopy.getText();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormattedText other = (FormattedText) obj;
        if (format != other.format) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }

    public Format getFormat() {
        return format;
    }

    public String getText() {
        return text;
    }

    public void setFormat(final Format format) {
        this.format = format;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final String text = getText();
        final Format format = getFormat();
        return text + " (" + format + ")";
    }

    private void toggleBold() {
        format = toggleBoldFormat(format);
    }

    private void toggleItalic() {
        format = toggleItalicFormat(format);
    }

}
