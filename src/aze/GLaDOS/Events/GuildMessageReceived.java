package aze.GLaDOS.events;

import java.util.Arrays;
import aze.GLaDOS.Constants;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.commands.Commands;
import aze.GLaDOS.utils.Logger;
import aze.GLaDOS.GLaDOS;
import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.accounts.Levels;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReceived extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		Boolean isDeleted = false;
		GLaDOS glados = GLaDOS.getInstance();

		if(Constants.logMessage){
			System.out.println(new Logger(true) + "[" + event.getChannel().getParent().getName() + "][#" + event.getChannel().getName() + "][" + event.getAuthor().getAsTag() +  "]" + Arrays.toString(message));
		}
		
		if((message.length == 1) && !event.getChannel().isNSFW() && !event.getChannel().getId().equals(Channels.NSFW.id)){
			for(int i = 0 ; i < glados.bannedWords.length() ; i++) {
				if(message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					isDeleted = true;
					break;
				}
			}
		}
		
		if(!isDeleted) {
			if(glados.leveling) {
				Account a = glados.getAccount(event.getMember().getId());
				if(a.level < glados.maxLevel) {
					a.experience += 1;
					Levels.checkLevelUp(a);
				}
				a.totalExperience += 1;
			}
			
			if(message[0].startsWith(glados.prefix) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
				glados.addRequest();
				
				Commands.Main(event, glados);
				/*
				for(Command c : glados.commands) {
					if(c.name.equalsIgnoreCase(glados.prefix + message[0])) {
						Commands.Main(event, glados);
					}
				}*/
			}
		}
	}
}
