package utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class StatsUtils {
	public static void ListChannelMessage(MessageChannel channel) {
		channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
			ReactPerMessage(me);
			return true;
		});
	}

	public static void ReactPerMessage(Message m) {
		System.out.println(m.getContentRaw() + " # " + m.getJumpUrl());
		for (MessageReaction mr : m.getReactions()) {

			// if (!isInEmote(emotes, mr.getReactionEmote().getName())) {
			// emotes.add(new EmoteCounter(mr.getReactionEmote().getName()));
			// }

			// addEmote(emotes, mr.getReactionEmote().getName(), mr.getCount());
		}
	}

	public int MemberAmount(Boolean includeBot) {
		// int guildMembers = event.getGuild().getMembers().stream().filter(member ->
		// !member.getUser().isBot()).count();
		// int guildBots = event.getGuild().getMembers().stream().filter(member ->
		// member.getUser().isBot()).count();
		return 0;
	}
}
