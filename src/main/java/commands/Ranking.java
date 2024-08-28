package commands;

import java.util.Arrays;
import java.util.Comparator;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.AccountUtils;
import utils.BuildEmbed;
import utils.StringsUtils;

public class Ranking extends Command {
	public Ranking() {
		super(
				"ranking",
				"Ranks all players, based on their inventory's value.",
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
		GLaDOS g = GLaDOS.getInstance();

		// Sort account list using inventory value
		g.accounts.sort(Comparator.comparingInt(Account::getInventoryValue).reversed());

		// Build embed
		EmbedBuilder ranking = BuildEmbed.rankingEmbed();

		// Display it (10 first)
		int count = 1;
		for (Account a : g.accounts) {
			if (count > 10)
				break;

			ranking.addField(AccountUtils.getMedalEmoji(count) + a.user.getName(),
					StringsUtils.formatNumber(a.getInventoryValue()) + " :coin:", false);
			count++;
		}

		// TODO Adds member place
		// ranking.setDescription("Your rank is " + 0);
		event.getHook().sendMessageEmbeds(ranking.build()).queue();
	}
}
