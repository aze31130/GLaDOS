package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonClick extends ListenerAdapter {
	public void onButtonInteraction(ButtonInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		Boolean success = true;
		switch (event.getComponentId()) {
			case "+Broadcast":
				event.getGuild()
						.addRoleToMember(event.getMember().getUser(),
								event.getGuild().getRoleById(glados.roleBroadcastMessenger))
						.queue();
				break;
			case "-Broadcast":
				event.getGuild()
						.removeRoleFromMember(event.getMember().getUser(),
								event.getGuild().getRoleById(glados.roleBroadcastMessenger))
						.queue();
				break;
			case "+Gamer":
				event.getGuild().addRoleToMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleGamer)).queue();
				break;
			case "-Gamer":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleGamer)).queue();
				break;
			case "+Member":
				event.getGuild().addRoleToMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleMember)).queue();
				break;
			case "-Member":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleMember)).queue();
				break;
			case "+Artistic":
				event.getGuild().addRoleToMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleArtistic)).queue();
				break;
			case "-Artistic":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleArtistic)).queue();
				break;
			case "+International":
				event.getGuild().addRoleToMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleInternational)).queue();
				break;
			case "-International":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleInternational)).queue();
				break;
			case "+Developer":
				event.getGuild().addRoleToMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleDeveloper)).queue();
				break;
			case "-Developer":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleDeveloper)).queue();
				break;
			case "-NSFW":
				event.getGuild().removeRoleFromMember(event.getMember().getUser(),
						event.getGuild().getRoleById(glados.roleNsfw)).queue();
				break;
			default:
				success = false;
				break;
		}

		if (success) {
			String action = "";
			if (event.getComponentId().startsWith("+")) {
				action = "added";
			}

			if (event.getComponentId().startsWith("-")) {
				action = "removed";
			}
			event.reply("Successfully " + action + " " + event.getComponentId().substring(1)
					+ " role !").setEphemeral(true).queue();
		} else {
			event.deferEdit().queue();
		}
	}
}
