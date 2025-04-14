package utils;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonDownloader {
	public static JSONObject getJson(String url) throws JSONException, IOException {
		return new JSONObject(new JSONTokener(HttpUtils.sendHTTPRequest(url)));
	}
}
