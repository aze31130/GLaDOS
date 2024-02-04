package utils;

import java.awt.Color;
import java.time.Instant;
import java.util.Random;
import accounts.Account;
import glados.GLaDOS;
import items.Item;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

public class BuildEmbed {
	public static EmbedBuilder gamerEmbed() {
		GLaDOS glados = GLaDOS.getInstance();

		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.BLACK)
				.setAuthor("www.epicgames.com", "https://www.epicgames.com/store/fr/free-games/")
				.setTitle(glados.epicgameQuotes.getString(new Random().nextInt(glados.epicgameQuotes.length())))
				.setThumbnail(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Epic_Games_logo.svg/1200px-Epic_Games_logo.svg.png")
				.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder midnightQuote(String quote, String author) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(quote);
		embed.setDescription(author);
		embed.setColor(Color.CYAN);
		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder midnightRankEmbed() {
		EmbedBuilder rankingEmbed = new EmbedBuilder();
		rankingEmbed.setColor(Color.MAGENTA);
		rankingEmbed.setTitle(":clock12: Midnight Race");
		rankingEmbed.setTimestamp(Instant.now());
		return rankingEmbed;
	}

	public static EmbedBuilder joinLeaveEmbed(String mention, Boolean joining) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.CYAN);

		String message = "";
		if (joining)
			message = glados.joinQuote.getString(new Random().nextInt(glados.joinQuote.length()));
		else
			message = glados.leaveQuote.getString(new Random().nextInt(glados.leaveQuote.length()));

		embed.setDescription(message.replace("[member]", mention));
		return embed;
	}

	public static EmbedBuilder errorEmbed(String description) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Error");
		embed.setDescription(description);
		embed.setColor(Color.RED);
		return embed;
	}

	public static EmbedBuilder successEmbed(String description) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Success");
		embed.setDescription(description);
		embed.setColor(Color.GREEN);
		return embed;
	}

	public static EmbedBuilder profileEmbed(Account account) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(account.user.getName() + "'s profile");
		embed.setDescription(account.user.getAsMention());
		embed.setColor(Color.GREEN);
		embed.setThumbnail(account.user.getAvatarUrl());

		embed.addField(":sparkles: - Level", Integer.toString(account.level), true);
		embed.addField(":green_book: - Experience", Long.toString(account.experience), true);
		embed.addField(":shield: - Trust Factor", "WIP", true);
		embed.addField(":wave: - Fame", "WIP", true);
		embed.addField(":clock: :timer: - Joined Discord", account.user.getTimeCreated().toString(),
				true);
		embed.addField(":moneybag: - Money", Long.toString(account.money), true);
		embed.addField(":briefcase: - Items", Integer.toString(account.inventory.size()), true);
		embed.addField(":envelope: - Messages", "WIP", true);
		embed.addField(":tada: - Reactions", "WIP", true);
		embed.addField(":trophy: - Achievements", "WIP", true);

		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder questionEmbed(String question, String category, String difficulty) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(category);
		embed.setDescription(question);

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

		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder modalEmbed(String subject, String description, String reactions,
			User author) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(subject);
		embed.setDescription(description);
		embed.setColor(Color.WHITE);

		embed.setAuthor(author.getName(), author.getAvatarUrl(), author.getAvatarUrl());
		embed.addField("Voting procedure", reactions, false);

		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder hashEmbed(String result, String algorithm) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Result");
		embed.addField(algorithm, result, false);
		embed.setColor(Color.GRAY);

		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder tradeEmbed(Account author, Account target, String srcItem,
			int srcMoney, String dstItem, int dstMoney) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Trade Offer");
		embed.setDescription(author.user.getAsMention() + "=>" + target.user.getAsMention());
		embed.addField("Item source", srcItem, false);
		embed.addField("Money source", Integer.toString(srcMoney), false);
		embed.addField("Item destination", dstItem, false);
		embed.addField("Money destination", Integer.toString(dstMoney), false);
		embed.setColor(Color.ORANGE);
		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder moneyDropEmbed(User dropper, int claimedMoney, long totalMoney) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Claimed !");
		embed.setAuthor(dropper.getName(), dropper.getAvatarUrl(), dropper.getAvatarUrl());
		embed.setColor(Color.ORANGE);
		embed.setDescription("Got " + claimedMoney + " (" + totalMoney + " total)");
		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder itemDropEmbed(User dropper, Item item) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("You got:");
		embed.setAuthor(dropper.getName(), dropper.getAvatarUrl(), dropper.getAvatarUrl());
		embed.setColor(item.rarity.color);
		embed.setDescription("[**" + item.rarity.name().toLowerCase() + "**] " + item.getFQName());
		embed.setTimestamp(Instant.now());
		return embed;
	}

	public static EmbedBuilder loggerEmbed(String action, String content) {
		EmbedBuilder event = new EmbedBuilder();
		event.setTitle(action);
		event.setColor(Color.GRAY);
		event.setDescription(content);
		event.setTimestamp(Instant.now());
		return event;
	}

	public static EmbedBuilder itemInfoEmbed(Item i) {
		GLaDOS glados = GLaDOS.getInstance();
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("[" + i.rarity.name() + "] " + i.name);
		info.setDescription(i.lore);
		info.setColor(i.rarity.color);
		if (i.untradable)
			info.addField(":no_entry:  Untradable", "Trade banned", false);
		if (i.starForceMaxLevel > 0)
			info.addField(":star2: Max StarForce", Integer.toString(i.starForceMaxLevel), false);
		info.addField(i.type.emote + " Type", i.type.toString().toLowerCase(), false);
		info.addField(":coin: Value", Integer.toString(i.value), false);
		info.addField(":four_leaf_clover: Drop Chance", String.format("%.3f%%", 100 * (i.dropChance / glados.itemTotalProb)),
				false);
		info.setTimestamp(Instant.now());
		return info;
	}

	public static EmbedBuilder inventoryEmbed(Account sender, int pageNumber) {
		int totalPages = (int) Math.ceil((double) sender.inventory.size() / 5);

		EmbedBuilder inventory = new EmbedBuilder();
		inventory.setTitle("Your inventory");
		inventory.setDescription("You have " + sender.money + " :coin:");
		inventory.setAuthor(sender.user.getName());
		inventory.setThumbnail(sender.user.getAvatarUrl());
		inventory.setColor(Color.GRAY);
		inventory.setFooter(pageNumber + " / " + totalPages);
		inventory.setTimestamp(Instant.now());
		return inventory;
	}

	public static EmbedBuilder itemChartEmbed() {
		EmbedBuilder result = new EmbedBuilder();
		result.setTitle("Drop rates information");
		result.setDescription("This list is dynamically generated, theses are the actuals rates currently in use.");
		result.setColor(Color.YELLOW);
		result.setTimestamp(Instant.now());
		return result;
	}
}
