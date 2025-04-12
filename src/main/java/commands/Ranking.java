package commands;

import java.util.Arrays;
import java.util.Comparator;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
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
		User author = event.getUser();
		GLaDOS g = GLaDOS.getInstance();

		// Sort account list using inventory value
		g.accounts.sort(Comparator.comparingInt(Account::getInventoryValue).reversed());

		// Build embed
		EmbedBuilder ranking = BuildEmbed.rankingEmbed();

		int count = 1;
		for (Account a : g.accounts) {
			// Display only 10 first
			if (count < 10)
				ranking.addField(AccountUtils.getMedalEmoji(count) + a.user.getName(),
						StringsUtils.formatNumber(a.getInventoryValue()) + " :coin:" + " | " + StringsUtils.formatNumber(a.inventory.size())
								+ " items",
						false);

			if (a.user.getId().equals(author.getId()))
				ranking.setDescription("Your rank is " + count + " out of " + g.accounts.size());

			count++;
		}

		event.getHook().sendMessageEmbeds(ranking.build()).queue();
	}
}
