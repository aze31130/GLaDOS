package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Status extends Command {
	public Status(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		String activity = event.getOption("type").getAsString();
		String description = event.getOption("description").getAsString();

		Boolean isValidActivity = true;

		switch (activity) {
			case "listening":
				source.getJDA().getPresence().setActivity(Activity.listening(activity));
				break;
			case "playing":
				source.getJDA().getPresence().setActivity(Activity.playing(activity));
				break;
			case "watching":
				source.getJDA().getPresence().setActivity(Activity.watching(activity));
				break;
			case "streaming":
				source.getJDA().getPresence()
						.setActivity(Activity.streaming(activity, "https://www.twitch.tv/ "));
				break;
			default:
				source.getJDA().getPresence()
						.setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, description));
				isValidActivity = false;
		}

		if (isValidActivity) {
			source.sendMessageEmbeds(BuildEmbed
					.successEmbed("Successfully updated to " + description + " activity.").build())
					.queue();
		} else {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("Unknown activity").build()).queue();
		}
	}
}
