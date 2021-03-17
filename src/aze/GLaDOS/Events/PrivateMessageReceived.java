package aze.GLaDOS.Events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event){
		if(!event.getAuthor().isBot()){
	        sendPrivateMessage(event.getAuthor(), "Private message trigger !");
		}
    }
	
	public void sendPrivateMessage(User user, String content) {
		// openPrivateChannel provides a RestAction<PrivateChannel>
		// which means it supplies you with the resulting channel
		user.openPrivateChannel().queue((channel) -> {
			channel.sendMessage(content).queue();
		});
	}
}
