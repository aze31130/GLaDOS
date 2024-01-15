package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

public class Item extends Command {
	public Item() {
		super("item", "Get information about an item",
				Permission.NONE, Arrays.asList(new OptionData(OptionType.STRING, "name",
						"The item name you're searching for.", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// Get item name from argument
		TextChannel source = event.getChannel().asTextChannel();
		String itemName = event.getOption("name").getAsString();

		// Search item
		Optional<items.Item> searchingItem = GLaDOS.getInstance().items.stream()
				.filter(i -> i.name.equals(itemName)).findFirst();

		if (searchingItem.isEmpty()) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("Item not found !").build()).queue();
			return;
		}

		// Display it
		source.sendMessageEmbeds(BuildEmbed.itemInfoEmbed(searchingItem.get()).build()).queue();
	}
}
