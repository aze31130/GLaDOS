package aze.GLaDOS.events;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event){
		if(!event.getAuthor().isBot()){
	        //sendPrivateMessage(event.getAuthor(), "Private message trigger !");
			System.out.println("triggered !");
			//Channel channel
			
			//download the file
			//run the game
			//get the score
			//record it with the name of the user
			//send it to the bot command channel
		}
    }
}
