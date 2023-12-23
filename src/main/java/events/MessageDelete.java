package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class MessageDelete extends ListenerAdapter {
	public void onMessageDelete(MessageDeleteEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		g.requestsAmount++;

		if (event.getChannel().getId().equals(g.channelSystem))
			return;

		event.getJDA().getTextChannelById(g.channelSystem).sendMessageEmbeds(
				BuildEmbed
						.loggerEmbed("Message deletion",
								event.getMessageId() + " (" + event.getChannel().getAsMention()
										+ ")")
						.build())
				.queue();
	}
}
