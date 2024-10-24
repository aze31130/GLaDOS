package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpUtils {
	/*
	 * This private constructor hides the implicit public one
	 */
	private HttpUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getIp() {
		try {
			final String url = "https://api.ipify.org?format=text";

			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));

			String result = reader.readLine();

			reader.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Could not get Ip";
	}
}
