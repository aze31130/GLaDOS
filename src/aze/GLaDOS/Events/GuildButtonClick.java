package aze.GLaDOS.events;

import aze.GLaDOS.Constants.Roles;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildButtonClick extends ListenerAdapter {
	public void onButtonClick(ButtonClickEvent event) {
		switch(event.getComponentId()) {
			case "AddGamer":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
				break;
			case "RemoveGamer":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
				break;
			case "AddMember":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
				break;
			case "RemoveMember":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
				break;
			case "AddArtistic":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
				break;
			case "RemoveArtistic":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
				break;
			case "AddInternational":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
				break;
			case "RemoveInternational":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
				break;
			case "AddDeveloper":
				event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.DEVELOPER.id)).queue();
				break;
			case "RemoveDeveloper":
				event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.DEVELOPER.id)).queue();
				break;
			default:
				break;
		}
		event.reply("executed " + event.getMember().getAsMention()).queue();
	}
}
