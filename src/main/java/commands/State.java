package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class State extends Command {
	public State(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		String state = event.getOption("status").getAsString();

		Boolean isValidState = true;
		switch (state) {
			case "online":
				source.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
				break;
			case "idle":
				source.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
				break;
			case "dnd":
				source.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				break;
			default:
				isValidState = false;
		}

		if (isValidState) {
			source.sendMessageEmbeds(
					BuildEmbed.successEmbed("Successfully updated to " + state + " state.").build())
					.queue();
		} else {
			source.sendMessageEmbeds(BuildEmbed
					.errorEmbed(
							"Unknown state (" + state + ") ! All states are: <online / idle / dnd>")
					.build()).queue();
		}
	}
}
