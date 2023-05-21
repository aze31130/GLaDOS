package commands;

import java.util.Random;
import utils.BuildEmbed;

public class Rng extends Command {
	public Rng(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(args.arguments.length > 0) {
			try {
				long rng = new Random().nextLong() % Long.parseLong(args.arguments[0]);
				if(rng < 0) {
					rng *= -1;
				}
				args.channel.sendMessage("The rng is: " + rng).queue();
			} catch(Exception e) {
				args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		} else {
			args.channel.sendMessage(BuildEmbed.errorEmbed("You need to provide a number !").build()).queue();
		}
	}
}
