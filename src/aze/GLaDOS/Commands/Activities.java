package aze.GLaDOS.commands;

import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Activity;

public class Activities extends Command {
	public Activities(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Account account, TextChannel channel, String[] arguments) {
		if(arguments.length > 1){
			Boolean isValidActivity = true;
			String activity = "";
			for(int i = 1 ; i < arguments.length ; i++) {
				activity += " " + arguments[i];
			}
			switch(arguments[0]){
				case "listening":
					channel.getJDA().getPresence().setActivity(Activity.listening(activity));
					break;
				case "playing":
					channel.getJDA().getPresence().setActivity(Activity.playing(activity));
					break;
				case "watching":
					channel.getJDA().getPresence().setActivity(Activity.watching(activity));
					break;
				case "streaming":
					channel.getJDA().getPresence().setActivity(Activity.streaming(activity, "https://www.twitch.tv/ "));
					break;
				default:
					channel.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, arguments[1] + arguments.toString()));
					isValidActivity = false;
			}
			
			if(isValidActivity){
				EmbedBuilder sucess = new EmbedBuilder()
						.setColor(0x22ff2a)
						.setTitle("Successfully updated to " + arguments[0] + " " + activity + " activity.");
				channel.sendMessage(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder()
						.setColor(0xff3923)
						.setTitle("Error in the command")
						.setDescription("Unknown activity: " + arguments[0] + ". All activities are: <listening / playing / watching / streaming> <name>");
				channel.sendMessage(error.build()).queue();
			}
		} else {
			channel.sendMessage(BuildEmbed.errorEmbed("Usage: activity <listening / playing / watching / streaming> <name>").build()).queue();
		}
	}
}
