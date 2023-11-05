package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Status extends Command {
	public Status(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		if (args.arguments.length > 1) {
			Boolean isValidActivity = true;
			String activity = "";
			for (int i = 1; i < args.arguments.length; i++) {
				activity += " " + args.arguments[i];
			}
			switch (args.arguments[0]) {
				case "listening":
					args.channel.getJDA().getPresence().setActivity(Activity.listening(activity));
					break;
				case "playing":
					args.channel.getJDA().getPresence().setActivity(Activity.playing(activity));
					break;
				case "watching":
					args.channel.getJDA().getPresence().setActivity(Activity.watching(activity));
					break;
				case "streaming":
					args.channel.getJDA().getPresence()
							.setActivity(Activity.streaming(activity, "https://www.twitch.tv/ "));
					break;
				default:
					args.channel.getJDA().getPresence()
							.setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS,
									args.arguments[1] + args.arguments.toString()));
					isValidActivity = false;
			}

			if (isValidActivity) {
				args.channel.sendMessageEmbeds(BuildEmbed.successEmbed(
						"Successfully updated to " + args.arguments[0] + activity + " activity.")
						.build()).queue();
			} else {
				args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed("Unknown activity").build())
						.queue();
			}
		} else {
			args.channel.sendMessageEmbeds(BuildEmbed
					.errorEmbed(
							"Usage: activity <listening / playing / watching / streaming> <name>")
					.build()).queue();
		}
	}
}
