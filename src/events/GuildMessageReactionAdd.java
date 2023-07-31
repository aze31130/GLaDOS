package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionAdd extends ListenerAdapter{
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("member"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleMember)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("🎮"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleGamer)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("🎨"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleArtistic)).queue();
		}
		
		if((event.getChannel().getName().equals(glados.channelRole)) && (event.getReactionEmote().getName().equals("🇺🇸"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(glados.roleInternational)).queue();
		}
	}
}
