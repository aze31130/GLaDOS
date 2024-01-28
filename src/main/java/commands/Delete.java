package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Delete extends Command {
	public Delete() {
		super("delete", "Deletes an item from someone's inventory. Owner privileges required.",
				Permission.OWNER, Arrays.asList(
						new OptionData(OptionType.USER, "target",
								"The user you want to remove an item").setRequired(true),
						new OptionData(OptionType.STRING, "item",
								"The item you want to remove").setAutoComplete(true),
						new OptionData(OptionType.INTEGER, "money",
								"The amount of money you want to remove")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		MessageChannelUnion source = event.getChannel();

		Account target = glados.getAccountById(event.getOption("target").getAsString());

		Optional<String> itemName =
				Optional.ofNullable(event.getOption("item")).map(OptionMapping::getAsString);

		Optional<Integer> moneyAmount =
				Optional.ofNullable(event.getOption("money")).map(OptionMapping::getAsInt);


		// Check if it's an item give
		if (itemName.isPresent()) {
			// Check if the user owns the item
			if (!ItemUtils.userOwnItem(target, itemName.get())) {
				source.sendMessageEmbeds(
						BuildEmbed.errorEmbed("The user does not own this item !")
								.build())
						.queue();
				return;
			}

			// Remove the item
			target.inventory.removeIf(it -> it.getFQName().equals(itemName.get()));

			source.sendMessageEmbeds(
					BuildEmbed.successEmbed("Removed " + itemName.get() + " to user "
							+ target.user.getAsMention()).build())
					.queue();
		}

		// Check if it's a money give
		if (moneyAmount.isPresent()) {

			if (moneyAmount.get() <= 0) {
				source.sendMessageEmbeds(
						BuildEmbed.errorEmbed("You cannot remove a negative amount of money !")
								.build())
						.queue();
				return;
			}

			target.money -= moneyAmount.get();

			if (target.money < 0)
				target.money = 0;

			source.sendMessageEmbeds(
					BuildEmbed.successEmbed("Removed " + moneyAmount.get() + " to user "
							+ target.user.getAsMention()).build())
					.queue();
		}
	}
}
