package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.json.JSONObject;
import glados.GLaDOS;
import news.News;

public class HttpUtils implements Logging {
	/*
	 * This private constructor hides the implicit public one
	 */
	private HttpUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String sendHTTPRequest(String url) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return "Unable to retrieve HTTP body";
	}

	public static String sendLLMQuery(List<News> newsToSumUp) {
		LOGGER.info("Processing " + newsToSumUp.size() + " news...");
		try {
			final String url = GLaDOS.getInstance().llm + "/api/generate";
			final String prePrompt =
					"You are given a list of cybersecurity news articles. Summarize them into a single, well-structured paragraph, no longer than 4000 characters. Merge duplicate or similar news stories.";

			// Build the prompt
			StringBuilder prompt = new StringBuilder(prePrompt);

			for (News news : newsToSumUp)
				prompt.append(news.title() + " - " + news.description() + "\n");

			JSONObject requestBody = new JSONObject();
			requestBody.put("model", "mistral");
			requestBody.put("prompt", prompt.toString());
			requestBody.put("stream", false);

			JSONObject options = new JSONObject();
			options.put("num_ctx", 8192);

			requestBody.put("options", options);

			LOGGER.info("Query size " + prompt.length());

			ProcessBuilder builder = new ProcessBuilder("curl", "-k", "-d", "@llm", url);
			Process p = builder.start();
			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String rawResponse = reader.readLine();

			JSONObject result = new JSONObject(rawResponse);

			LOGGER.info("Query OK !");

			return result.getString("response");
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		return "Could not get LLM inference";
	}
}
