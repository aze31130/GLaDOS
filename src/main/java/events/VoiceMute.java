package events;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceMute extends ListenerAdapter {
	public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
		System.out.print("Muted");
		// event.getGuild().getMemberById("").mute(true).queue();
		// event.getGuild().getMemberById("").deafen(true).queue();
	}
}