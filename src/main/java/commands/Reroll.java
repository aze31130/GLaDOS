package commands;

import java.util.Arrays;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Reroll extends Command {
	public Reroll() {
		super(
				"reroll",
				"[WIP] Reroll an item quality.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "name", "The item name you want to reroll.", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// Ensure the user own the item

		// Ensure the user has enough money

		// Send the embed
	}
}
