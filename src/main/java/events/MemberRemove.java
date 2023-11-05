package events;

import utils.BuildEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberRemove extends ListenerAdapter {
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		event.getGuild().getDefaultChannel().asTextChannel()
				.sendMessageEmbeds(
						BuildEmbed.joinLeaveEmbed(event.getUser().getGlobalName(), false).build())
				.queue();
	}
}
