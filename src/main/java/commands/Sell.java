package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

public class Sell extends Command {
	public Sell() {
		super(
				"sell",
				"Sells an item.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.STRING, "item", "The item you want to sell", true, true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		String itemFQName = event.getOption("item").getAsString();

		// Ensure the owner own the item
		Optional<items.Item> pretendedItem = authorAccount.getItemByFQName(itemFQName);

		if (pretendedItem.isEmpty()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You cannot sell an item you do not own !").build()).queue();
			return;
		}

		// Sells the item
		items.Item item = pretendedItem.get();

		authorAccount.money += item.getValue();
		authorAccount.inventory.remove(item);

		event.getHook().sendMessageEmbeds(BuildEmbed.successEmbed(
				"Successfully sold " + item.getFQName() + " at " + item.getValue() + " (" + authorAccount.money + " total)")
				.build()).queue();
	}
}
