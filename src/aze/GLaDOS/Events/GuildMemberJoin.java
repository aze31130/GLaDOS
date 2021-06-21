package aze.GLaDOS.events;

import aze.GLaDOS.utils.BuildEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		event.getGuild().getDefaultChannel().sendMessage(BuildEmbed.joinLeaveEmbed(event.getUser().getAsMention(), true).build()).queue();
	}
}
