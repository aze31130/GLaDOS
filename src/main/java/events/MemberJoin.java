package events;

import utils.BuildEmbed;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoin extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		TextChannel defaultChannel = event.getGuild().getDefaultChannel().asTextChannel();

		defaultChannel.sendMessageEmbeds(BuildEmbed.joinLeaveEmbed(event.getUser().getAsMention(), true).build()).queue();
	}
}
