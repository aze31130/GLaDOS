package commands;

import java.util.Arrays;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

public class Market extends Command {
	public Market() {
		super(
				"market",
				"Displays the market. Resets at midnight.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(new OptionData(OptionType.STRING, "item", "The item you want to buy", false, true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();

		// TODO Buy mechanism

		event.getHook().sendMessageEmbeds(BuildEmbed.marketEmbed(glados.market).build()).queue();
	}
}
