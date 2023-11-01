package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionAdd extends ListenerAdapter {
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
	}
}
