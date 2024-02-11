package commands;

import java.util.Arrays;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Contract extends Command {
	public Contract() {
		super(
				"contract",
				"[WIP] Trade 5 items of same tier against one random of upper tier.",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// Ensure the user own the item

		// Ensure the user has enough money

		// Send the embed
		event.getHook().sendMessage("WIP").queue();
	}
}
