package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonDownloader {
	public static JSONObject getJson(String url) throws JSONException, IOException {
		return new JSONObject(new JSONTokener(new BufferedReader(
				new InputStreamReader(new URL(url).openConnection().getInputStream()))));
	}
}
