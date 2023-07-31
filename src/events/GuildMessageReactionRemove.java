package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionRemove extends ListenerAdapter {
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event){
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("member"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleMember)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("AAAAA"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleGamer)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("BBBBB"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleArtistic)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("CCCCC"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleInternational)).queue();
		}

		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("DDDDDD"))){
			event.getGuild().removeRoleFromMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleNsfw)).queue();
		}
	}
}
