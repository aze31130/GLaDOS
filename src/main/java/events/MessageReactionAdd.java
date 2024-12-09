package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionAdd extends ListenerAdapter {

	public static final String pinReaction = "⭐";

	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		if (event.getEmoji().getName().equals(pinReaction)) {
			Message reactedMessage = event.retrieveMessage().complete();

			// Get amount of reaction
			long amountOfStars =
					reactedMessage.getReactions().stream().filter(r -> r.getEmoji().getName().equals(pinReaction)).count();

			if (amountOfStars >= 5 && !reactedMessage.isPinned())
				reactedMessage.pin().complete();
		}
	}
}
