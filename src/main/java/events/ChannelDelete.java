package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class ChannelDelete extends ListenerAdapter {
	public void onChannelDelete(ChannelDeleteEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;
		event.getJDA().getTextChannelById(glados.channelSystem).sendMessageEmbeds(
				BuildEmbed.loggerEmbed("Channel deleted", event.getChannel().getAsMention() + " "
						+ event.getChannel().getName()).build())
				.queue();
	}
}
