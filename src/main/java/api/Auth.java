package api;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import io.javalin.http.Context;
import io.javalin.http.util.NaiveRateLimit;
import utils.FileUtils;

public class Auth {
	private static final String headerName = "X-API-Key";
	private static JSONArray apiKeys = FileUtils.loadJsonArray("./apiKeys.json");

	public static void checkKey(Context ctx) {
		NaiveRateLimit.requestPerTimeUnit(ctx, 50, TimeUnit.MINUTES);
		String apiKey = ctx.header(headerName);

		if (Objects.isNull(apiKey)) {
			ctx.status(401).result("Unauthorized: Missing " + headerName + " header.");
			return;
		}

		for (int i = 0; i < apiKeys.length(); i++) {
			JSONObject key = apiKeys.getJSONObject(i);

			if (apiKey.equals(key.get("key"))) {
				return;
			}
		}
		ctx.status(401).result("Unauthorized: Invalid API key.");
	}
}
