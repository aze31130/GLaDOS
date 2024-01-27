package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.Arrays;

import accounts.Permission;

public class Status extends Command {
	public Status() {
		super("activity", "Updates GLaDOS's activity", Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "type",
								"Can be [listening, playing, watching, streaming]")
										.setRequired(true)
										.addChoice("listening", "listening")
										.addChoice("playing", "playing")
										.addChoice("watching", "watching")
										.addChoice("streaming", "streaming"),
						new OptionData(OptionType.STRING, "description",
								"The displayed activity").setRequired(true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();
		String activity = event.getOption("type").getAsString();
		String description = event.getOption("description").getAsString();

		switch (activity) {
			case "listening":
				source.getJDA().getPresence().setActivity(Activity.listening(description));
				break;
			case "playing":
				source.getJDA().getPresence().setActivity(Activity.playing(description));
				break;
			case "watching":
				source.getJDA().getPresence().setActivity(Activity.watching(description));
				break;
			case "streaming":
				source.getJDA().getPresence()
						.setActivity(Activity.streaming(description, "https://www.twitch.tv/ "));
				break;
			default:
				source.getJDA().getPresence()
						.setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, description));
				source.sendMessageEmbeds(BuildEmbed.errorEmbed("Unknown activity").build()).queue();
				return;
		}

		source.sendMessageEmbeds(BuildEmbed
				.successEmbed("Successfully updated to " + activity + " " + description).build())
				.queue();
	}
}
