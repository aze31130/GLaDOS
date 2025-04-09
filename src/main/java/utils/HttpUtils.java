package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
		LOGGER.info("Sent LLM inference query...");
		try {
			final String url = GLaDOS.getInstance().llm + "/api/generate";
			final String prePrompt =
					"You are given a list of cybersecurity news articles. Summarize them into a single, well-structured paragraph, no longer than 4000 characters. Merge duplicate or similar news stories. Include the source link for each story when available.";

			// Build the prompt
			StringBuilder prompt = new StringBuilder(prePrompt);

			for (News news : newsToSumUp)
				prompt.append(news.title() + " - " + news.description() + " Source: " + news.url() + "\n");

			JSONObject requestBody = new JSONObject();
			requestBody.put("model", "mistral");
			requestBody.put("prompt", prompt.toString());
			requestBody.put("stream", false);

			JSONObject options = new JSONObject();
			options.put("num_ctx", "4096");

			requestBody.put("options", options);

			// Write request content in a file
			FileUtils.writeRawFile("llm", requestBody.toString());

			ProcessBuilder builder = new ProcessBuilder("curl", "-k", "-d", "@llm", url);
			Process p = builder.start();
			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String rawResponse = reader.readLine();

			// Debug output
			FileUtils.writeRawFile("rawanwser", rawResponse);

			JSONObject result = new JSONObject(rawResponse);

			// Debug output
			FileUtils.writeRawFile("anwserJSON", result.toString());

			LOGGER.info("Query OK !");

			return result.getString("response");
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		return "Could not get LLM inference";
	}
}
