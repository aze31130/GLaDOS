package commands;

import java.util.Arrays;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Contract extends Command {
	public Contract() {
		super(
				"contract",
				"[WIP] Trade 5 items of same tier against one random of upper tier.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "item1", "The first item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item2", "The second item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item3", "The third item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item4", "The fourth item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item5", "The fifth item you want to trade", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// Ensure the user own all 5 items and all 5 items are differents

		// Compute average item quality

		// Take random item from upper quality

		// Give to user

		// Remove all 5 items from user inventory

		// Send the embed
		event.getHook().sendMessage("WIP").queue();
	}
}
