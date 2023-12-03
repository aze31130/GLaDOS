package events;

import glados.GLaDOS;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceMute extends ListenerAdapter {
	public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;
		// event.getGuild().getMemberById("").mute(true).queue();
		// event.getGuild().getMemberById("").deafen(true).queue();
	}
}
