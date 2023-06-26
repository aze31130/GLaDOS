package events;

import main.GLaDOS;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceLeave extends ListenerAdapter {
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event){
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		// TextChannel channel = event.getJDA().getTextChannelById("");
		// channel.sendMessage("User " + event.getMember().getEffectiveName() + " disconnected in " + event.getChannelLeft().getName()).queue();
	}
}
