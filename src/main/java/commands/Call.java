package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.TimeUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;
import accounts.Permissions;

import glados.GLaDOS;

public class Call extends Command {
	public Call(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	private static String getMedalEmoji(int rank) {
		return switch (rank) {
			case 1 -> ":first_place: ";
			case 2 -> ":second_place: ";
			case 3 -> ":third_place: ";
			default -> " ";
		};
	}

	/*
	 * Ranks midnight messages and computes who is the winner
	 */
	public static void midnightRank(MessageChannel generalChannel) {
		EmbedBuilder midnightEmbed = BuildEmbed.midnightRankEmbed();
		List<Message> latestMessages = generalChannel.getHistory().retrievePast(10).complete();

		Collections.sort(latestMessages, TimeUtils::compareTo);

		int rank = 1;
		for (Message m : latestMessages) {
			if (rank == 1) {
				midnightEmbed.setThumbnail(m.getAuthor().getAvatarUrl());
				midnightEmbed.setDescription("Winner is " + m.getAuthor().getAsMention());
			}

			long delta = TimeUtils.computeDelta(m.getTimeCreated());

			if ((delta > -300000) && (delta < 300000))
				midnightEmbed.addField(getMedalEmoji(rank) + m.getAuthor().getName(),
						"**" + delta + "** ms", true);
			rank++;
		}

		generalChannel.sendMessageEmbeds(midnightEmbed.build()).queue();
	}

	public static void callMessage(MessageChannel destination, String trigger) {
		EmbedBuilder embed = null;

		switch (trigger) {
			case "Gamer":
				destination.sendMessage("<@&" + GLaDOS.getInstance().roleBroadcastMessenger + ">")
						.queue();
				embed = BuildEmbed.gamerEmbed();
				break;
			case "Midnight":
				try {
					JSONObject jsonObject =
							JsonDownloader.getJson("https://api.quotable.io/random");
					String author = jsonObject.getString("author");
					String quote = jsonObject.getString("content");

					if (quote.length() > 256)
						destination.sendMessage(quote + author).queue();

					embed = BuildEmbed.midnightQuote(quote, author);
				} catch (Exception e) {
					destination.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build())
							.queue();
				}
				break;
			default:
				embed = BuildEmbed.errorEmbed("This trigger has not been registered !");
				break;
		}

		destination.sendMessageEmbeds(embed.build()).queue();
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		String trigger = event.getOption("trigger").getAsString();

		if (trigger.isBlank() || trigger.isEmpty()) {
			source.sendMessageEmbeds(
					BuildEmbed.errorEmbed("You need to provide a trigger name !").build()).queue();
			return;
		}
		Call.callMessage(source, trigger);
	}

	public static void MerryChristmas(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage(
				"May this season of giving be the start of your better life. Have a great and blessed holiday ! Merry christmas @everyone !")
				.queue();
	}

	public static void HappyNewYear(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage(
				"Every end marks a new beginning. Keep your spirits and determination unshaken, and you shall always walk the glory road. With courage, faith and great effort, you shall achieve everything you desire. May your home gets filled with good fortune, happy New Year @everyone !")
				.queue();
	}
}
