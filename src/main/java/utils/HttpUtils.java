package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
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

	public static String getIp() {
		try {
			final String url = "https://api.ipify.org?format=text";

			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));

			String result = reader.readLine();

			reader.close();
			return result;
		} catch (IOException e) {
			LOGGER.severe(e.toString());
		}
		return "Could not get Ip";
	}

	public static String sendLLMQuery(List<News> newsToSumUp) {
		try {
			final String url = "https://" + GLaDOS.getInstance().llm + "/api/generate";
			final String prePrompt = "Hello, I have the following cybersecurity news. Can you synthetise all in a few paragraphs ?\n\n";

			// Build the prompt
			StringBuilder prompt = new StringBuilder(prePrompt);

			for (News news : newsToSumUp)
				prompt.append(news.title() + " - " + news.description() + " Source: " + news.source() + "\n");

			JSONObject requestBody = new JSONObject();
			requestBody.put("model", "phi4");
			requestBody.put("prompt", prompt.toString());
			requestBody.put("stream", false);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
					.build();

			// Send the request and get the response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			return response.body();
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		return "Could not get LLM inference";
	}
}
