package glados;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import commands.*;
import commands.Shutdown;
import database.JsonIO;

import utils.FileUtils;

public class GLaDOS {
	private static volatile GLaDOS instance = null;
	public String version;
	public String prefix;
	public boolean leveling;
	public boolean logMessages;
	public boolean checkPrivateMessages;
	public boolean metricLogging;
	public String guildId;
	public String ownerId;

	// Role attributes
	public String roleAdministrator;
	public String roleModerator;
	public String roleGamer;
	public String roleMember;
	public String roleArtistic;
	public String roleBroadcastMessenger;
	public String roleInternational;
	public String roleDeveloper;
	public String roleNsfw;

	// Channel attributes
	public String channelGeneral;
	public String channelGamer;
	public String channelBotSnapshot;
	public String channelNsfw;
	public String channelRole;

	public String token;
	public int requestsAmount;
	public int activityCounter;
	// public Ranking ranking;
	public JSONArray bannedWords;
	public int maxLevel;
	public LocalDateTime translationCooldown;

	public boolean FreeGameAnnonce = false;
	public boolean DailyQuote = false;

	// public List<Account> accounts;
	public List<Command> commands = new ArrayList<Command>();

	private GLaDOS() {
		super();
	}

	public final static GLaDOS getInstance() {
		if (GLaDOS.instance == null) {
			synchronized (GLaDOS.class) {
				if (GLaDOS.instance == null) {
					GLaDOS.instance = new GLaDOS();
				}
			}
		}
		return GLaDOS.instance;
	}

	public void initialize() {
		File config = new File("./config.json");

		// Create config file is not present
		if (!config.exists()) {
			FileUtils.createDefaultConfig();
			System.out.println("You have to define your token inside your config file !");
			System.exit(1);
		}

		try {
			// Load the global variables
			JSONObject json = JsonIO.loadJsonObject("./config.json");
			this.version = json.get("version").toString();
			this.prefix = json.get("prefix").toString();
			this.leveling = (boolean) json.get("leveling");
			this.metricLogging = (boolean) json.get("metricLogging");
			this.logMessages = (boolean) json.get("logMessages");
			this.checkPrivateMessages = (boolean) json.get("checkPrivateMessages");

			this.guildId = json.get("guildId").toString();
			this.ownerId = json.get("ownerId").toString();

			// Read role and channels attributes
			this.roleAdministrator = json.get("role_administrator").toString();
			this.roleModerator = json.get("role_moderator").toString();
			this.roleGamer = json.get("role_gamer").toString();
			this.roleMember = json.get("role_member").toString();
			this.roleArtistic = json.get("role_artistic").toString();
			this.roleBroadcastMessenger = json.get("role_broadcastMessenger").toString();
			this.roleInternational = json.get("role_international").toString();
			this.roleDeveloper = json.get("role_developer").toString();
			this.roleNsfw = json.get("role_nsfw").toString();

			this.channelGeneral = json.get("channel_general").toString();
			this.channelGamer = json.get("channel_gamer").toString();
			this.channelBotSnapshot = json.get("channel_botSnapshot").toString();
			this.channelNsfw = json.get("channel_nsfw").toString();
			this.channelRole = json.get("channel_role").toString();

			this.bannedWords = (JSONArray) json.get("bannedWords");
			this.token = json.get("token").toString();
			this.maxLevel = json.getInt("maxLevel");
			this.requestsAmount = 0;
			this.activityCounter = 0;
			this.translationCooldown = LocalDateTime.now();

			// Initialize accounts
			// if(leveling) {
			// this.accounts = JsonIO.loadAccounts();
			// }

			// Initialize command
			this.commands.add(new Call("call", "call", "call command", "example", false, 1));
			this.commands.add(new Translate("translate", "tr", "translate command", "example", false, 1));
			this.commands.add(new Statistics("stats", "s", "stats command", "example", false, 1));
			this.commands.add(new Ping("ping", "p", "ping command", "example", false, 1));
			this.commands.add(new Clear("clear", "c", "clear command", "example", false, 1));
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
			this.commands
					.add(new PictureInverse("picture-inverse", "pi", "picture inverse command", "example", false, 2));
			this.commands.add(new Profile("profile", "p", "profile command", "example", false, 2));
			this.commands.add(new Test("test", "p", "profile command", "example", false, 2));
			this.commands.add(new Role("role", "r", "role command", "example", false, 2));
			this.commands.add(new Connect("connect", "co", "connect command", "example", false, 2));
			this.commands.add(new Disconnect("disconnect", "do", "disconnect command", "example", false, 2));
			this.commands.add(new Backup("backup", "back", "backup command", "example", false, 2));
			this.commands.add(new Statistics("statistics", "statistics", "Stats command", "example", false, 2));

		} catch (Exception e) {
			System.err.println("Error ! The given config file is invalid !");
			System.exit(1);
		}
	}

	public void executeCommand(String name, Argument args) {
		for (Command command : GLaDOS.getInstance().commands) {
			if (name.equalsIgnoreCase(command.name) || name.equalsIgnoreCase(command.alias)) {
				command.execute(args);
				return;
			}
		}
	}

	// public void backup() {
	// for(Account account : this.accounts) {
	// account.save();
	// }
	// }

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
		// Check the amount of registered accounts and compare it to the member list
		// size
	}

	// public Account getAccount(String accountId) {

	// if(accountId.contains("<") || accountId.contains(">") ||
	// accountId.contains("@")) {
	// accountId = accountId.replace("<", "").replace("@", "").replace("!",
	// "").replace(">", "");
	// }

	// if(this.accounts != null) {
	// for(Account a : this.accounts) {
	// if(a.id.equals(accountId)) {
	// return a;
	// }
	// }
	// }

	// return null;
	// }

	@Override
	public String toString() {
		return "Version: " + this.version;
	}
}
