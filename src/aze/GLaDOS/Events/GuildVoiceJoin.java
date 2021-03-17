package aze.GLaDOS.Events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceJoin extends ListenerAdapter {
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
		TextChannel channel = event.getJDA().getTextChannelById("");
		channel.sendMessage("User " + event.getMember().getEffectiveName() + " connected in " + event.getChannelJoined().getName()).queue();
	}
}
