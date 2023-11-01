package glados;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

import accounts.Permissions;
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
			this.commands
					.add(new Call("call", "Triggers an internal event. Admin privileges required", Permissions.ADMIN));
			this.commands
					.add(new Clear("clear", "Clears the latests messages in a channel. Admin privileges required",
							Permissions.ADMIN));
			this.commands.add(
					new Translate("translate", "Translates the latests messages in a text channel", Permissions.ADMIN));
			this.commands
					.add(new Statistics("stats", "Generates statistics regarding a text channel", Permissions.ADMIN));
			this.commands.add(new Ping("ping", "Display ping between Discord gateway and glados", Permissions.ADMIN));
			this.commands
					.add(new Version("version", "Displays version alongside others indicators", Permissions.ADMIN));
			this.commands
					.add(new CheGuevara("che-guevara", "Generate a random fact about Che-Guevara", Permissions.ADMIN));
			this.commands.add(new Status("activity", "Updates GLaDOS's activity", Permissions.ADMIN));
			this.commands.add(
					new State("state", "Updates GLaDOS's state (online, idle, do not disturb)", Permissions.ADMIN));
			this.commands
					.add(new Move("move", "Move every voice connected users to another channel", Permissions.ADMIN));
			this.commands
					.add(new Spam("spam", "Spam-mention a given user. Admin privileges required", Permissions.ADMIN));
			this.commands.add(new Shutdown("shutdown", "Gracely Shutdown GLaDOS", Permissions.ADMIN));
			this.commands
					.add(new Rng("rng", "Generate a random number using 'perfect and totally not rigged' random",
							Permissions.ADMIN));
			this.commands.add(new RandomCat("random-cat", "Displays a cat picture", Permissions.ADMIN));
			this.commands.add(new RandomDog("random-dog", "Display a dog picture", Permissions.ADMIN));
			this.commands.add(new Fibonacci("fibonacci", "Computes given fibonacci number", Permissions.ADMIN));
			this.commands.add(new Factorielle("factorielle", "Computes given factorial number", Permissions.ADMIN));
			this.commands
					.add(new Idea("what-should-i-do", "Use it when you do not know what to do", Permissions.ADMIN));
			this.commands.add(new Help("help", "Shows an help page listing each commands", Permissions.ADMIN));
			this.commands.add(new PictureInverse("picture-inverse", "Inverse every colors of a given picture",
					Permissions.ADMIN));
			this.commands.add(new Profile("profile", "Show a user profile", Permissions.ADMIN));
			this.commands.add(new Test("test", "Test command, nothing to see here", Permissions.ADMIN));
			this.commands.add(new Role("role", "Generate buttons for members to clic on", Permissions.ADMIN));
			this.commands.add(new Connect("connect", "Invoke GLaDOS in a vocal channel", Permissions.ADMIN));
			this.commands
					.add(new Disconnect("disconnect", "Disconnects GLaDOS from a vocal channel", Permissions.ADMIN));
			this.commands
					.add(new Backup("backup", "Download a backup of the entire server. Admin privileges required",
							Permissions.ADMIN));
			this.commands
					.add(new Statistics("statistics", "Generates statistics of the given channel.", Permissions.ADMIN));

		} catch (Exception e) {
			System.err.println("Error ! The given config file is invalid !");
			System.exit(1);
		}
	}

	public void executeCommand(String name, Argument args) {
		for (Command command : GLaDOS.getInstance().commands) {
			if (name.equalsIgnoreCase(command.name)) {
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
