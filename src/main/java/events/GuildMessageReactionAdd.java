package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionAdd extends ListenerAdapter {
	public void onGuildMessageReactionAdd(MessageReactionAddEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		System.out.println("Reaction add event !");
	}
}
