package commands;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import items.Item;
import items.Rarity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

/*
 * Rules of a contract: 5 distintcs items are destroyed and one random item of upper tier is given
 * to member. The quality of this new item is the average of all input item and the starforce level
 * alongside rarity is equal to the one of the lowest of 5 input item + 1. The rarity and starforce
 * cannot exceed GODLY tier.
 */
public class Contract extends Command {
	public Contract() {
		super(
				"contract",
				"Trade 5 items of same tier against one random of upper tier.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(
						new OptionData(OptionType.STRING, "item1", "The first item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item2", "The second item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item3", "The third item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item4", "The fourth item you want to trade", true, true),
						new OptionData(OptionType.STRING, "item5", "The fifth item you want to trade", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) throws CloneNotSupportedException {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		// Ensure that the 5 provided items are differents. Let's use a Set for that:
		Set<String> contractItemsFQName = new HashSet<>();

		for (int i = 1; i <= 5; i++)
			contractItemsFQName.add(event.getOption("item" + i).getAsString());

		if (contractItemsFQName.size() != 5) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("You need to provide 5 differents items !").build()).queue();
			return;
		}

		List<items.Item> contractItems = new ArrayList<>();

		// Ensure the author owns all the items and items is not broken
		for (String itemFQName : contractItemsFQName) {
			Optional<items.Item> perhapsItem = authorAccount.getItemByFQName(itemFQName);

			if (perhapsItem.isEmpty()) {
				event.getHook().sendMessageEmbeds(
						BuildEmbed.errorEmbed("You do not own all the items you provided !").build()).queue();
				return;
			}

			if (perhapsItem.get().broken) {
				event.getHook().sendMessageEmbeds(
						BuildEmbed.errorEmbed("You cannot trade a broken item in a contract !").build()).queue();
				return;
			}

			contractItems.add(authorAccount.getItemByFQName(itemFQName).get());
		}

		// Compute average item quality
		double averageQuality = contractItems.stream().mapToDouble(items.Item::getQuality).average().orElse(0);

		// Get lowest star force
		int lowestStarForce =
				contractItems.stream().min((a, b) -> Integer.compare(a.starForceLevel, b.starForceLevel)).get().starForceLevel;

		// Get lowest rarity tier
		Rarity lowestRarity = contractItems.stream().min((a, b) -> Integer.compare(a.rarity.level, b.rarity.level)).get().rarity;

		// Take random item from upper quality
		SecureRandom random = new SecureRandom();
		List<items.Item> possibleItems = glados.getItemsByTier(lowestRarity.level + 1);

		items.Item tradedItem = (Item) possibleItems.get(random.nextInt(possibleItems.size())).clone();
		tradedItem.quality = averageQuality;
		tradedItem.starForceLevel = lowestStarForce + 1;

		// Remove all 5 items from user inventory
		for (items.Item i : contractItems)
			authorAccount.inventory.remove(i);

		// Give to user
		authorAccount.inventory.add(tradedItem);

		// Send the embed
		event.getHook().sendMessageEmbeds(BuildEmbed.itemDropEmbed(author, tradedItem, glados.cdn).build()).queue();
	}
}
