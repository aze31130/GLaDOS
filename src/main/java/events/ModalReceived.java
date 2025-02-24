package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class ModalReceived extends ListenerAdapter {
	public void onModalInteraction(ModalInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		String subject = event.getValue("subject").getAsString();
		String body = event.getValue("body").getAsString();
		User author = event.getUser();
		String reactions = event.getValue("reactions").getAsString();

		event.getJDA().getTextChannelById(glados.getRoleId("vote").get())
				.sendMessageEmbeds(BuildEmbed.modalEmbed(subject, body, reactions, author).build())
				.queue(message -> {
					for (Character c : reactions.toCharArray()) {
						if (Character.isAlphabetic(c) || Character.isDigit(c))
							continue;
						try {
							message.addReaction(Emoji.fromUnicode(c.toString())).queue();
						} catch (Exception e) {
							System.err.println("Unable to react with emote " + c);
						}
					}
				});

		event.replyEmbeds(BuildEmbed.successEmbed("Vote successfully posted in survey channel").build()).queue();
	}
}
