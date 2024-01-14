package events;

import java.util.Random;
import utils.Mention;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		if ((message.length == 1) && !event.getChannel().asTextChannel().isNSFW()
				&& !event.getChannel().getId().equals(glados.channelNsfw)) {
			for (int i = 0; i < glados.bannedWords.length(); i++) {
				if (message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					return;
				}
			}
		}

		// if(glados.leveling) {
		// Account a = glados.getAccount(event.getMember().getId());
		// if(a != null && a.level < glados.maxLevel) {
		// a.experience += 1;
		// Levels.checkLevelUp(a);
		// }
		// a.totalExperience += 1;
		// }

		if (event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getId())) {
			if (new Random().nextInt(100) >= 30) {
				event.getChannel().sendMessage(Mention.randomAnswer()).queue();
			}
		}
	}
}
