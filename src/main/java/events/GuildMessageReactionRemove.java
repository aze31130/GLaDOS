package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionRemove extends ListenerAdapter {
	public void onGuildMessageReactionRemove(MessageReactionRemoveEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;

		System.out.println("Reaction remove event !");
	}
}
