package utils;

public class StringsUtils {
	public static String escapeHTML(String s) {
		String regex = "<[^>]*>";

		// Replace all HTML tags with an empty string
		return s.replaceAll(regex, "");
	}
}
