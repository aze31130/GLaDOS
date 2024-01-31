package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionAdd extends ListenerAdapter {
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		// Check if reaction is ⭐
		if (event.getEmoji().getName().equals("⭐")) {
			Message reactedMessage = event.retrieveMessage().complete();

			// Get amount of reaction
			long amountOfStars = reactedMessage.getReactions().stream().filter(r -> r.getEmoji().getName().equals("⭐")).count();

			if (amountOfStars >= 5 && !reactedMessage.isPinned()) {
				reactedMessage.pin().complete();
			}
		}
	}
}
