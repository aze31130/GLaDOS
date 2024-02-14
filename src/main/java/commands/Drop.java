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
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		if (!authorAccount.canDrop) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You already dropped today ! Come back tomorrow :D").build())
					.queue();
			return;
		}

		SecureRandom random = new SecureRandom();

		// Get random amount of money [1 - 1000]
		int acquiredMoney = random.nextInt(100, 1001);

		authorAccount.money += acquiredMoney;
		event.getHook().sendMessageEmbeds(BuildEmbed.moneyDropEmbed(author, acquiredMoney, authorAccount.money).build()).queue();

		// if (TimeUtils.isSpecialDay()) {
		// Guaranteeing specific drops on event days
		// TODO
		// return;
		// }

		// Drop item
		double dropValue = random.nextDouble(glados.itemTotalProb + 1);
		long cumulativeProbability = 0;
		Item droppedItem = null;

		for (Item item : glados.items) {
			cumulativeProbability += item.dropChance;
			if (dropValue <= cumulativeProbability) {
				// Check if drop requirements are fulfilled
				if (ItemUtils.checkDropConditions(item)) {
					try {
						droppedItem = (Item) item.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}

		/*
		 * Very rare edge case: if the last item has been skipped due to unsuffisient drop conditions. In
		 * this case, we simply send an error message and allow the user to drop again.
		 */
		if (droppedItem == null) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed(
					"An error occured during item generation. Your daily record has not been updated: you can drop again normaly right now.")
					.build()).queue();
			return;
		}

		// Asign random quality
		droppedItem.quality = random.nextDouble();

		if (droppedItem.quality > 0.9999)
			droppedItem.quality = 1.0;
		if (droppedItem.quality < 0.0001)
			droppedItem.quality = 0.0;

		authorAccount.inventory.add(droppedItem);
		event.getHook().sendMessageEmbeds(BuildEmbed.itemDropEmbed(author, droppedItem).build()).queue();
		authorAccount.canDrop = false;
	}
}
