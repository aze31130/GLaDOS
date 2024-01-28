package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

public class Sell extends Command {
	public Sell() {
		super("sell",
				"Sells an item.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "item",
								"The item you want to sell").setRequired(true)
										.setAutoComplete(true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		String itemFQName = event.getOption("item").getAsString();

		// Ensure the owner own the item
		Optional<items.Item> pretendedItem =
				authorAccount.inventory.stream().filter(it -> it.getFQName().equals(itemFQName))
						.findFirst();

		if (pretendedItem.isEmpty()) {
			source.sendMessageEmbeds(
					BuildEmbed.errorEmbed("You cannot sell an item you do not own !").build())
					.queue();
			return;
		}

		// Sells the item
		items.Item item = pretendedItem.get();

		authorAccount.money += item.value;
		authorAccount.inventory.remove(item);

		source.sendMessageEmbeds(
				BuildEmbed.successEmbed(
						"Successfully sold " + item.getFQName() + " at " + item.value + " ("
								+ authorAccount.money + " total)")
						.build())
				.queue();
	}

}
