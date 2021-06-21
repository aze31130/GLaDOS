package aze.GLaDOS.commands;

import java.util.Random;
import aze.GLaDOS.utils.BuildEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Rng {
	public static void rng(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(message.length == 2){
			try {
				long rng = new Random().nextLong() % Long.parseLong(message[1]);
				if(rng < 0) {
					rng *= -1;
				}
				event.getChannel().sendMessage("The rng is: " + rng).queue();
			} catch(Exception e) {
				event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		} else {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed("Error, the command syntax is ?rng <positive number>").build()).queue();
		}
	}
}
