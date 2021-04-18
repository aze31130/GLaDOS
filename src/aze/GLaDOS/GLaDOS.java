package aze.GLaDOS;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import aze.GLaDOS.Accounts.Account;
import aze.GLaDOS.Ranking.Ranking;
import aze.GLaDOS.Utils.JsonIO;

public class GLaDOS {
	private static volatile GLaDOS instance = null;
	public String version;
	public String prefix;
	public boolean rankingEnabled; 
	public String token;
	public int requestsAmount;
	public Ranking ranking;
	public JSONArray bannedWords;
	public int maxLevel;
	public List<Account> accounts;
	
	private GLaDOS() {
		super();
	}
	
	public final static GLaDOS getInstance() {
		if(GLaDOS.instance == null) {
			synchronized(GLaDOS.class) {
				if (GLaDOS.instance == null) {
					GLaDOS.instance = new GLaDOS();
				}
			}
		}
		return GLaDOS.instance;
	}
	
	public void initialize() {
		//Load with json
		try {
			JSONObject json = JsonIO.loadJsonObject("./settings.json");
			this.version = json.get("version").toString();
			this.prefix = json.get("prefix").toString();
			this.rankingEnabled = (boolean)json.get("rankingEnabled");
			this.bannedWords = (JSONArray) json.get("bannedWords");
			this.token = json.get("token").toString();
			this.maxLevel = json.getInt("maxLevel");
			this.requestsAmount = 0;
		} catch (Exception e) {
			System.err.println("FATAL ! Couldn't read the settings.json file, exiting !");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void addRequest() {
		this.requestsAmount++;
	}
	
	public int getRequests() {
		return this.requestsAmount;
	}
	
	public void updatePrefix(String newPrefix) {
		this.prefix = newPrefix;
	}
	
	public void checkAccounts() {
		//Check the amount of registered accounts and compare it to the member list size
	}
	
	@Override
	public String toString() {
		return "Version: " + this.version;
	}
}
