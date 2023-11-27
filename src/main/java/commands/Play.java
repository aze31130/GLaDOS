package commands;

import java.util.Arrays;

import accounts.Permissions;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Play extends Command {
	public Play() {
		super("play", "Play a youtube music in channel [WIP]", Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {

	}
}
