package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;

public class FileUtils {
	/*
	 * This private constructor hides the implicit public one
	 */
	private FileUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void createDefaultConfig() {
		JSONObject defaultConfig = new JSONObject();
		JSONArray bannedWords = new JSONArray();
		defaultConfig.put("version", "1.0.0");
		defaultConfig.put("prefix", "?");
		defaultConfig.put("leveling", false);
		defaultConfig.put("maxLevel", "100");
		defaultConfig.put("bannedWords", bannedWords);
		defaultConfig.put("token", "YOUR_TOKEN_HERE");

		FileUtils.writeRawFile("settings.json", defaultConfig.toString(4));
	}
	
	public static String readRawFile(String filename) {
		try {
			return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Cannot open file " + filename + "! Exiting");
		}
		return new String();
	}
	
	public static void writeRawFile(String filename, String content) {
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch(IOException e) {
			System.err.println("Cannot write to file " + filename + "! Exiting");
		}
	}
}