package events;

import commands.Command;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;
import utils.PermissionsUtils;

public class ContextUser extends ListenerAdapter {
	public void onUserContextInteraction(UserContextInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		for (Command command : glados.commands) {
			if (event.getName().equalsIgnoreCase(command.name)) {
				if (!PermissionsUtils.canExecute(event.getMember(), command.permissionLevel)) {
					event.replyEmbeds(BuildEmbed.errorEmbed("You need to have the " + command.permissionLevel.toString()
							+ " role in order to execute that.").build()).queue();
					return;
				}

				try {
					event.deferReply().queue();
					command.executeContextUser(event);
				} catch (Exception e) {
					event.replyEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				}
				return;
			}
		}
	}
}
