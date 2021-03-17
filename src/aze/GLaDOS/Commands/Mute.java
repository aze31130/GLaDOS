package aze.GLaDOS.Commands;

import aze.GLaDOS.Constants;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Mute {
	public static void mute(GuildMessageReceivedEvent event){
		
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		
		if(message[0].equalsIgnoreCase(Constants.commandPrefix + "mute")){
			if(message.length > 1 && message.length < 3){
				Member member = event.getGuild().getMemberById(message[1].replace("<@", "").replace(">", ""));
				Role role = event.getGuild().getRoleById("");
				
				if(!member.getRoles().contains(role)){
					//Mute the user
					event.getGuild().addRoleToMember(member, role).complete();
					event.getChannel().sendMessage("Muted " + message[1] + ".").queue();
				} else {
					//Unmute the user
					event.getGuild().removeRoleFromMember(member, role);
					event.getChannel().sendMessage("Muted " + message[1] + ".").queue();
				}
				
				if(message.length == 3){
					
				} else {
					
				}
			} else {
				event.getChannel().sendMessage("Incorrect syntax. Use ?mute [member] [time (optional)]");
			}
		}
	}
}
