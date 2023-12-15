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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import utils.FileUtils;

public class GLaDOS {
	private static volatile GLaDOS instance = null;
	public String version;
	public String token;

	// Internal settings
	public boolean leveling, logMessages, checkPrivateMessages, metricLogging;
	public int maxLevel;
	public String guildId, ownerId;

	// Role attributes
	public String roleAdministrator, roleModerator, roleGamer, roleMember,
			roleArtistic, roleBroadcastMessenger, roleInternational, roleDeveloper, roleNsfw;

	// Channel attributes
	public String channelGeneral, channelGamer, channelBotSnapshot, channelNsfw, channelRole,
			channelVote;

	public int requestsAmount;

	public JSONArray bannedWords;

	// Temp variable
	public LocalDateTime translationCooldown;
	public String goodAnswer = "";

	public List<Account> accounts = new ArrayList<>();
	public List<Command> commands = new ArrayList<>();

	private GLaDOS() {}

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
		Command commands[] = {new Backup(), new Call(), new Checksum(), new CheGuevara(),
				new Clear(), new Connect(), new Disconnect(), new Drop(), new Factorielle(),
				new Fibonacci(), new Help(), new Idea(), new Inventory(), new Move(), new Ping(),
				new Play(), new Profile(), new Question(), new RandomCat(), new RandomDog(),
				new Rng(), new Role(), new Shutdown(), new Spam(), new State(), new Statistics(),
				new Status(), new Test(), new Trade(), new Translate(), new Upgrade(),
				new Version(), new Vote()};

		for (Command c : commands)
			this.commands.add(c);

		try {
			// Load the global variables
			JSONObject json = JsonIO.loadJsonObject("./config.json");
			this.getVersion();

			this.leveling = json.getBoolean("leveling");
			this.metricLogging = json.getBoolean("metricLogging");
			this.logMessages = json.getBoolean("logMessages");
			this.checkPrivateMessages = json.getBoolean("checkPrivateMessages");

			this.guildId = json.getString("guildId");
			this.ownerId = json.getString("ownerId");

			// Read role and channels attributes
			this.roleAdministrator = json.getString("role_administrator");
			this.roleModerator = json.getString("role_moderator");
			this.roleGamer = json.getString("role_gamer");
			this.roleMember = json.getString("role_member");
			this.roleArtistic = json.getString("role_artistic");
			this.roleBroadcastMessenger = json.getString("role_broadcastMessenger");
			this.roleInternational = json.getString("role_international");
			this.roleDeveloper = json.getString("role_developer");
			this.roleNsfw = json.getString("role_nsfw");

			this.channelGeneral = json.getString("channel_general");
			this.channelGamer = json.getString("channel_gamer");
			this.channelBotSnapshot = json.getString("channel_botSnapshot");
			this.channelNsfw = json.getString("channel_nsfw");
			this.channelRole = json.getString("channel_role");
			this.channelVote = json.getString("channel_vote");

			this.bannedWords = json.getJSONArray("bannedWords");
			this.token = json.getString("token");
			this.maxLevel = json.getInt("maxLevel");
			this.requestsAmount = 0;
			this.translationCooldown = LocalDateTime.now();

			// this.accounts = JsonIO.loadAccounts();
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
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

	/*
	 * Returns given account. Create if not exist
	 */
	public Account getAccountById(Member m) {
		// Check if account is registered
		Account result =
				this.accounts.stream().filter(a -> a.id == m.getId()).findFirst().orElse(null);

		// Create the account if not exist
		if (result == null) {
			result = new Account(m.getId(), m, 0, 0, 0, TrustFactor.UNTRUSTED, Permissions.NONE);
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
