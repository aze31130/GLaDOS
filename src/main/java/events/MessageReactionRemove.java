package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionRemove extends ListenerAdapter {
	public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
	}
}
