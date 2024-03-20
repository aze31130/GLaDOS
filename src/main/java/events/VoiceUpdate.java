package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceUpdate extends ListenerAdapter {
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;
	}
}
