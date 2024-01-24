package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Permission;
import glados.GLaDOS;
import items.Rarity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Item extends Command {
	public Item() {
		super("item", "Get information about an item",
				Permission.NONE, Arrays.asList(new OptionData(OptionType.STRING, "name",
						"The item name you're searching for.", false, true)));
	}

	public void generateItemChart(TextChannel source) {
		GLaDOS g = GLaDOS.getInstance();
		EmbedBuilder rarityEmbed = BuildEmbed.itemChartEmbed();

		Rarity allRarity[] = {
				Rarity.COMMON,
				Rarity.UNUSUAL,
				Rarity.RARE,
				Rarity.EPIC,
				Rarity.LEGENDARY,
				Rarity.FABLED,
				Rarity.MYTHICAL,
				Rarity.GODLY,
				Rarity.UNIQUE,
		};

		for (Rarity r : allRarity)
			rarityEmbed.addField(
					r.name().toLowerCase() + " : " + ItemUtils.getRarityDropRate(r) + "%",
					"Registered Items: " + g.items.stream().filter(i -> i.rarity.equals(r)).count(),
					false);

		source.sendMessageEmbeds(rarityEmbed.build()).queue();
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// Get item name from argument
		TextChannel source = event.getChannel().asTextChannel();
		OptionMapping optionalItemName = event.getOption("name");

		if (optionalItemName == null) {
			generateItemChart(source);
			return;
		}

		String itemName = optionalItemName.getAsString();

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
