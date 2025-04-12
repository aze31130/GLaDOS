package commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Buy extends Command {
	public Buy() {
		super(
				"buy",
				"Buys a market item.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(new OptionData(OptionType.STRING, "item", "The item you want to buy", true, true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();

		Optional<String> itemName = Optional.ofNullable(event.getOption("item")).map(OptionMapping::getAsString);
		if (itemName.isEmpty()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You need to provide an item.").build()).queue();
			return;
		}

		String itemFQName = itemName.get();

		// Check if item is available in market
		Optional<items.Item> item = glados.market.stream().filter(i -> i.getFQName().equals(itemFQName)).findFirst();
		if (item.isEmpty()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Item not available in market.").build()).queue();
			return;
		}

		items.Item itemInstance = item.get();

		// Check if user has enough money
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		if (authorAccount.money < itemInstance.getValue()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You do not have enough money to buy " + itemInstance.getFQName() + " !").build())
					.queue();
			return;
		}

		// Removes money
		authorAccount.money -= itemInstance.getValue();

		// Swap item market to user inventory
		glados.market.remove(itemInstance);
		authorAccount.inventory.add(itemInstance);

		event.getHook().sendMessageEmbeds(
				BuildEmbed.successEmbed("Successfully acquired " + itemInstance.getFQName() + " for " + itemInstance.getValue()).build()).queue();
	}
}
