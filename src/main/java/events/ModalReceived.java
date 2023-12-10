package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class ModalReceived extends ListenerAdapter {
	public void onModalInteraction(ModalInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		String subject = event.getValue("subject").getAsString();
		String body = event.getValue("body").getAsString();
		String author = event.getUser().getName();

		event.replyEmbeds(BuildEmbed.modalEmbed(subject, body, author).build()).queue();
	}
}
