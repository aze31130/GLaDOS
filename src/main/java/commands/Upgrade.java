package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import accounts.Permission;

public class Upgrade extends Command {
	public Upgrade() {
		super("upgrade",
				"Upgrade an item from your inventory.",
				Permission.NONE, Arrays.asList(new OptionData(OptionType.STRING, "name",
						"The item name you want to upgrade.", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();

		// Check if the owner own the item

		// Check if the item is upgradable

		// Check if the user has enough money

		source.sendMessageEmbeds(
				BuildEmbed.errorEmbed("This system has not been implemented yet !").build())
				.queue();
	}
}
