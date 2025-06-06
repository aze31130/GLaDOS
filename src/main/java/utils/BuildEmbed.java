package utils;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import accounts.Account;
import glados.GLaDOS;
import items.Item;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import news.News;

public class BuildEmbed {
	public static EmbedBuilder gamerEmbed() {
		GLaDOS glados = GLaDOS.getInstance();

		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.BLACK)
				.setAuthor("epicgames.com", "https://store.epicgames.com/fr/free-games/")
				.setTitle(glados.epicgameQuotes.getString(new Random().nextInt(glados.epicgameQuotes.length())))
				.setThumbnail(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Epic_Games_logo.svg/1200px-Epic_Games_logo.svg.png")
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder midnightQuote(String quote, String author) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle(quote)
				.setDescription(author)
				.setColor(Color.CYAN)
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder midnightRankEmbed() {
		EmbedBuilder rankingEmbed = new EmbedBuilder()
				.setColor(Color.MAGENTA)
				.setTitle(":clock12: Midnight Race")
				.setTimestamp(Instant.now());
		return rankingEmbed;
	}

	public static EmbedBuilder joinLeaveEmbed(String mention, Boolean joining) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.CYAN);

		String message = "";
		if (joining)
			message = glados.joinQuote.getString(new Random().nextInt(glados.joinQuote.length()));
		else
			message = glados.leaveQuote.getString(new Random().nextInt(glados.leaveQuote.length()));

