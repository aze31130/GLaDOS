package events;

import utils.BuildEmbed;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberRemove extends ListenerAdapter {
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		TextChannel defaultChannel = event.getGuild().getDefaultChannel().asTextChannel();

		defaultChannel.sendMessageEmbeds(BuildEmbed.joinLeaveEmbed(event.getUser().getGlobalName(), false).build()).queue();
	}
}
