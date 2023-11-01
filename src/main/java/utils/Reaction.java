package utils;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Reaction {
	// React to every message in a text channel
	public static void reactToChannel(MessageChannel channel) {
		// channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
		// me.addReaction(channel.getGuild().getEmoteById("780782089355395093")).queue();
		// return true;
		// });
	}
}
