package events;

import java.util.Random;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		if (event.isFromGuild() && !event.getChannel().asTextChannel().isNSFW()
				&& !event.getChannel().getId().equals(glados.channelNsfw)) {
			for (int i = 0; i < glados.bannedWords.length(); i++) {
				if (message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					return;
				}
			}
		}

		if (event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getId())) {
			if (new Random().nextInt(10) >= 3) {
				event.getChannel().sendMessage(glados.randomQuote.getString(new Random().nextInt(glados.randomQuote.length())))
						.queue();
			}
		}

		final String dropbotCommands[] = {
				"|d",
				"|c",
				"|g -1"
		};
		// Check self sender
		for (String dropbotCommand : dropbotCommands)
			if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().contains(dropbotCommand)
					&& (new Random().nextInt(10) < 2))
				event.getChannel().sendMessage(dropbotCommand).queue();
	}
}
