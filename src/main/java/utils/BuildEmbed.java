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
		String[] messages = {"Games don't make you violent, lag does",
				"We are not players, we are gamerz",
				"Failure doesn't mean the game is over, it means try again with more experience",
				"A true Gamerz doesn't have birthdays, He level up !",
				"I don't need to get a life, I am a Gamer, I have a lots of lives.",
				"Just one more game UwU",
				"A hero need not speak. When he is gone, the world will speak for him.",
				"Alert ! The bomb has been planted. 40 seconds to detonation.",
				"If I cannot outsmart them, I will outfight them.",
				"Games are the only legal place to kill stupids.",
				"They told me I couldn't, that's why I did.",
				"Video games foster the mindset that allows creativity to grow.",
				"When life gives you curdled milk, be patient, you get very good cheese.",
				"Yes! I am a gamer, but surely I don't play until you start gaming.",
				"Escape reality and play games.",
				"When I play fighting games, I press random buttons and hope for the rest.",
				"Maturity is when you realize Winner Winner Chicken Dinner is not a great success.",
				"Life is a game, the game of life ! Play to win.",
				"We don't stop playing because we grow old, We grow old because we stop playing.",
				"We all make choices in life, but in the end our choices make us.",
				"Eat, Sleep, Play repeat.", "Gamers don't die they respawn.",
				"I am not a player, I am a gamer. Players get a chick, I get the achievement.",
				"Don't play the game. Win it.",
				"A sword wields no strength unless the hands that holds it has courage.",
				"It’s a funny thing, ambition. It can take one to sublime heights or harrowing depths. And sometimes they are one and the same.",
				"You can’t break a man the way you break a dog, or a horse. The harder you beat a man, the taller he stands.",
				"True grace is beautiful in its imperfection, honest in its emotion, freed by its on reality.",
				"If history is to change, let it change. If the world is to be destroyed, so be it. If my fate is to die, I must simply laugh.",
				"Even in dark times, we cannot relinquish the things that make us human.",
				"A strong man doesn’t need to read the future. He makes his own.",
				"What is better – To be born good, or to overcome your evil nature through great effort?",
				"However, that parting need not last forever... Whether a parting be forever or merely for a short time... That is up to you.",
				"Hatred and prejudice will never be eradicated. And the witch hunts will never be about witches. To have a scapegoat, that's the key.",
				"Life is a negotiation. We all want. We all give to get what we want.",
				"Does this unit have a soul?",
				"I had no choice, I had to do it…I only see the opportunity. But when I’m gone, everyone’s gonna remember my name: Big Smoke!",
				"Always fear the flame, lest you be devoured by it, and lose yourself.",
				"I am a man of unfortunate and I must seek my fortune.",
				"A famous explorer once said, that the extraordinary is in what we do, not who we are.",
				"Often when we guess at others' motives, we reveal only our own.",
				"If history only remembers one in thousands of us, then the future will be filled with stories of who we were and what we did.",
				"The more things change, the more they stay the same.",
				"I'm the leading man. Do you know what they say about the leading man? He never dies.",
				"I don't like to lose at all.",
				"Everyone has played video games at some point these days, and video games are fun.",
				"I do miss playing on the big stage, to be able to hear the crowd roar in the stadium. But I don't miss competing.",
				"BREAKING NEW: A new game appeared, go and get it !",
				"Jean-Pierre, there's another free game this week.",
				"Again ? Another ping ? There must be another free game.",
				"Is it a bird ? Is this a plane ? No, that's a free game !",
				"Oh oh oh, captain' that's a gallion of free games.", "My name is free, game free",
				"Everything began when games were created, 15 were given to mens.",
				"One game to rule them all",
				"If you are not coming to the free game, then the free game will come to you.",
				"Irgendwie, irgendwo, irgendwann, a free game !",
				"There's a free game in my boots.",
				"And the 7th day, God rest himself with a free game.",
				"The cake is a lie, but not the free game",
				"It's not just any free game Harry, it's an Epic game !",
				"Sarah Connor ? Looking for Sarah Connor... There are only free games here.",
				"A free game is never late, Frodo Baggins, nor early for that matter, it arrives exactly on time.",
				"Jessie!, James!, Team Rocket blasts off at the speed of light! Surrender now, or prepare to play a free game!",
				"What's your name ? What is your quest ? What is the capital of Syria ? That's fine, you can have your free game!"};

		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.BLACK)
				.setAuthor("www.epicgames.com", "https://www.epicgames.com/store/fr/free-games/")
				.setTitle(messages[randomNumber])
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
		Random rng = new Random();
		int randomNumber = 0;
		String message;
		if (joining) {
			String[] messages = {"[member] is here ! Now it's time to raise our hands !",
					"[member] just arrived ! Hide the vodka quickly !", "Big [member] is here !",
					"[member] joined the game.", "[member] just did a barrel roll",
					"Attention, everyone! [member] has entered the chat. Let's give them a warm welcome!",
					"Look who decided to grace us with their presence! Welcome, [member]!",
					"Hold on tight, folks! [member] has joined the server. Let the good times roll!",
					"Breaking news: [member] has arrived! Get ready for some epic conversations and laughter!",
					"[member] has entered the arena! Brace yourselves for their awesomeness!",
					"Ladies and gentlemen, give a round of applause for our newest arrival! Welcome, [member], to the heart of our community!",
					"Watch out, everyone! [member] just graced us with their presence!",
					"[member] entered our digital universe!",
					"Hold your breath and get ready, for [member] has officially joined the game!",
					"Attention, everyone! [member] has stepped into our virtual gathering",
					"Whoa, did you see that? [member] just pulled off an incredible barrel roll into our chat!",
					"Step aside, everyone! It's time to make way for [member]",
					"Breaking news, folks! Say hello to our latest epic member [member]",
					"The arena awaits the grand entrance of [member]!",
					"[member] arrived ! It's gaming time!"};
			randomNumber = rng.nextInt(messages.length);
			message = messages[randomNumber];
		} else {
			String[] messages = {"[member] may be gone, but he still lives on in our hearts.",
					"Oh no, we just loose [member].",
					"In this dark day, [member] decided to leave this place.",
					"[member] picked a chance card: Go to jail, do not pass go, do not collect 200$.",
					"[member] left. The party is now over.",
					"[member] has bid farewell to the server. We'll miss you!",
					"Oh no! [member] has departed. Wishing them all the best in their future endeavors.",
					"Sad to see [member] go. They brought so much joy and laughter to the server.",
					"It's a bittersweet moment as [member] embarks on a new journey. Farewell and take care!",
					"The time has come to say goodbye to [member]. Thank you for being part of our community!",
					"Farewell, [member]. Your presence will be greatly missed. Stay in touch!",
					"Wishing [member] all the best as they venture beyond our server's boundaries. Goodbye!",
					"We're saying goodbye to [member], but the memories we shared will remain. Take care!",
					"It's with a heavy heart that we wave goodbye to [member]. You will always be welcome here!",
					"As [member] leaves, we reflect on the good times and friendships formed. Farewell and best wishes!",
					"In the quiet of the night, [member] slips away. Farewell, and may your journey be filled with light.",
					"As the sun sets on [member]'s time here, may a new dawn bring them endless possibilities. Goodbye and take care!",
					"With every departure, a new chapter begins. Wishing [member] success and happiness in their next adventure.",
					"The echoes of [member]'s presence will linger here, a reminder of the friendships and moments we shared. Farewell, dear friend.",
					"Though parting is such sweet sorrow, we are grateful for the moments we've had with [member].",
					"The server's stage dims a little as [member] takes their final bow. Thank you for the memories, and best wishes on your future stage.",
					"As [member] leaves our virtual home, let us remember that goodbyes are just stepping stones to new beginnings.",
					"Though [member] departs, the bonds of friendship remain unbroken. We'll miss you, and the server's doors will always be open to your return.",
					"The universe awaits [member]'s next adventure. May it be filled with joy, success, and the same positive energy they brought to our server.",
					"In the book of our server's history, [member] wrote beautiful chapters. It's time for a new page in their story.",
					"Every goodbye is a chance for a new hello. [member], we'll cherish the times we've shared and look forward to reuniting someday.",
					"The departure of [member] leaves a void, but it also opens up space for new friendships and experiences. Wishing you happiness on your journey.",
					"Though [member] may be leaving our server, the ripples of their presence will forever touch our hearts. Farewell, and keep shining.",
					"Life is a journey, and [member] is on to the next chapter. May it be a tale of triumph, adventure, and fulfillment. Goodbye and good luck!"};
			randomNumber = rng.nextInt(messages.length);
			message = messages[randomNumber];
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.CYAN);
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
		embed.setDescription(
				"[**" + item.rarity.name().toLowerCase() + "**] [**" + String.format("%.2f%%", 100 * item.quality) + "**] "
						+ item.getFQName());
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
