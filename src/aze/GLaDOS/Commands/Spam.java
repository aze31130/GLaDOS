package aze.GLaDOS.commands;

import java.awt.Color;

import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.utils.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Spam extends Command {
	public Spam(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Account account, TextChannel channel, String[] arguments) {
		//String[] message = event.getMessage().getContentRaw().split("\\s+");
		//if(Permission.permissionLevel(member, 1)){
		String[] message = null;
		if(false) {
			if(message.length >= 3){
				int iterations = 0;
				try {
					iterations = Integer.parseInt(message[2]);
				} catch(Exception e){
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(Color.RED);
					error.setTitle("Exception !");
					error.setDescription(e.toString());
					channel.sendMessage(error.build()).queue();
				}
				channel.sendTyping().queue();
				channel.sendMessage("Queueing " + iterations + " iterations of pinging user").queue();
				while(iterations > 0){
					channel.sendMessage(message[1]).queue();
					iterations--;
				}
			} else {
				channel.sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(Color.RED);
				error.setTitle("Error");
				error.setDescription("The command syntax is ?hardspam (@User) <Amount>");
				channel.sendMessage(error.build()).queue();
			}
		} else {
			channel.sendTyping().queue();
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to have the Administrator role in order to execute that.");
			channel.sendMessage(error.build()).queue();
		}
	}
}
