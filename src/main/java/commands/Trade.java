package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
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
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.USER, "target", "The target of your trade offer", true),
						new OptionData(OptionType.STRING, "srcitem", "The item you want to give", true, true),
						new OptionData(OptionType.STRING, "dstitem", "What item the target gives you in exchange", true, true),
						new OptionData(OptionType.INTEGER, "srcmoney", "The amount of money you want to give"),
						new OptionData(OptionType.INTEGER, "dstmoney", "The amount of money the target gives you")));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		// Ensures trade is performed in a server, not in private message
		if (!event.isFromGuild()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You can only trade in a server !").build()).queue();
			return;
		}

		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		Optional<Account> optionnalTargetAccount = glados.getAccountById(event.getOption("target").getAsString());

		if (optionnalTargetAccount.isEmpty()) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("This user does not have an account. He needs to /drop first !").build()).queue();
			return;
		}

		Account targetAccount = optionnalTargetAccount.get();

		Optional<items.Item> srcItem = authorAccount.getItemByFQName(event.getOption("srcitem").getAsString());
		Optional<items.Item> dstItem = targetAccount.getItemByFQName(event.getOption("dstitem").getAsString());
		int srcMoney = Optional.ofNullable(event.getOption("srcmoney")).map(OptionMapping::getAsInt).orElse(0);
		int dstMoney = Optional.ofNullable(event.getOption("dstmoney")).map(OptionMapping::getAsInt).orElse(0);

		if (srcItem.isEmpty() || dstItem.isEmpty()) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("Someone in the trade does not own the provided item !").build()).queue();
			return;
		}

		// Ensure the trade is possible
		if (!ItemUtils.isTradePossible(authorAccount, targetAccount, srcItem.get(), srcMoney, dstItem.get(), dstMoney)) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Illegal trade ! Ensures the trade is possible !").build())
					.queue();
			return;
		}

		// Put the button accept and refuse trade
		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("AcceptTrade", "Accept Trade"),
				Button.danger("RefuseTrade", "Refuse Trade"));

		event.getHook().sendMessageEmbeds(
				BuildEmbed.tradeEmbed(authorAccount, targetAccount, srcItem.get(), srcMoney, dstItem.get(), dstMoney).build())
				.addActionRow(buttons).queue();
		event.getHook().sendMessage("<@" + targetAccount.id + "> :arrow_heading_up:").queue();
	}
}
