package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.Arrays;

import accounts.Permissions;

public class State extends Command {
	public State() {
		super("state",
				"Updates GLaDOS's state (online, idle, do not disturb)", Permissions.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "status", "Can be [online, idle, dnd]")
								.setRequired(true)
								.addChoice("online", "online")
								.addChoice("idle", "idle")
								.addChoice("dnd", "dnd")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		String state = event.getOption("status").getAsString();

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
				source.sendMessageEmbeds(BuildEmbed.errorEmbed(
						"Unknown state (" + state + ") ! All states are: <online / idle / dnd>")
						.build()).queue();
				return;
		}

		source.sendMessageEmbeds(
				BuildEmbed.successEmbed("Successfully updated to " + state + " state.").build())
				.queue();
	}
}
