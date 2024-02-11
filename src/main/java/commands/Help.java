package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permission;
import glados.GLaDOS;

public class Help extends Command {
	public Help() {
		super(
				"help",
				"Shows an help page listing each commands",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		StringBuilder sb = new StringBuilder();

		sb.append("Command list:\n");

		for (Command c : g.commands)
			sb.append(c.name + " " + c.description + " " + c.permissionLevel.toString().toLowerCase());

		event.getHook().sendMessage(sb.toString()).queue();
	}
}
