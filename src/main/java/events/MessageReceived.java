package events;

import java.util.Arrays;
import java.util.Random;
import commands.Argument;
import commands.Command;
import utils.Logger;
import utils.Mention;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		Boolean isDeleted = false;
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;

		if (glados.logMessages) {
			System.out.println(new Logger(true) + "[" + event.getChannel().asTextChannel().getName()
					+ "][#" + event.getChannel().getName() + "][" + event.getAuthor().getName()
					+ "]" + Arrays.toString(message));
		}

		if ((message.length == 1) && !event.getChannel().asTextChannel().isNSFW()
				&& !event.getChannel().getId().equals(glados.channelNsfw)) {
			for (int i = 0; i < glados.bannedWords.length(); i++) {
				if (message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					isDeleted = true;
					break;
				}
			}
		}

		if (!isDeleted) {
			// if(glados.leveling) {
			// Account a = glados.getAccount(event.getMember().getId());
			// if(a != null && a.level < glados.maxLevel) {
			// a.experience += 1;
			// Levels.checkLevelUp(a);
			// }
			// a.totalExperience += 1;
			// }

			if (event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getId())) {
				if (new Random().nextInt(100) >= 15) {
					event.getChannel().sendMessage(Mention.randomAnswer()).queue();
				}
			}

			if (message[0].startsWith(glados.prefix)
					&& !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
				String command = message[0].substring(1);
				String[] arguments = Arrays.copyOfRange(message, 1, message.length);;

				for (Command c : glados.commands) {
					if (c.name.equalsIgnoreCase(command)) {
						glados.addRequest();
						c.execute(new Argument(event.getMember(), event.getChannel(), arguments,
								event.getMessage().getAttachments()));
					}
				}
			}
		}
	}
}