		embed.setDescription(message.replace("[member]", mention));
		return embed;
	}

	public static EmbedBuilder errorEmbed(String description) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Error")
				.setDescription(description)
				.setColor(Color.RED);
		return embed;
	}

	public static EmbedBuilder successEmbed(String description) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Success")
				.setDescription(description)
				.setColor(Color.GREEN);
		return embed;
	}

	public static EmbedBuilder upgradeSuccessEmbed(int level) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle(":sparkles: Success")
				.setDescription("Upgraded to  " + level + " :star:")
				.setImage(glados.cdn + "/SUCCESS.png")
				.setColor(Color.GREEN);
		return embed;
	}

	public static EmbedBuilder upgradeKeepEmbed(int level) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Failed ! (Keep)")
				.setDescription("Keep " + level + " :star:")
				.setImage(glados.cdn + "/FAIL1.png")
				.setColor(Color.ORANGE);
		return embed;
	}

	public static EmbedBuilder upgradeFailEmbed(int level) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Failed ! (Down)")
				.setDescription("Downgraded to " + level + " :star:")
				.setImage(glados.cdn + "/FAIL2.png")
				.setColor(Color.RED);
		return embed;
	}

	public static EmbedBuilder upgradeDestroyEmbed() {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Destroyed !")
				.setDescription("The item is now broken an not upgradable anymore !")
				.setImage(glados.cdn + "/DESTROYED.png")
				.setColor(Color.BLACK);
		return embed;
	}

	public static EmbedBuilder profileEmbed(Account account, GLaDOS g) {
		int totalItemAmount = g.items.size();
		int totalUniqueOwnedItem = (int) account.inventory.stream().map(item -> item.id).distinct().count();

		EmbedBuilder embed = new EmbedBuilder()
				.setTitle(account.user.getName() + "'s profile")
				.setDescription(account.user.getAsMention())
				.setColor(Color.GREEN)
				.setThumbnail(account.user.getAvatarUrl())
				.addField(":shield: - Trust Factor", "WIP", true)
				.addField(":clock: :timer: - Joined Discord", account.user.getTimeCreated().toString(), true)
				.addField(":coin: - Money", StringsUtils.formatNumber(account.money), true)
				.addField(":moneybag: - Inventory value", StringsUtils.formatNumber(account.getInventoryValue()), true)
				.addField(":briefcase: - Items", Integer.toString(account.inventory.size()), true)
				.addField(":notepad_spiral: - Collection",
						totalUniqueOwnedItem + " / " + totalItemAmount + " (" + (totalUniqueOwnedItem * 100 / totalItemAmount) + "%)", true)
				.addField(":trophy: - Achievements", "WIP", true)
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder questionEmbed(String question, String category, String difficulty) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle(category)
				.setDescription(question)
				.setTimestamp(Instant.now());

		switch (difficulty) {
			case "easy":
				embed.setColor(Color.GREEN);
				break;
			case "normal":
				embed.setColor(Color.ORANGE);
				break;
			case "hard":
				embed.setColor(Color.RED);
				break;
			default:
				embed.setColor(Color.GRAY);
				break;
		}

		return embed;
	}

	public static EmbedBuilder modalEmbed(String subject, String description, String reactions, User author) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle(subject)
				.setDescription(description)
				.setColor(Color.WHITE)
				.setAuthor(author.getName(), author.getAvatarUrl(), author.getAvatarUrl())
				.addField("Voting procedure", reactions, false)
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder hashEmbed(String result, String algorithm) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Result")
				.addField(algorithm, result, false)
				.setColor(Color.GRAY)
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder tradeEmbed(Account author, Account target, Item srcItem,
			int srcMoney, Item dstItem, int dstMoney) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Trade Offer")
				.setDescription(author.user.getAsMention() + "=>" + target.user.getAsMention())
				.addField("Item source", srcItem.getFQName(), true)
				.addField(srcItem.rarity.emote + " " + srcItem.rarity.name(), "", true)
				.addField("Money source", Integer.toString(srcMoney), false)
				.addBlankField(false)
				.addField("Item destination", dstItem.getFQName(), true)
				.addField(dstItem.rarity.emote + " " + dstItem.rarity.name(), "", true)
				.addField("Money destination", Integer.toString(dstMoney), false)
				.setColor(Color.ORANGE)
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder moneyDropEmbed(User dropper, int claimedMoney, long totalMoney) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Claimed !")
				.setAuthor(dropper.getName(), dropper.getAvatarUrl(), dropper.getAvatarUrl())
				.setColor(Color.ORANGE)
				.setDescription("Got " + claimedMoney + " (" + totalMoney + " total)")
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder itemDropEmbed(User dropper, Item item, String cdn) {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("You got:")
				.setAuthor(dropper.getName(), dropper.getAvatarUrl(), dropper.getAvatarUrl())
				.setImage(cdn + "/" + item.id + ".png")
				.setColor(item.rarity.color)
				.setDescription("[**" + item.rarity.name().toLowerCase() + "**] " + item.getFQName())
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder loggerEmbed(String action, String content) {
		EmbedBuilder event = new EmbedBuilder()
				.setTitle(action)
				.setColor(Color.GRAY)
				.setDescription(content)
				.setTimestamp(Instant.now());
		return event;
	}

	public static EmbedBuilder itemInfoEmbed(Item i) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder info = new EmbedBuilder()
				.setTitle("[" + i.rarity.name() + "] " + i.name)
				.setDescription(i.lore)
				.setImage(glados.cdn + "/" + i.id + ".png")
				.setColor(i.rarity.color);

		if (i.untradable)
			info.addField(":no_entry:  Untradable", "Trade banned", false);
		if (i.starForceMaxLevel > 0)
			info.addField(":star2: Max StarForce", Integer.toString(i.starForceMaxLevel), false);
		info.addField(i.type.emote + " Type", i.type.toString().toLowerCase(), false);
		info.addField(":coin: Value", Integer.toString(i.getValue()), false);
		info.addField(":four_leaf_clover: Drop Chance", String.format("%.3f%%", 100 * (i.dropChance / glados.itemTotalProb)),
				false);
		info.setTimestamp(Instant.now());
		return info;
	}

	public static EmbedBuilder inventoryEmbed(Account sender, int pageNumber) {
		int totalPages = (int) Math.ceil((double) sender.inventory.size() / ItemUtils.AMOUNT_ITEM_PER_PAGE);

		EmbedBuilder inventory = new EmbedBuilder()
				.setTitle("Your inventory")
				.setDescription("You have " + StringsUtils.formatNumber(sender.money) + " :coin:")
				.setAuthor(sender.user.getId())
				.setThumbnail(sender.user.getAvatarUrl())
				.setColor(Color.GRAY)
				.setFooter(pageNumber + " / " + totalPages)
				.setTimestamp(Instant.now());
		return inventory;
	}

	public static EmbedBuilder itemChartEmbed() {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle("Drop rates information")
				.setDescription("This list is dynamically generated, theses are the actuals rates currently in use.")
				.setColor(Color.YELLOW)
				.setTimestamp(Instant.now());
		return result;
	}

	public static EmbedBuilder ownerEmbed(Item i) {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle("Users owning " + i.name)
				.setDescription(i.rarity.emote + " [" + i.rarity.toString() + "] " + i.getFQName())
				.setColor(Color.LIGHT_GRAY)
				.setTimestamp(Instant.now());
		return result;
	}

	public static EmbedBuilder upgradeEmbed(Account author, Item i) {
		EmbedBuilder upgrade = new EmbedBuilder()
				.setAuthor(author.user.getName())
				.setTitle(i.getFQName())
				.setDescription("You have " + StringsUtils.formatNumber(author.money) + " :coin:")
				.addField("Upgrade", i.starForceLevel + " :star: => " + (i.starForceLevel + 1) + " :star2: ", true)
				.addField(":moneybag: Cost", StringsUtils.formatNumber(i.getStarForceCost()), true)
				.addField(":chart_with_upwards_trend: Success", i.getStarForceSuccessChance() + " %", true)
				.addField(":chart_with_downwards_trend: Fail (keep)", i.getStarForceKeepChance() + " %", true)
				.addField(":chart_with_downwards_trend: Fail (decrease)", i.getStarForceFailChance() + " %", true)
				.addField(":boom: Boom", i.getStarForceDestroyChance() + " %", true)
				.setColor(Color.ORANGE)
				.setTimestamp(Instant.now());
		return upgrade;
	}

	public static EmbedBuilder cheGuevaraEmbed(String meme) {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle("Che Guevara")
				.setThumbnail(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png")
				.setDescription(meme)
				.setColor(Color.BLUE)
				.setTimestamp(Instant.now());
		return result;
	}

	public static EmbedBuilder rssNewsEmbed(News news) {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle(news.title())
				.setDescription(StringsUtils.escapeHTML(news.description()))
				.setFooter(news.source() + " - " + news.pubDate())
				.setColor(Color.decode("#6460AA"));

		result.addField("", news.url(), false);

		return result;
	}

	public static EmbedBuilder marketEmbed(List<Item> items) {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle("Market")
				.setDescription("Use /buy <item> to buy a given item")
				.setColor(Color.ORANGE)
				.setTimestamp(Instant.now());

		for (Item i : items)
			result.addField(i.rarity.emote + " " + i.rarity.toString() + " - " + i.getValue() + " :coin:  ", i.getFQName(), false);

		return result;
	}

	public static EmbedBuilder helpEmbed() {
		EmbedBuilder result = new EmbedBuilder()
				.setTitle("Help page")
				.setColor(Color.WHITE);
		return result;
	}

	public static EmbedBuilder rankingEmbed() {
		EmbedBuilder ranking = new EmbedBuilder()
				.setTitle("Ranking of user's fortune")
				.setColor(Color.WHITE)
				.setTimestamp(Instant.now());
		return ranking;
	}
}
