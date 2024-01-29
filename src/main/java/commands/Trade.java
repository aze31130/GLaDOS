package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import utils.BuildEmbed;
import utils.ItemUtils;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Trade extends Command {
	public Trade() {
		super(
				"trade",
				"Request a trade with another user.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.USER, "target", "The target of your trade offer").setRequired(true),
						new OptionData(OptionType.STRING, "srcitem", "The item you want to give").setAutoComplete(true),
						new OptionData(OptionType.INTEGER, "srcmoney", "The amount of money you want to give"),
						new OptionData(OptionType.STRING, "dstitem", "What item the target gives you in exchange")
								.setAutoComplete(true),
						new OptionData(OptionType.INTEGER, "dstmoney", "The amount of money the target gives you")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();

		// Check if the target is valid
		if (event.getOption("target") == null) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("The target account does not exist !").build()).queue();
			return;
		}

		// Ensures trade is performed in a server, not in private message
		if (!event.isFromGuild()) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("You can only trade in a server !").build()).queue();
			return;
		}

		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);
		Account targetAccount = glados.getAccountById(event.getOption("target").getAsString());

		String srcItem = Optional.ofNullable(event.getOption("srcitem")).map(OptionMapping::getAsString).orElse("");
		int srcMoney = Optional.ofNullable(event.getOption("srcmoney")).map(OptionMapping::getAsInt).orElse(0);
		String dstItem = Optional.ofNullable(event.getOption("dstitem")).map(OptionMapping::getAsString).orElse("");
		int dstMoney = Optional.ofNullable(event.getOption("dstmoney")).map(OptionMapping::getAsInt).orElse(0);

		// Ensure the trade is possible
		if (!ItemUtils.isTradePossible(authorAccount, targetAccount, srcItem, srcMoney, dstItem, dstMoney)) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("Illegal trade ! Ensures the trade is possible !").build()).queue();
			return;
		}

		// Put the button accept and refuse trade
		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("AcceptTrade", "Accept Trade"),
				Button.danger("RefuseTrade", "Refuse Trade"));

		source.sendMessageEmbeds(
				BuildEmbed.tradeEmbed(authorAccount, targetAccount, srcItem, srcMoney, dstItem, dstMoney).build())
				.addActionRow(buttons).queue();
	}
}
