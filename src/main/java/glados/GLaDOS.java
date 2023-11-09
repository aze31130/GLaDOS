package glados;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import accounts.Account;
import accounts.Permissions;
import commands.*;
import database.JsonIO;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
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

	public boolean FreeGameAnnonce = false;
	public boolean DailyQuote = false;

	public List<Account> accounts;
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

		if (!config.exists()) {
			FileUtils.createDefaultConfig();
			System.out.println("You have to define your token inside your config file !");
			System.exit(1);
		}

		/*
		 * Initialize command
		 */
		this.commands.add(new Call("call", "Triggers an internal event. Admin privileges required",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.STRING, "trigger",
						"Call name you want to trigger").setRequired(true))));

		this.commands.add(new Clear("clear",
				"Clears the latests messages in a channel. Admin privileges required",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.INTEGER, "amount",
						"Amount of message you want to delete"))));

		this.commands
				.add(new Translate("translate", "Translates the latests messages in a text channel",
						Permissions.NONE, Arrays.asList(new OptionData(OptionType.STRING, "amount",
								"Amount of message you want to translate."))));

		this.commands.add(new Statistics("stats", "Generates statistics regarding a text channel",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.CHANNEL, "target",
						"Channel you want to compute stats of"))));

		this.commands.add(new Ping("ping", "Display ping between Discord gateway and glados",
				Permissions.NONE, Arrays.asList()));

		this.commands.add(new Version("version", "displays version alongside others indicators",
				Permissions.NONE, Arrays.asList()));

		this.commands.add(new CheGuevara("che-guevara", "Generate a random fact about Che-Guevara",
				Permissions.NONE, Arrays.asList()));

		this.commands.add(new Move("move", "Move every voice connected users to another channel",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.CHANNEL,
						"destination", "Channel you want to move in"))));

		this.commands.add(new Spam("spam", "Spam-mention a given user. Admin privileges required",
				Permissions.MODERATOR,
				Arrays.asList(
						new OptionData(OptionType.MENTIONABLE, "target",
								"Person you want to annoy"),
						new OptionData(OptionType.INTEGER, "amount",
								"The amount of mention you want to generate"))));

		this.commands.add(new Shutdown("shutdown", "Gracely Shutdown GLaDOS", Permissions.MODERATOR,
				Arrays.asList()));

		this.commands.add(new Rng("rng",
				"Generate a random number using 'perfect and totally not rigged' random",
				Permissions.NONE,
				Arrays.asList(
						new OptionData(OptionType.INTEGER, "lower_bound",
								"The lower bound is included"),
						new OptionData(OptionType.INTEGER, "upper_bound",
								"The upper bound is included"))));

		this.commands.add(new RandomCat("random-cat", "Displays a cat picture", Permissions.NONE,
				Arrays.asList()));

		this.commands.add(new RandomDog("random-dog", "Display a dog picture", Permissions.NONE,
				Arrays.asList()));

		this.commands.add(new Fibonacci("fibonacci", "Computes given fibonacci number",
				Permissions.NONE, Arrays.asList(
						new OptionData(OptionType.INTEGER, "n", "F(n) you want to compute"))));

		this.commands.add(new Factorielle("factorielle", "Computes given factorial number",
				Permissions.NONE, Arrays.asList(
						new OptionData(OptionType.INTEGER, "n", "F(n) you want to compute"))));

		this.commands.add(new Idea("what-should-i-do", "Use it when you do not know what to do",
				Permissions.NONE, Arrays.asList()));

		this.commands.add(new Help("help", "Shows an help page listing each commands",
				Permissions.NONE, Arrays.asList()));

		this.commands.add(new PictureInverse("picture-inverse",
				"Inverse every colors of a given picture", Permissions.NONE, Arrays.asList()));

		this.commands.add(
				new Profile("profile", "Show a user profile", Permissions.NONE, Arrays.asList()));

		this.commands.add(new Test("test", "Test command, nothing to see here", Permissions.OWNER,
				Arrays.asList()));

		this.commands.add(new Role("role", "Generate buttons for members to clic on.",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.MENTIONABLE, "role",
						"Generates the join/leave button for the given role."))));

		this.commands.add(new Connect("connect", "Summons GLaDOS in a vocal channel",
				Permissions.MODERATOR, Arrays.asList()));

		this.commands.add(new Disconnect("disconnect", "Disconnects GLaDOS from a vocal channel",
				Permissions.MODERATOR, Arrays.asList()));

		this.commands.add(new Backup("backup",
				"Download a backup of the entire server. Admin privileges required",
				Permissions.MODERATOR, Arrays.asList()));

		this.commands.add(new Statistics("statistics", "Generates statistics of the given channel.",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.CHANNEL, "target",
						"The channel you want to inspect"))));

		this.commands.add(new Status("activity", "Updates GLaDOS's activity", Permissions.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "type",
								"Can be [listening, playing, watching, streaming]"),
						new OptionData(OptionType.STRING, "description",
								"The displayed activity"))));

		this.commands
				.add(new State("state", "Updates GLaDOS's state (online, idle, do not disturb)",
						Permissions.NONE, Arrays.asList(new OptionData(OptionType.STRING, "status",
								"Can be [online, idle, dnd]"))));

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
		return null;
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
