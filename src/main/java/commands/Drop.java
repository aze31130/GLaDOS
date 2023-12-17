package commands;

import java.security.SecureRandom;
import java.util.Arrays;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import utils.BuildEmbed;
import utils.TimeUtils;
import accounts.Account;
import accounts.Permissions;
import glados.GLaDOS;

public class Drop extends Command {
	public Drop() {
		super("drop",
				"[WIP] Drops you a random item. Admin privileges required",
				Permissions.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account authorAccount = glados.getAccount(author);

		if (!authorAccount.canDrop) {
			event.getMessageChannel()
					.sendMessageEmbeds(BuildEmbed.errorEmbed("You already dropped today !").build())
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
		}


		// Drop item

		// Check for conditionnal drop



		authorAccount.canDrop = false;
	}
}
