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
		try {
			final String url = "https://" + GLaDOS.getInstance().llm + "/api/generate";
			final String prePrompt =
					"Here is a list of cybersecurity news. Please summarize them into a few concise paragraphs of 4000 characters max, merge duplicated news and format them in Markdown. Provide links to the original sources when possible.\n";

			// Build the prompt
			StringBuilder prompt = new StringBuilder(prePrompt);

			for (News news : newsToSumUp)
				prompt.append(news.title() + " - " + news.description() + " Source: " + news.url() + "\n");

			JSONObject requestBody = new JSONObject();
			requestBody.put("model", "mistral");
			requestBody.put("prompt", prompt.toString());
			requestBody.put("stream", false);

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

			return result.getString("response");
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		return "Could not get LLM inference";
	}
}
