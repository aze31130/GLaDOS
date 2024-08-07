package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
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
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.STRING, "category", "The category you want to be helped with", true)
								.addChoice("rpg", "rpg")
								.addChoice("maths", "maths")
								.addChoice("system", "system")));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		EmbedBuilder help = BuildEmbed.helpEmbed();

		Tag commandTag = Tag.valueOf(event.getOption("category").getAsString().toUpperCase());

		help.setDescription("Showing help page of " + commandTag.toString().toLowerCase() + " category.");

		// Only display public commands
		for (Command c : g.commands)
			if (c.tag.equals(commandTag))
				help.addField(c.name, c.description, true);

		event.getHook().sendMessageEmbeds(help.build()).queue();
	}
}
