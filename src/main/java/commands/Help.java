package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import accounts.Permission;
import glados.GLaDOS;

public class Help extends Command {
	public Help() {
		super(
				"help",
				"Shows an help page listing each commands",
				Permission.NONE,
				Tag.SYSTEM,
				Arrays.asList(
						new OptionData(OptionType.STRING, "category", "The category you want to be helped with", true)
								.addChoice("rpg", "rpg")
								.addChoice("maths", "maths")
								.addChoice("system", "system")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		EmbedBuilder help = BuildEmbed.helpEmbed();

		// Only display public commands
		for (Command c : g.commands)
			if (c.permissionLevel.equals(Permission.NONE))
				help.addField(c.name, c.description, true);

		event.getHook().sendMessageEmbeds(help.build()).queue();
	}
}
