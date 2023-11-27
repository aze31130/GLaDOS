package glados;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import accounts.Account;
import accounts.Permissions;
import accounts.TrustFactor;
import commands.*;
import commands.Shutdown;
import database.JsonIO;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import utils.FileUtils;

public class GLaDOS {
	private static volatile GLaDOS instance = null;
	public String version;
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

	// Temp variable
	public String goodAnswer = "";
	public boolean FreeGameAnnonce = false;
	public boolean DailyQuote = false;

	public List<Account> accounts = new ArrayList<>();
	public List<Command> commands = new ArrayList<>();

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

		if (!config.exists()) {
			FileUtils.createDefaultConfig();
			System.out.println("You have to define your token inside your config file !");
			System.exit(1);
		}

		/*
		 * Initialize command
		 */
		Command commands[] = {new Backup(), new Call(), new CheGuevara(), new Clear(),
				new Connect(), new Disconnect(), new Factorielle(), new Fibonacci(), new Help(),
				new Idea(), new Move(), new Ping(), new Play(), new Profile(), new Question(),
				new RandomCat(), new RandomDog(), new Report(), new Rng(), new Role(),
				new Shutdown(), new Spam(), new State(), new Statistics(), new Status(), new Test(),
				new Translate(), new Version()};

		for (Command c : commands)
			this.commands.add(c);

		try {
			// Load the global variables
			JSONObject json = JsonIO.loadJsonObject("./config.json");
			getVersion();
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

			// this.accounts = JsonIO.loadAccounts();


		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// public void backup() {
	// for(Account account : this.accounts) {
	// account.save();
	// }
	// }

	/*
	 * This function registers discord slash commands according to the initialized command list
	 */
	public void registerCommands(JDA jda) {
		if (this.commands.isEmpty())
			return;

		List<SlashCommandData> convertedCommands = new ArrayList<>();

		for (Command c : this.commands) {
			if (c.arguments.isEmpty())
				convertedCommands.add(Commands.slash(c.name, c.description));
			else
				convertedCommands
						.add(Commands.slash(c.name, c.description).addOptions(c.arguments));
		}

		jda.updateCommands().addCommands(convertedCommands).queue();
	}

	public Account getAccountById(String id) {
		// Check if account is registered
		Account result = this.accounts.stream().filter(a -> a.id == id).findFirst().orElse(null);

		// Create the account if not exist
		if (result == null) {
			result = new Account(id, 0, 0, 0, TrustFactor.UNTRUSTED, Permissions.NONE);
			this.accounts.add(result);
		}

		return result;
	}

	/*
	 * Reads the latest commit hash where the project is currently running
	 */
	public void getVersion() {
		try {
			ProcessBuilder builder = new ProcessBuilder("git", "rev-parse", "HEAD");
			Process p = builder.start();
			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			this.version = reader.readLine();

			reader.close();
		} catch (IOException | InterruptedException exception) {
			exception.printStackTrace();
		}
	}
}
