package commands;

import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Play extends Command {
	public Play() {
		super(
				"play",
				"Play a youtube music in channel [WIP]",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {

	}
}
