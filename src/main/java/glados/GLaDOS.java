package glados;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import news.News;
import utils.FileUtils;
import utils.ItemUtils;
import utils.Logging;

public class GLaDOS implements Logging {
	private static volatile GLaDOS instance = null;
	public String version, token, cdn, llm;

	// Internal settings
	public boolean metricLogging;
	public String guildId, ownerId, botId;

	public int requestsAmount;

	public JSONArray roles, channels, bannedWords, epicgameQuotes, quotes, joinQuote, leaveQuote, randomQuote, activities, rssFeeds, birthdays;

	// Temp variable
	public LocalDateTime translationCooldown;
	public String goodAnswer = "";

	public List<Account> accounts = new ArrayList<>();
	public List<Command> commands = new ArrayList<>();
	public List<Item> items = new ArrayList<>();
	public List<Item> market = new ArrayList<>();

	public List<Integer> rssNews = new ArrayList<>();
	public List<News> rssNewsSumUp = new ArrayList<>();

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
		Command commands[] =
				{new Auction(), new Backup(), new Checksum(), new CheGuevara(), new Clear(), new Contract(),
						new Delete(), new Disconnect(), new Drop(), new Factorielle(), new Fibonacci(), new Give(), new Help(),
						new Inventory(), new Ip(), new commands.Item(), new Market(), new Move(), new Ping(), new Play(),
						new Profile(), new Question(), new RandomCat(), new RandomDog(), new Ranking(), new Rng(), new Role(),
						new Sell(), new Shutdown(), new Spam(), new State(), new Status(), new Trade(), new Translate(),
						new Trigger(), new Upgrade(), new Version()};

		for (Command c : commands)
			this.commands.add(c);

		this.loadItems();

		// Init market
		ItemUtils.resetMarket();

		try {
			// Load quotes
			this.epicgameQuotes = FileUtils.loadJsonArray("./epicgameQuote.json");
			this.quotes = FileUtils.loadJsonArray("./quotes.json");
			this.joinQuote = FileUtils.loadJsonArray("./joinQuote.json");
			this.leaveQuote = FileUtils.loadJsonArray("./leaveQuote.json");
			this.randomQuote = FileUtils.loadJsonArray("./randomAnswer.json");
			this.activities = FileUtils.loadJsonArray("./activities.json");

			// Load the global variables
			JSONObject json = FileUtils.loadJsonObject("./config.json");
			JSONObject rssFeeds = FileUtils.loadJsonObject("./feeds.json");
			this.getVersion();

			// Read role and channels attributes
			this.channels = json.getJSONArray("channels");
			this.roles = json.getJSONArray("roles");

			this.metricLogging = json.getBoolean("metricLogging");

			this.cdn = json.getString("cdn");

			this.guildId = json.getString("guildId");
			this.ownerId = json.getString("ownerId");
			this.botId = json.getString("botId");

			this.bannedWords = json.getJSONArray("bannedWords");
			this.rssFeeds = rssFeeds.getJSONArray("rss");
			this.birthdays = json.getJSONArray("birthdays");
			this.token = json.getString("token");
			this.llm = json.getString("llm");

			this.requestsAmount = 0;
			this.translationCooldown = LocalDateTime.now();

		} catch (JSONException e) {
			LOGGER.severe(e.getMessage());
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

	/*
	 * Returns the item list of a given tier
	 */
	public List<Item> getItemsByTier(int rarityLevel) {
		return this.items.stream().filter(it -> (it.rarity.level == rarityLevel) && (rarityLevel < 7)).toList();
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

		List<CommandData> convertedCommands = new ArrayList<>();

		for (Command c : this.commands) {
			for (Type t : c.type) {
				switch (t) {
					case SLASH:
						if (c.arguments.isEmpty())
							convertedCommands.add(Commands.slash(c.name, c.description));
						else
							convertedCommands.add(Commands.slash(c.name, c.description).addOptions(c.arguments));
						break;
					case MESSAGE:
						convertedCommands.add(Commands.message(c.name));
						break;
					case USER:
						convertedCommands.add(Commands.user(c.name));
						break;
					case UNKNOWN:
						break;
				}
			}
		}

		jda.updateCommands().addCommands(convertedCommands).queue();
	}

	public void loadAccounts(JDA jda) {
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

				try {
					Item it = (Item) item.get().clone();
					it.starForceLevel = itemStarForceLevel;
					it.broken = itemBroken;
					it.quality = itemQuality;
					userInventory.add(it);
				} catch (CloneNotSupportedException e) {
					LOGGER.severe("Cannot clone item " + itemId);
					e.printStackTrace();
				}
			}

			Account a = new Account(
					jsonAccount.getString("id"),
					jda.getUserById(jsonAccount.getString("id")),
					jsonAccount.getLong("trustFactorScore"),
					jsonAccount.getEnum(TrustFactor.class, "trustFactor"),
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
			result = new Account(u.getId(), u, 0, TrustFactor.UNTRUSTED, new ArrayList<Item>(), true, 0);
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
	 * Returns the channel id based on its name
	 */
	public Optional<String> getChannelId(String channelName) {
		for (int i = 0; i < this.channels.length(); i++) {
			JSONObject channel = this.channels.getJSONObject(i);

			if (channel.getString("name").equalsIgnoreCase(channelName))
				return Optional.of(channel.getString("id"));
		}
		LOGGER.warning("Channel id " + channelName + " not found !");
		return Optional.empty();
	}

	/*
	 * Returns the role id based on its name
	 */
	public Optional<String> getRoleId(String roleName) {
		for (int i = 0; i < this.roles.length(); i++) {
			JSONObject role = this.roles.getJSONObject(i);

			if (role.getString("name").equalsIgnoreCase(roleName))
				return Optional.of(role.getString("id"));
		}
		LOGGER.warning("Role id " + roleName + " not found !");
		return Optional.empty();
	}

	/*
	 * Reads the latest commit hash where the project is currently running
	 */
	public void getVersion() {
		List<String> filesToTry = Arrays.asList("../.git/refs/heads/master", "master");

		for (String paths : filesToTry) {
			String content = FileUtils.readRawFile(paths).replace("\n", "");

			if (content.length() > 0) {
				this.version = content;
				return;
			}
		}

		this.version = "Unknown";
	}
}
