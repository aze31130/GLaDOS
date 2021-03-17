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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import aze.GLaDOS.Constants;

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
	 * Rank:
	 * MessageAmount:
	 * Fame:
	 * Favourite Command:
	 * Version: 1
	 */
	
	public static JSONArray loadArray(String filename) throws Exception {
		File file = new File("./" + filename + ".json");
		JSONArray json = new JSONArray();
		if(file.exists()) {
			JSONParser jsonParser = new JSONParser();
			json = (JSONArray) jsonParser.parse(new FileReader("./" + filename + ".json"));
		}
		return json;
	}
	
	public static JSONObject load(String filename) throws Exception {
		File file = new File("./" + Constants.SystemFolderName + "/" + filename + ".txt");
		JSONObject json = new JSONObject();
		if(file.exists()) {
			JSONParser jsonParser = new JSONParser();
			json = (JSONObject) jsonParser.parse(new FileReader("./" + Constants.SystemFolderName + "/" + filename + ".txt"));
		}
		return json;
	}
	
	public static void write(JSONObject json, String filename) {
		if(isFolderCreated()) {
			try {
				FileWriter jsonFile = new FileWriter("./" + Constants.SystemFolderName + "/" + filename + ".txt");
				jsonFile.write(json.toJSONString());
				jsonFile.flush();
				jsonFile.close();
			} catch(Exception e) {
				System.err.println(e.toString());
			}
		} else {
			createFolder();
			write(json, filename);
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
	
	public static void backupAccounts() {
		//copie les fichiers monté en ram dans la clef usb
	}
	
	
	public static void loadAccounts() {
		String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
		System.out.println(time + " Loading account...");
		if(JsonIO.isBackupFolderMounted()) {
			int acountAmount = 0;
			File folder = new File(Constants.BackupFolder);
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
	
	public static boolean isFolderCreated() {
		File folder = new File("./" + Constants.SystemFolderName);
		return (folder.exists() && folder.isDirectory());
	}
	
	public static void createFolder() {
		File folder = new File("./" + Constants.SystemFolderName);
		folder.mkdir();
	}
	
	public static boolean isBackupFolderMounted() {
		File folder = new File("./" + Constants.BackupFolder);
		return (folder.exists() && folder.isDirectory());
	}
}
