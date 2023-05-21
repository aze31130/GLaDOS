package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Status extends Command {
	public Status(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(args.arguments.length > 1){
			Boolean isValidActivity = true;
			String activity = "";
			for(int i = 1 ; i < args.arguments.length ; i++) {
				activity += " " + args.arguments[i];
			}
			switch(args.arguments[0]){
				case "listening":
					args.channel.getJDA().getPresence().setActivity(Activity.listening(activity));
					break;
				case "playing":
					args.channel.getJDA().getPresence().setActivity(Activity.playing(activity));
					break;
				case "watching":
					args.channel.getJDA().getPresence().setActivity(Activity.watching(activity));
					break;
				case "streaming":
					args.channel.getJDA().getPresence().setActivity(Activity.streaming(activity, "https://www.twitch.tv/ "));
					break;
				default:
					args.channel.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, args.arguments[1] + args.arguments.toString()));
					isValidActivity = false;
			}
			
			if(isValidActivity){
				EmbedBuilder sucess = new EmbedBuilder()
						.setColor(0x22ff2a)
						.setTitle("Successfully updated to" + args.arguments[0] + " " + activity + " activity.");
				args.channel.sendMessage(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder()
						.setColor(0xff3923)
						.setTitle("Error in the command")
						.setDescription("Unknown activity: " + args.arguments[0] + ". All activities are: <listening / playing / watching / streaming> <name>");
				args.channel.sendMessage(error.build()).queue();
			}
		} else {
			args.channel.sendMessage(BuildEmbed.errorEmbed("Usage: activity <listening / playing / watching / streaming> <name>").build()).queue();
		}
	}
}
