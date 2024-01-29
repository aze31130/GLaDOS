package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class ChannelCreate extends ListenerAdapter {
	public void onChannelCreate(ChannelCreateEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;
		event.getJDA().getTextChannelById(glados.channelSystem).sendMessageEmbeds(
				BuildEmbed.loggerEmbed("Channel created",
						event.getChannel().getAsMention() + " " + event.getChannel().getName())
						.build())
				.queue();
	}
}
