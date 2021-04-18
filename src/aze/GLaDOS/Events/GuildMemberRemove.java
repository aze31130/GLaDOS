package aze.GLaDOS.Events;

import aze.GLaDOS.Utils.BuildEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRemove extends ListenerAdapter {
	public void onGuildMemberRemove(GuildMemberRemoveEvent event){
		event.getGuild().getDefaultChannel().sendMessage(BuildEmbed.joinLeaveEmbed(event.getUser().getAsMention(), false).build()).queue();
	}
}
