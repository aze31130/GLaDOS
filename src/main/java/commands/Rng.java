package commands;

import java.util.Random;
import utils.BuildEmbed;

import accounts.Permissions;

public class Rng extends Command {
	public Rng(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		if (args.arguments.length > 0) {
			try {
				long rng = new Random().nextLong() % Long.parseLong(args.arguments[0]);
				if (rng < 0) {
					rng *= -1;
				}
				args.channel.sendMessage("The rng is: " + rng).queue();
			} catch (Exception e) {
				args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		} else {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed("You need to provide a number !").build()).queue();
		}
	}
}
