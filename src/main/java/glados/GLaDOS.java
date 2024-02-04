package glados;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import accounts.*;
import commands.*;
import items.Item;
import items.ItemType;
import items.Rarity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import utils.BuildEmbed;
import utils.FileUtils;
import utils.ItemUtils;
import utils.Logging;

public class GLaDOS implements Logging {
	private static volatile GLaDOS instance = null;
	public String version, token;

	// Internal settings
	public boolean leveling, checkPrivateMessages, metricLogging;
	public int maxLevel, maxExpPerDay;
	public String guildId, ownerId;

	// Role attributes
	public String roleAdministrator, roleModerator, roleGamer, roleMember, roleArtistic, roleBroadcastMessenger,
			roleInternational, roleDeveloper, roleNsfw;

	// Channel attributes
	public String channelGeneral, channelGamer, channelBotSnapshot, channelNsfw, channelRole, channelVote, channelBackup,
			channelSystem;

	public int requestsAmount;

	public JSONArray bannedWords;

	public JSONArray epicgameQuotes, joinQuote, leaveQuote, randomQuote;

	// Temp variable
	public LocalDateTime translationCooldown;
	public String goodAnswer = "";

	public List<Account> accounts = new ArrayList<>();
	public List<Command> commands = new ArrayList<>();
	public List<Item> items = new ArrayList<>();
	// Optimisation, holds the sum of each drop weight so that we avoid recalculating it
	public double itemTotalProb = 0;

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
			LOGGER.severe("You have to define your token inside your config file !");
			System.exit(1);
		}

		/*
		 * Initialize command
		 */
		Command commands[] = {new Backup(), new Trigger(), new Checksum(), new CheGuevara(), new Clear(), new Delete(),
				new Disconnect(), new Drop(), new Factorielle(), new Fibonacci(), new Give(), new Help(), new Idea(),
				new Inventory(), new commands.Item(), new Move(), new Ping(), new Play(), new Profile(), new Question(),
				new RandomCat(), new RandomDog(), new Reroll(), new Rng(), new Role(), new Sell(), new Shutdown(), new Spam(),
				new State(), new Statistics(), new Status(), new Trade(), new Translate(), new Upgrade(), new Version(),
				new Vote()};

		for (Command c : commands)
			this.commands.add(c);

		this.loadItems();

		try {
			// Load quotes
			this.epicgameQuotes = FileUtils.loadJsonArray("./epicgameQuote.json");
			this.joinQuote = FileUtils.loadJsonArray("./joinQuote.json");
			this.leaveQuote = FileUtils.loadJsonArray("./leaveQuote.json");
			this.randomQuote = FileUtils.loadJsonArray("./randomAnswer.json");

			// Load the global variables
			JSONObject json = FileUtils.loadJsonObject("./config.json");
			this.getVersion();

			this.leveling = json.getBoolean("leveling");
			this.metricLogging = json.getBoolean("metricLogging");
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
			this.channelBackup = json.getString("channel_backup");
			this.channelSystem = json.getString("channel_system");

			this.bannedWords = json.getJSONArray("bannedWords");
			this.token = json.getString("token");

			this.maxExpPerDay = json.getInt("maxExpPerDay");
			this.maxLevel = json.getInt("maxLevel");
			this.requestsAmount = 0;
			this.translationCooldown = LocalDateTime.now();

		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 * Reads all item data contained in all json files
	 */
	public void loadItems() {
		LOGGER.log(Level.INFO, "Loading Items...");

		List<JSONObject> itemsJson = new ArrayList<>();

		FileUtils.loadItems(new File("classes/items"), itemsJson);

		for (JSONObject itemJson : itemsJson) {
			Item i = new Item(
					itemJson.getInt("id"),
					itemJson.getString("name"),
					itemJson.getEnum(ItemType.class, "type"),
					itemJson.getString("lore"),
					itemJson.getEnum(Rarity.class, "rarity"),
					itemJson.getDouble("dropChance"),
					0,
					itemJson.getInt("starForceMaxLevel"),
					itemJson.getBoolean("claimable"),
					itemJson.getBoolean("untradable"),
					false,
					1.0,
					itemJson.getInt("value"));

			// Check that there are no duplicate item id
			if (this.items.stream().anyMatch(x -> x.id == i.id)) {
				LOGGER.warning("Duplicate item id detected ! Skiping " + itemJson.toString());
				continue;
			}

			this.items.add(i);
			this.itemTotalProb += i.dropChance;
		}

		LOGGER.info("Loaded " + this.items.size() + " items.");
		LOGGER.info("Last ID is " + this.getLastItemId());
		ItemUtils.generateItemChartDropRate();
	}

	/*
	 * Returns the item given its id.
	 */
	public Optional<Item> getItemById(int itemId) {
		return this.items.stream().filter(it -> it.id == itemId).findFirst();
	}

	/*
	 * Returns the item given its FQ name
	 */
	public Optional<Item> getItemByFQName(String fqname) {
		return this.items.stream().filter(it -> it.getFQName().equals(fqname)).findFirst();
	}

	/*
	 * Returns the item given its name
	 */
	public Optional<Item> getItemByName(String name) {
		return this.items.stream().filter(it -> it.name.equals(name)).findFirst();
	}

	public int getLastItemId() {
		Optional<Item> lastItem = this.items.stream().max((i1, i2) -> Integer.compare(i1.id, i2.id));

		if (lastItem.isEmpty())
			return 0;
		return lastItem.get().id;
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
				convertedCommands.add(Commands.slash(c.name, c.description).addOptions(c.arguments));
		}

		jda.updateCommands().addCommands(convertedCommands).queue();
	}

	public void loadAccounts(JDA jda) {
		LOGGER.info("Downloading account file from discord...");

		Message latestMessage = jda.getTextChannelById(this.channelBackup).getHistory().retrievePast(1).complete().get(0);

		if (!latestMessage.getAuthor().isBot()) {
			jda.getTextChannelById(this.channelSystem)
					.sendMessageEmbeds(BuildEmbed.errorEmbed("Inventory sender is not a bot. Aborting loading.").build()).queue();
			return;
		}

		Attachment attachment = latestMessage.getAttachments().get(0);

		try {
			URL fileUrl = new URL(attachment.getUrl());
			Files.copy(fileUrl.openStream(), Paths.get("accounts.json"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOGGER.info("Loading account from file...");
		JSONArray jsonAccounts = FileUtils.loadJsonArray("accounts.json");

		for (int i = 0; i < jsonAccounts.length(); i++) {
			JSONObject jsonAccount = jsonAccounts.getJSONObject(i);
			JSONArray jsonInventory = jsonAccount.getJSONArray("inventory");
			List<Item> userInventory = new ArrayList<>();

			for (int j = 0; j < jsonInventory.length(); j++) {
				int itemId = jsonInventory.getJSONObject(j).getInt("id");
				int itemStarForceLevel = jsonInventory.getJSONObject(j).getInt("starForceLevel");
				Boolean itemBroken = jsonInventory.getJSONObject(j).getBoolean("broken");
				Double itemQuality = jsonInventory.getJSONObject(j).getDouble("quality");

				Optional<Item> item = this.getItemById(itemId);

				if (item.isEmpty()) {
					LOGGER.warning("Skipped unknown item id in inventory " + jsonAccount);
					continue;
				}

				Item it = item.get();
				it.starForceLevel = itemStarForceLevel;
				it.broken = itemBroken;
				it.quality = itemQuality;
				userInventory.add(it);
			}

			Account a = new Account(
					jsonAccount.getString("id"),
					jda.getUserById(jsonAccount.getString("id")),
					jsonAccount.getInt("level"),
					jsonAccount.getInt("experience"),
					jsonAccount.getInt("totalExperience"),
					jsonAccount.getEnum(TrustFactor.class, "trustFactor"),
					jsonAccount.getEnum(Permission.class, "permission"),
					userInventory,
					jsonAccount.getBoolean("canDrop"),
					jsonAccount.getLong("money"));

			this.accounts.add(a);
		}

		LOGGER.info("Loaded " + this.accounts.size() + " accounts.");
	}

	/*
	 * Returns given account. Create if not exist
	 */
	public Account getAccount(User u) {
		// Check if account is registered
		Account result = this.accounts.stream().filter(a -> a.id.equals(u.getId())).findFirst().orElse(null);

		// Create the account if not exist
		if (result == null) {
			result = new Account(u.getId(), u, 1, 0, 0, TrustFactor.UNTRUSTED, Permission.NONE, new ArrayList<Item>(), true, 0);
			this.accounts.add(result);
		}

		return result;
	}

	/*
	 * Returns the account associated to the given id. WARNING, if user is not found, it will not create
	 * the account.
	 */
	public Optional<Account> getAccountById(String accountId) {
		return this.accounts.stream().filter(a -> a.id.equals(accountId)).findFirst();
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
