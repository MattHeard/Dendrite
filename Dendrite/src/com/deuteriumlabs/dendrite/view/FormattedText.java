package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

public class FormattedText {

	public enum Format {
		BOLD, BOLD_ITALIC, ITALIC, NONE
	}

	public static List<FormattedText> extractFormattedText(String input) {
		List<String> firstList = splitIntoFormatList(input);
		List<FormattedText> secondList = new ArrayList<FormattedText>();
		for (int i = 0; i < firstList.size(); i++) {
			FormattedText text = new FormattedText();
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
				FormattedText duplicate = new FormattedText(secondList.get(i));
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
		
		for (int i = 0; i < secondList.size() - 1; i++) {
			if (secondList.get(i).getFormat() == secondList.get(i + 1).getFormat()) {
				String combined = secondList.get(i).getText() + secondList.get(i + 1).getText();
				secondList.get(i).setText(combined);
				secondList.remove(i + 1);
			}
		}
		return secondList;
	}

	// Called in 'read.jsp'
	public static FormattedText[] parseFormattedText(String input) {
		List<FormattedText> list = extractFormattedText(input);
		FormattedText[] array = new FormattedText[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	private static void printTestResults(String string) {
		List<FormattedText> list = extractFormattedText(string);
		System.out.print(string + " > ");
		for (int i = 0; i < list.size(); i++) {
			System.out.print("[");
			FormattedText text = list.get(i);
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
	
	private static List<String> splitIntoFormatList(String input) {
		List<String> list = new ArrayList<String>();
		if (input != null) {
			final char NULL_CHAR = '0';
			char prev = NULL_CHAR;
			String word = "";
			for (int i = 0; i < input.length(); i++) {
				char curr = input.charAt(i);
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
		printTestResults("");			//   >
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
	
	public static void main() {
		test();
	}

	private static Format toggleBoldFormat(Format format) {
		switch (format){
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
	
	private static Format toggleItalicFormat(Format format) {
		switch (format){
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

	private FormattedText() { }
	
	private FormattedText(FormattedText deepCopy) {
		this.format = deepCopy.getFormat();
		this.text = deepCopy.getText();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormattedText other = (FormattedText) obj;
		if (format != other.format)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public Format getFormat() {
		return format;
	}

	public String getText() {
		return text;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public void setText(String text) {
		this.text = text;
	}

	private void toggleBold() {
		this.format = toggleBoldFormat(this.format);
	}

	private void toggleItalic() {
		this.format = toggleItalicFormat(this.format);
	}
}
