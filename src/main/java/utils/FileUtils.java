package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONTokener;
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

		defaultConfig.put("metricLogging", true);
		defaultConfig.put("cdn", "");
		defaultConfig.put("guildId", "");
		defaultConfig.put("ownerId", "");
		defaultConfig.put("channel_general", "");
		defaultConfig.put("channel_gamer", "");
		defaultConfig.put("channel_botSnapshot", "");
		defaultConfig.put("channel_role", "");
		defaultConfig.put("channel_vote", "");
		defaultConfig.put("channel_system", "");

		defaultConfig.put("role_broadcastMessenger", "");
		defaultConfig.put("role_gamer", "");
		defaultConfig.put("role_member", "");
		defaultConfig.put("role_artistic", "");
		defaultConfig.put("role_international", "");
		defaultConfig.put("role_developer", "");
		defaultConfig.put("role_nsfw", "");
		defaultConfig.put("role_moderator", "");
		defaultConfig.put("role_administrator", "");

		defaultConfig.put("bannedWords", bannedWords);

		FileUtils.writeRawFile("config.json", defaultConfig.toString(4));
	}

	public static void loadItems(File folder, List<JSONObject> items) {
		File[] files = folder.listFiles();

		if (files == null)
			return;

		for (File f : files) {
			if (f.isDirectory()) {
				loadItems(f, items);
			} else if (f.getName().endsWith("json")) {
				JSONObject object = loadJsonObject(f.getAbsolutePath());
				items.add(object);
			}
		}
	}

	public static JSONArray loadJsonArray(String filename) {
		if (!filename.endsWith(".json"))
			filename.concat(".json");

		String rawContent = FileUtils.readRawFile(filename);
		JSONTokener jsonParser = new JSONTokener(rawContent);
		JSONArray json = new JSONArray(jsonParser);

		return json;
	}

	public static JSONObject loadJsonObject(String filename) {
		if (!filename.endsWith(".json"))
			filename.concat(".json");

		String rawContent = FileUtils.readRawFile(filename);
		JSONTokener jsonParser = new JSONTokener(rawContent);
		JSONObject json = new JSONObject(jsonParser);

		return json;
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
		} catch (IOException e) {
			System.err.println("Cannot write to file " + filename + "! Exiting");
		}
	}

	public static String sha256sum(String path) throws IOException, NoSuchAlgorithmException {
		byte[] data = Files.readAllBytes(Path.of(path));
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);

		return new BigInteger(1, hash).toString(16);
	}
}
