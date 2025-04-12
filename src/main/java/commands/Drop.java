package commands;

import java.security.SecureRandom;
import java.util.Arrays;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import items.Item;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Drop extends Command {
	public Drop() {
		super(
				"drop",
				"Drops you a random item.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList());
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

		if (!authorAccount.canDrop) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("You already dropped today ! Come back tomorrow for more.").build()).queue();
			return;
		}

		SecureRandom random = new SecureRandom();
		int acquiredMoney = random.nextInt(250, 1001);

		authorAccount.money += acquiredMoney;

		// Drop item
		Item droppedItem = ItemUtils.getRandomItem(glados.items);

		/*
		 * Very rare case: if the item dropped is not obtainable. For instance, dropping again a unique item
		 * already in someones else inventory.
		 */
		if (!ItemUtils.checkDropConditions(droppedItem)) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("The item you dropped " + droppedItem.name + " cannot be added to your inventory !").build(),
					BuildEmbed.errorEmbed("Drop canceled, you are allowed to drop again.").build()).queue();
			return;
		}

		droppedItem.quality = random.nextDouble();
		droppedItem.makeLegit();

		authorAccount.inventory.add(droppedItem);
		event.getHook().sendMessageEmbeds(
				BuildEmbed.moneyDropEmbed(author, acquiredMoney, authorAccount.money).build(),
				BuildEmbed.itemDropEmbed(author, droppedItem, glados.cdn).build()).queue();
		authorAccount.canDrop = false;
	}
}
