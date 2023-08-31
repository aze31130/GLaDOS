package events;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import commands.Argument;
import commands.Command;
import utils.Logger;
import utils.Mention;
import glados.GLaDOS;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReceived extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		Boolean isDeleted = false;
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;

		if(glados.logMessages){
			System.out.println(new Logger(true) + "[" + event.getChannel().getParent().getName() + "][#" + event.getChannel().getName() + "][" + event.getAuthor().getAsTag() +  "]" + Arrays.toString(message));
		}
		
		if((message.length == 1) && !event.getChannel().isNSFW() && !event.getChannel().getId().equals(glados.channelNsfw)){
			for(int i = 0 ; i < glados.bannedWords.length() ; i++) {
				if(message[0].equalsIgnoreCase(glados.bannedWords.get(i).toString())) {
					event.getMessage().delete().queue();
					isDeleted = true;
					break;
				}
			}
		}
		
		if(!isDeleted) {
			// if(glados.leveling) {
			// 	Account a = glados.getAccount(event.getMember().getId());
			// 	if(a != null && a.level < glados.maxLevel) {
			// 		a.experience += 1;
			// 		Levels.checkLevelUp(a);
			// 	}
			// 	a.totalExperience += 1;
			// }

			if (event.getMessage().getContentRaw().startsWith("|c")) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Not claimed !");
				embed.setDescription("Here are NOT your daily Schards: **+" + new Random().nextInt(3000) + "** || (??? in total)");
				embed.setColor(Color.ORANGE);
				event.getChannel().sendMessage(embed.build()).queue();
			}

			if (event.getMessage().getContentRaw().startsWith("|d")) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("You did not get a card today :-(");
				embed.setColor(Color.gray);
				event.getChannel().sendMessage(embed.build()).queue();
			}
			
			if(event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getId())) {
				if(new Random().nextInt(100) >= 5) {
					event.getChannel().sendMessage(Mention.randomAnswer()).queue();
				}
			}
			
			if(message[0].startsWith(glados.prefix) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
				String command = message[0].substring(1);
				String[] arguments = Arrays.copyOfRange(message, 1, message.length);;
				
				for(Command c : glados.commands) {
					if(c.name.equalsIgnoreCase(command) || c.alias.equalsIgnoreCase(command)) {
						glados.addRequest();
						c.execute(new Argument(event.getMember(), event.getChannel(), arguments, event.getMessage().getAttachments()));
					}
				}
			}
		}
	}
}
