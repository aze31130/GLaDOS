package aze.GLaDOS.Events;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import aze.GLaDOS.Constants;
import aze.GLaDOS.Commands.Commands;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.GLaDOS;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReceived extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		GLaDOS glados = GLaDOS.getInstance();
		
		if((message.length == 1) && !event.getChannel().isNSFW() && !event.getChannel().getId().equals(Channels.NSFW.id)){
			for(int i = 0 ; i < glados.bannedWords.length() ; i++) {
				if(message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					break;
				}
			}
		}
		
		if(Constants.logMessage){
			String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
			System.out.println(time + "[" + event.getChannel().getParent().getName() + "][#" + event.getChannel().getName() + "][" + event.getAuthor().getAsTag() +  "]" + Arrays.toString(message));
		}
		
		if(message[0].startsWith(glados.prefix) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
			glados.addRequest();
			Commands.Main(event, glados);
		}
	}
}
