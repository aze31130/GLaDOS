package commands;

import java.util.Arrays;
import java.util.Optional;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Delete extends Command {
	public Delete() {
		super(
				"delete",
				"Deletes an item from someone's inventory. Owner privileges required.",
				Permission.OWNER,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.USER, "target", "The user you want to remove an item", true),
						new OptionData(OptionType.STRING, "item", "The item you want to remove", true),
						new OptionData(OptionType.INTEGER, "money", "The amount of money you want to remove")));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();

		Optional<Account> optionalTarget = glados.getAccountById(event.getOption("target").getAsString());

		if (optionalTarget.isEmpty()) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("This user does not have an account. He needs to /drop first !").build()).queue();
			return;
		}

		Account target = optionalTarget.get();

		Optional<String> itemName = Optional.ofNullable(event.getOption("item")).map(OptionMapping::getAsString);

		Optional<Integer> moneyAmount = Optional.ofNullable(event.getOption("money")).map(OptionMapping::getAsInt);


		// Check if it's an item give
		if (itemName.isPresent()) {
			// Check if the user owns the item
			if (!ItemUtils.userOwnItem(target, itemName.get())) {
				event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("The user does not own this item !").build()).queue();
				return;
			}

			// Remove the item
			items.Item it = target.inventory.stream().filter(i -> i.getFQName().equals(itemName.get())).findFirst().get();
			target.inventory.remove(it);

			event.getHook().sendMessageEmbeds(
					BuildEmbed.successEmbed("Removed " + itemName.get() + " to user " + target.user.getAsMention()).build())
					.queue();
		}

		// Check if it's a money give
		if (moneyAmount.isPresent()) {
			if (moneyAmount.get() <= 0) {
				event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You cannot remove a negative amount of money !").build())
						.queue();
				return;
			}

			target.money -= moneyAmount.get();

			if (target.money < 0)
				target.money = 0;

			event.getHook().sendMessageEmbeds(
					BuildEmbed.successEmbed("Removed " + moneyAmount.get() + " to user " + target.user.getAsMention()).build())
					.queue();
		}
	}
}
