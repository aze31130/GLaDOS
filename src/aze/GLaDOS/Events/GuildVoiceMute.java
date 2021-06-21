package aze.GLaDOS.events;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceMute extends ListenerAdapter {
	public void onGuildVoiceMute(GuildVoiceMuteEvent event){
		//event.getGuild().getMemberById("").mute(true).queue();
		//event.getGuild().getMemberById("").deafen(true).queue();
	}
}
