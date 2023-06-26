package events;

import main.GLaDOS;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceJoin extends ListenerAdapter {
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		//TextChannel channel = event.getJDA().getTextChannelById("");
		//channel.sendMessage("User " + event.getMember().getEffectiveName() + " connected in " + event.getChannelJoined().getName()).queue();
	}
}
