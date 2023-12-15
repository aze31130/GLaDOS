package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonIO {
	/*
	 * JSON FORMAT CONVENTION
	 * 
	 * Name: Tag: Nickname: Created: Joined: Level: Experience: TotalExperience: Achievements:
	 * (JsonArray of achievements) Rank: MessageAmount: ReactAmount: ?EmoteAmount: Fame: Favourite
	 * Command: Version: 1
	 */

	/*
	 * JSON FORMAT CONVENTION Achievements: (JsonArray of achievements) MessageAmount: ReactAmount:
	 * ?EmoteAmount:
	 */


	/*
	 * JSONTokener jsonTokener = new JSONTokener(new FileReader("./accounts/test.txt")); JSONObject
	 * jsonObject = new JSONObject(jsonTokener);
	 */

	// UNUSED YET
	public static JSONArray loadJsonArray(String filename) throws Exception {
		if (!filename.endsWith(".json")) {
			filename.concat(".json");
		}

		File file = new File("./" + filename);
		JSONArray json = null;
		if (file.exists()) {
			JSONTokener jsonParser = new JSONTokener(new FileReader("./" + filename));
			json = new JSONArray(jsonParser);
		}
		return json;
	}


	// Function to parse a json
	public static JSONObject loadJsonObject(String path) {
		if (!path.endsWith(".json")) {
			path = path.concat(".json");
		}
		File file = new File("./" + path);
		JSONObject json = null;
		if (file.exists()) {
			try {
				JSONTokener jsonParser = new JSONTokener(new FileReader("./" + path));
				json = new JSONObject(jsonParser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	public static void saveAccount(JSONObject json, String filename) {
		if (!filename.endsWith(".json")) {
			filename = filename.concat(".json");
		}

		if (!isAccountFolderCreated()) {
			createAccountFolder();
		}

		try {
			FileWriter jsonFile = new FileWriter("./data/accounts/" + filename);
			jsonFile.write(json.toString());
			jsonFile.flush();
			jsonFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	// public static List<Account> loadAccounts() {
	// System.out.println(new Logger(true) + "Loading account...");

	// List<Account> accounts = new ArrayList<Account>();

	// if(!JsonIO.isDataFolderCreated()) {
	// JsonIO.createDataFolder();
	// }

	// if(!JsonIO.isAccountFolderCreated()) {
	// JsonIO.createAccountFolder();
	// //Create the basics files ?
	// }

	// File folder = new File("./data/accounts");
	// for (String filename : folder.list()) {
	// accounts.add(loadAccount("./data/accounts/" + filename));
	// }
	// System.out.println(new Logger(true) + "Successfully loaded " + folder.list().length + "
	// accounts");
	// return accounts;
	// }

	// ---------- Checking methods ----------
	// Returns true if the data folder is created
	public static Boolean isDataFolderCreated() {
		File folder = new File("./data/");
		return (folder.exists() && folder.isDirectory());
	}

	// Returns true if the account folder is created
	public static Boolean isAccountFolderCreated() {
		File folder = new File("./data/accounts");
		return (folder.exists() && folder.isDirectory());
	}

	public static boolean isBackupFolderCreated() {
		File folder = new File("media/Backup/");
		return (folder.exists() && folder.isDirectory());
	}

	// ---------- Creating methods ----------
	// Creates the data folder
	public static void createDataFolder() {
		new File("./data/").mkdir();
	}

	// Creates the account folder
	public static void createAccountFolder() {
		new File("./data/accounts").mkdir();
	}
}
