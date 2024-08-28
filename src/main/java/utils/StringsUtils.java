package utils;

import java.util.Locale;

public class StringsUtils {
	public static String escapeHTML(String s) {
		String regex = "<[^>]*>";

		// Replace all HTML tags with an empty string
		return s.replaceAll(regex, "");
	}

	/*
	 * Formats number. For instance, 12345 becomes 12,345. Locale.US is used here to have a comma
	 * separated number.
	 */
	public static String formatNumber(int input) {
		return String.format(Locale.US, "%,d", input);
	}

	/*
	 * Overload for long input
	 */
	public static String formatNumber(long input) {
		return String.format(Locale.US, "%,d", input);
	}
}
