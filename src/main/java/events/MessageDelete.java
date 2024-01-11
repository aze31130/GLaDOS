package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDelete extends ListenerAdapter {
	public void onMessageDelete(MessageDeleteEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		g.requestsAmount++;
	}
}
