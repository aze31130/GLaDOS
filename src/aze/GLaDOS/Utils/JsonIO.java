package aze.GLaDOS.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import aze.GLaDOS.Constants;
import net.dv8tion.jda.api.JDA;

public class JsonIO {
	
	/* JSON FORMAT CONVENTION
	 * 
	 * Name:
	 * Tag:
	 * Nickname:
	 * Created:
	 * Joined:
	 * Level:
	 * Experience:
	 * TotalExperience:
	 * Achievements: (JsonArray of achievements)
	 * Rank:
	 * MessageAmount:
	 * ReactAmount:
	 * ?EmoteAmount:
	 * Fame:
	 * Favourite Command:
	 * Version: 1
	 */
	
	
	/*
	 * JSONTokener jsonTokener = new JSONTokener(new FileReader("./accounts/test.txt"));
	 * JSONObject jsonObject = new JSONObject(jsonTokener);
	 */
	
	public static JSONArray loadJsonArray(String filename) throws Exception {
		if(!filename.endsWith(".json")) {
			filename.concat(".json");
		}
		
		File file = new File("./" + filename);
		JSONArray json = null;
		if(file.exists()) {
			JSONTokener jsonParser = new JSONTokener(new FileReader("./" + filename));
			json = new JSONArray(jsonParser);
		}
		return json;
	}
	
	public static JSONObject loadJsonObject(String path) throws Exception {
		if(!path.endsWith(".json")) {
			path.concat(".json");
		}
		
		File file = new File("./" + path);
		JSONObject json = null;
		if(file.exists()) {
			JSONTokener jsonParser = new JSONTokener(new FileReader("./" + path));
			json = new JSONObject(jsonParser);
		}
		return json;
	}
	
	public static void saveAccount(JSONObject json, String filename) {
		if(!filename.endsWith(".json")) {
			filename = filename.concat(".json");
		}
		
		if(!isAccountFolderCreated()) {
			createAccountFolder();
		}
		
		try {
			FileWriter jsonFile = new FileWriter("./accounts/" + filename);
			jsonFile.write(json.toString());
			jsonFile.flush();
			jsonFile.close();
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private static boolean isAccountFolderCreated() {
		File accountFolder = new File("./accounts/");
		return (accountFolder.exists() && accountFolder.isDirectory());
	}
	
	private static void createAccountFolder() {
		new File("./accounts/").mkdir();
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
	
	public static void backupAccounts() {
		//copie les fichiers monté en ram dans la clef usb
	}
	
	public static void loadAccounts() {
		String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
		System.out.println(time + " Loading account...");
		if(JsonIO.isBackupFolderMounted()) {
			int acountAmount = 0;
			File folder = new File("./backup");
			for (String filenames : folder.list()) {
				System.out.println(filenames);
				//copier tout les fichier vers /home/tmp +
				acountAmount++;
			}
			System.out.println(time + " Successfully loaded " + acountAmount + " accounts");
			Constants.isAccountLoaded = true;
		} else {
			System.err.println(time + " Error while loading account. GLaDOS will run without the accounts !");
			Constants.isAccountLoaded = false;
		}
	}
	

	
	public static boolean isBackupFolderMounted() {
		File folder = new File("./backup/");
		return (folder.exists() && folder.isDirectory());
	}
	
	public static int accountAmount() {
		return new File("./accounts").list().length;
	}
}
