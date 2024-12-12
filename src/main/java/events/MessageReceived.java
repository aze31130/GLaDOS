package events;

import java.util.Random;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {

	final String dropbotCommands[] = {
			"|d",
			"|c",
			"|g -1"
	};

	public void onMessageReceived(MessageReceivedEvent event) {
		MessageChannelUnion channel = event.getChannel();
		Message message = event.getMessage();
		String[] messageContent = message.getContentRaw().split("\\s+");

		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		/*
		 * Check for banned words
		 */
		if (event.isFromGuild() && !channel.asTextChannel().isNSFW() && !channel.getId().equals(glados.channelNsfw)) {
			for (int i = 0; i < glados.bannedWords.length(); i++) {
				if (messageContent[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					message.delete().queue();
					return;
				}
			}
		}

		if (message.getContentRaw().contains(event.getJDA().getSelfUser().getId())) {
			if (new Random().nextInt(10) >= 2) {
				channel.sendMessage(glados.randomQuote.getString(new Random().nextInt(glados.randomQuote.length()))).queue();
			}
		}

		/*
		 * Ensure every message received in survey channel contains a poll
		 */
		if (message.getChannelId().equals(glados.channelVote) && message.getPoll() == null)
			message.delete().queue();


		// Check self sender
		for (String dropbotCommand : dropbotCommands)
			if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().contains(dropbotCommand)
					&& (new Random().nextInt(15) < 1))
				channel.sendMessage(dropbotCommand).queue();
	}
}
