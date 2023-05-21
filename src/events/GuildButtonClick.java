package events;

import constants.Constants.Roles;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildButtonClick extends ListenerAdapter {
	public void onButtonClick(ButtonClickEvent event) {
		Boolean success = true;
		switch(event.getComponentId()) {
			case "+Gamer":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
				break;
			case "-Gamer":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
				break;
			case "+Member":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
				break;
			case "-Member":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
				break;
			case "+Artistic":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
				break;
			case "-Artistic":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
				break;
			case "+International":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
				break;
			case "-International":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
				break;
			case "+Developer":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.DEVELOPER.id)).queue();
				break;
			case "-Developer":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.DEVELOPER.id)).queue();
				break;
			case "-NSFW":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.NSFW.id)).queue();
				break;
			default:
				success = false;
				break;
		}
		
		if(success) {
			String action = "";
			if(event.getComponentId().startsWith("+")) {
				action = "added";
			}
			
			if(event.getComponentId().startsWith("-")) {
				action = "removed";
			}
			event.reply("Successfully " + action + " " + event.getComponentId().substring(1) + " role !").setEphemeral(true).queue();
		} else {
			event.deferEdit().queue();
		}
	}
}
