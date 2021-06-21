package aze.GLaDOS;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.commands.Activities;
import aze.GLaDOS.commands.CheGuevara;
import aze.GLaDOS.commands.Clear;
import aze.GLaDOS.commands.Command;
import aze.GLaDOS.commands.Ping;
import aze.GLaDOS.commands.RandomMeme;
import aze.GLaDOS.commands.State;
import aze.GLaDOS.commands.Version;
import aze.GLaDOS.database.JsonIO;
import aze.GLaDOS.ranking.Ranking;

public class GLaDOS {
	private static volatile GLaDOS instance = null;
	public String version;
	public String prefix;
	public boolean leveling; 
	public String token;
	public int requestsAmount;
	public Ranking ranking;
	public JSONArray bannedWords;
	public int maxLevel;
	
	public List<Account> accounts;
	public List<Command> commands = new ArrayList<Command>();

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
		try {
			//Load the global variables
			JSONObject json = JsonIO.loadJsonObject("./settings.json");
			this.version = json.get("version").toString();
			this.prefix = json.get("prefix").toString();
			this.leveling = (boolean)json.get("leveling");
			this.bannedWords = (JSONArray) json.get("bannedWords");
			this.token = json.get("token").toString();
			this.maxLevel = json.getInt("maxLevel");
			this.requestsAmount = 0;
			
			//Initialize accounts
			if(leveling) {
				this.accounts = JsonIO.loadAccounts();
			}
			
			//Initialize commands
			this.commands.add(new Ping("ping", "p", "ping command", "example", false, 1));
			this.commands.add(new Clear("clear", "c", "clear command", "example", false, 1));
			this.commands.add(new RandomMeme("random-meme", "rm", "random meme command", "example", false, 1));
			this.commands.add(new Version("version", "v", "version command", "example", false, 1));
			this.commands.add(new CheGuevara("che-guevara", "cg", "che guevara command", "example", false, 1));
			this.commands.add(new Activities("activity", "at", "activity command", "example", false, 1));
			this.commands.add(new State("state", "st", "state command", "example", false, 1));
			
			
		} catch (Exception e) {
			System.err.println("FATAL ! Couldn't read the settings.json file, exiting !");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void backup() {
		for(Account account : this.accounts) {
			account.save();
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
	
	public Account getAccount(String accountId) {
		return JsonIO.loadAccount("./data/accounts/" + accountId);
	}
	
	@Override
	public String toString() {
		return "Version: " + this.version;
	}
}
