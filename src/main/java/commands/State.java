package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.Arrays;

import accounts.Permission;

public class State extends Command {
	public State() {
		super(
				"state",
				"Updates GLaDOS's state (online, idle, do not disturb)",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "status", "Can be [online, idle, dnd]")
								.setRequired(true)
								.addChoice("online", "online")
								.addChoice("idle", "idle")
								.addChoice("dnd", "dnd")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String state = event.getOption("status").getAsString();

		switch (state) {
			case "online":
				event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
				break;
			case "idle":
				event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
				break;
			case "dnd":
				event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				break;
			default:
				event.getHook().sendMessageEmbeds(
						BuildEmbed.errorEmbed("Unknown state (" + state + ") ! All states are: <online / idle / dnd>")
								.build())
						.queue();
				return;
		}

		event.getHook().sendMessageEmbeds(BuildEmbed.successEmbed("Successfully updated to " + state + " state.").build())
				.queue();
	}
}
