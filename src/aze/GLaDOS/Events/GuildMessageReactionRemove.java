package aze.GLaDOS.Events;

import aze.GLaDOS.Constants;
import aze.GLaDOS.Constants.Roles;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionRemove extends ListenerAdapter {
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event){
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("member"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🎮"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🎨"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🇺🇸"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
		}

		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("❤️"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.NSFW.id)).queue();
		}
	}
}
