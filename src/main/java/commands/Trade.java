package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.ItemUtils;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Trade extends Command {
	public Trade() {
		super("trade",
				"Request a trade with another user.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.USER, "target",
								"The target of your trade offer").setRequired(true),
						new OptionData(OptionType.STRING, "srcitem",
								"The item you want to give").setRequired(true)
										.setAutoComplete(true),
						new OptionData(OptionType.INTEGER, "srcmoney",
								"The amount of money you want to give").setRequired(true),
						new OptionData(OptionType.STRING, "dstitem",
								"What item the target gives you in exchange").setRequired(true)
										.setAutoComplete(true),
						new OptionData(OptionType.INTEGER, "dstmoney",
								"The amount of money the target gives you").setRequired(true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account authorAccount = glados.getAccount(author);

		// Ensure the trade is possible (src owns the item and money, dst owns the item and money)
		String srcItem = event.getOption("srcitem").getAsString();
		int srcMoney = event.getOption("srcmoney").getAsInt();

		// Check src money and item
		if ((authorAccount.money < srcMoney) || !ItemUtils.userOwnItem(authorAccount, srcItem)) {
			source.sendMessageEmbeds(
					BuildEmbed.errorEmbed("You do not own enough money or the src item !").build())
					.queue();
			return;
		}

		// Check dst money and item



		String dstItem = event.getOption("dstitem").getAsString();
		int dstMoney = event.getOption("dstmoney").getAsInt();


		// Build the embed

		// Put the button accept and refuse trade

		source.sendMessageEmbeds(
				BuildEmbed.errorEmbed("This system has not been implemented yet !").build())
				.queue();
	}
}
