package events;

import glados.GLaDOS;
import commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;
import utils.PermissionsUtils;

public class SlashCommandInteraction extends ListenerAdapter {
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		for (Command command : glados.commands) {
			if (event.getName().equalsIgnoreCase(command.name)) {
				if (!PermissionsUtils.canExecute(event.getMember(), command.permissionLevel)) {
					event.replyEmbeds(BuildEmbed
							.errorEmbed("You need to have the " + command.permissionLevel.toString()
									+ " role in order to execute that.")
							.build()).queue();
					return;
				}
				// event.deferReply().queue();
				if (!command.name.equalsIgnoreCase("report"))
					event.reply("200 OK").queue();

				command.execute(event);
				return;
			}
		}
	}
}
