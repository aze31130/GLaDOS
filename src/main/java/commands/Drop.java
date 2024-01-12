package commands;

import java.security.SecureRandom;
import java.util.Arrays;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import items.Item;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import utils.BuildEmbed;
import utils.TimeUtils;

public class Drop extends Command {
	public Drop() {
		super("drop",
				"[WIP] Drops you a random item. Admin privileges required",
				Permission.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account authorAccount = glados.getAccount(author);

		if (!authorAccount.canDrop) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("You already dropped today !").build())
					.queue();
			return;
		}

		SecureRandom random = new SecureRandom();

		// 50% chance to get daily money
		if (random.nextBoolean()) {
			// Get random amount of money [1 - 1000]
			int acquiredMoney = random.nextInt(1001);

			authorAccount.money += acquiredMoney;
			event.getMessageChannel()
					.sendMessageEmbeds(
							BuildEmbed.moneyDropEmbed(acquiredMoney, authorAccount.money).build())
					.queue();
		}

		if (TimeUtils.isSpecialDay()) {
			// Guaranteeing specific drops on event days
			// TODO
			return;
		}

		// Drop item
		long dropValue = random.nextLong(glados.itemTotalProb + 1);
		long cumulativeProbability = 0;
		Item droppedItem = null;

		for (Item item : glados.items) {
			cumulativeProbability += item.dropChance;
			if (dropValue <= cumulativeProbability) {
				droppedItem = item;
				break;
			}
		}

		// Check if drop requirements are met
		// TODO

		authorAccount.inventory.add(droppedItem);
		source.sendMessageEmbeds(BuildEmbed.itemDropEmbed(droppedItem).build()).queue();

		authorAccount.canDrop = false;
	}
}
