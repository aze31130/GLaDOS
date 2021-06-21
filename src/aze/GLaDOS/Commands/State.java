package aze.GLaDOS.commands;

import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;

public class State extends Command {
	public State(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Account account, TextChannel channel, String[] arguments) {
		if(arguments.length > 0){
			Boolean isValidState = true;
			switch(arguments[0]){
			case "online":
				channel.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
				break;
			case "idle":
				channel.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
				break;
			case "dnd":
				channel.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				break;
			default:
				isValidState = false;
			}
			
			if(isValidState){
				EmbedBuilder sucess = new EmbedBuilder()
						.setColor(0x22ff2a)
						.setTitle("Successfully updated to " + arguments[0] + " state.");
				channel.sendMessage(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder()
						.setColor(0xff3923)
						.setTitle("Error in the command");
				error.setDescription("Unknown state: " + arguments[0] + ". All states are: <online / idle / dnd>");
				channel.sendMessage(error.build()).queue();
			}
		} else {	
			channel.sendMessage(BuildEmbed.errorEmbed("Usage: state <online / idle / dnd>").build()).queue();
		}
	}
}
