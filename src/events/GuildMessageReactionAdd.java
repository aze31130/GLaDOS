package events;

import constants.Constants;
import constants.Constants.Roles;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionAdd extends ListenerAdapter{
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("member"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.MEMBER.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🎮"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.GAMER.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🎨"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.ARTISTIC.id)).queue();
		}
		
		if((event.getChannel().getName().equals(Constants.RoleChannelName)) && (event.getReactionEmote().getName().equals("🇺🇸"))){
			event.getGuild().addRoleToMember(event.getMember().getUser().getId(), event.getGuild().getRoleById(Roles.INTERNATIONAL.id)).queue();
		}
	}
}
