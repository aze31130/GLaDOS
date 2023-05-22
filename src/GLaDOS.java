package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Account;
import commands.*;
import database.JsonIO;
import ranking.Ranking;
import constants.Constants;

import utils.FileUtils;

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
	public LocalDateTime translationCooldown;
	
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
		File config = new File("./settings.json");

		//Create config file is not present
		if (!config.exists()) {
			FileUtils.createDefaultConfig();
			System.out.println("You have to define your token inside the " + constants.Constants.Config_File + " file !");
			System.exit(1);
		}

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
			this.translationCooldown = LocalDateTime.now();
			
			//Initialize accounts
			if(leveling) {
				this.accounts = JsonIO.loadAccounts();
			}
			
			//Initialize command
			this.commands.add(new Call("call", "call", "call command", "example", false, 1));
			this.commands.add(new Translate("translate", "tr", "translate command", "example", false, 1));
			this.commands.add(new Statistics("stats", "s", "stats command", "example", false, 1));
			this.commands.add(new Ping("ping", "p", "ping command", "example", false, 1));
			this.commands.add(new Clear("clear", "c", "clear command", "example", false, 1));
			this.commands.add(new RandomMeme("random-meme", "rm", "random meme command", "example", false, 1));
			this.commands.add(new Version("version", "v", "version command", "example", false, 1));
			this.commands.add(new CheGuevara("che-guevara", "cg", "che guevara command", "example", false, 1));
			this.commands.add(new Status("activity", "at", "activity command", "example", false, 1));
			this.commands.add(new State("state", "st", "state command", "example", false, 1));
			this.commands.add(new Move("move", "mv", "move command", "example", false, 1));
			this.commands.add(new Spam("spam", "sp", "spam command", "example", false, 1));
			this.commands.add(new Shutdown("shutdown", "off", "shutdown command", "example", false, 2));
			this.commands.add(new Rng("rng", "rng", "rng command", "example", false, 2));
			this.commands.add(new RandomCat("random-cat", "rc", "cat command", "example", false, 2));
			this.commands.add(new RandomDog("random-dog", "rd", "dog command", "example", false, 2));
			this.commands.add(new Fibonacci("fibonacci", "fibo", "fibo command", "example", false, 2));
			this.commands.add(new Factorielle("factorielle", "facto", "facto command", "example", false, 2));
			this.commands.add(new Idea("what-should-i-do", "wsid", "idea command", "example", false, 2));
			this.commands.add(new Help("help", "h", "help command", "example", false, 2));
			this.commands.add(new PictureInverse("picture-inverse", "pi", "picture inverse command", "example", false, 2));
			this.commands.add(new Profile("profile", "p", "profile command", "example", false, 2));
			this.commands.add(new Test("test", "p", "profile command", "example", false, 2));
		} catch (Exception e) {
			System.err.println("Error ! The given " + constants.Constants.Config_File + " is invalid !");
			System.exit(1);
		}
	}

	public void executeCommand(String name, Argument args) {
		for(Command command : GLaDOS.getInstance().commands) {
			if(name.equalsIgnoreCase(command.name) || name.equalsIgnoreCase(command.alias)) {
				command.execute(args);
				return;
			}
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
		
		if(accountId.contains("<") || accountId.contains(">") || accountId.contains("@")) {
			accountId = accountId.replace("<", "").replace("@", "").replace("!", "").replace(">", "");
		}
		
		if(this.accounts != null) {
			for(Account a : this.accounts) {
				if(a.id.equals(accountId)) {
					return a;
				}
			}
		}

		return null;
	}
	
	@Override
	public String toString() {
		return "Version: " + this.version;
	}
}
