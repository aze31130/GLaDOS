package commands;

import java.security.SecureRandom;
import java.util.Arrays;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import items.Item;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import utils.BuildEmbed;
import utils.ItemUtils;

public class Drop extends Command {
	public Drop() {
		super(
				"drop",
				"Drops you a random item.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		if (!authorAccount.canDrop) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("You already dropped today ! Come back tomorrow for more.").build()).queue();
			return;
		}

		SecureRandom random = new SecureRandom();

		int acquiredMoney = random.nextInt(100, 1001);

		authorAccount.money += acquiredMoney;
		event.getHook().sendMessageEmbeds(BuildEmbed.moneyDropEmbed(author, acquiredMoney, authorAccount.money).build()).queue();

		// Drop item
		Item droppedItem = ItemUtils.getRandomItem(glados.items);

		// Asign random quality
		droppedItem.quality = random.nextDouble();

		if (droppedItem.quality > 0.9999)
			droppedItem.quality = 1.0;
		if (droppedItem.quality < 0.0001)
			droppedItem.quality = 0.0;

		authorAccount.inventory.add(droppedItem);
		event.getHook().sendMessageEmbeds(BuildEmbed.itemDropEmbed(author, droppedItem, glados.cdn).build()).queue();
		authorAccount.canDrop = false;
	}
}
