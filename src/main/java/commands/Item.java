package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import items.Rarity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Item extends Command {
	public Item() {
		super(
				"item",
				"Get information about an item",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(
						new OptionData(OptionType.STRING, "name", "The item name you're searching for.", false, true),
						new OptionData(OptionType.BOOLEAN, "owners", "Displays a list of all account owning the item.")));
	}

	public void generateItemChart(InteractionHook source, GLaDOS g) {
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
			rarityEmbed.addField(r.name().toLowerCase() + " : " + ItemUtils.getRarityDropRate(r) + "%",
					"Registered Items: " + g.items.stream().filter(i -> i.rarity.equals(r)).count(), false);

		source.sendMessageEmbeds(rarityEmbed.build()).queue();
	}

	public void listAllOwners(InteractionHook source, items.Item searchItem, GLaDOS g) {
		EmbedBuilder ownerEmbeds = BuildEmbed.ownerEmbed(searchItem);

		for (Account a : g.accounts) {
			for (items.Item i : a.inventory) {
				if (i.name.equals(searchItem.name)) {
					ownerEmbeds.addField(a.user.getName(), a.user.getAsMention(), false);
					break;
				}
			}
		}

		source.sendMessageEmbeds(ownerEmbeds.build()).queue();
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();

		// Get item name from argument
		OptionMapping optionalItemName = event.getOption("name");

		if (optionalItemName == null) {
			generateItemChart(event.getHook(), g);
			return;
		}

		String itemName = optionalItemName.getAsString();

		// Search item
		Optional<items.Item> searchingItem = GLaDOS.getInstance().items.stream().filter(i -> i.name.equals(itemName)).findFirst();

		if (searchingItem.isEmpty()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Item not found !").build()).queue();
			return;
		}

		// Display it
		event.getHook().sendMessageEmbeds(BuildEmbed.itemInfoEmbed(searchingItem.get()).build()).queue();

		Optional<Boolean> optionalOwnerList = Optional.ofNullable(event.getOption("owners")).map(OptionMapping::getAsBoolean);

		if (optionalOwnerList.isPresent()) {
			listAllOwners(event.getHook(), searchingItem.get(), g);
			return;
		}
	}
}
