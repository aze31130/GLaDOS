package commands;

import utils.BuildEmbed;
import utils.Permission;

public class Spam extends Command {
	public Spam(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		if (Permission.permissionLevel(args.member, 2)) {
			if (args.arguments.length > 0) {
				int iterations = 0;
				try {
					iterations = Integer.parseInt(args.arguments[1]);
				} catch (Exception e) {
					args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				}
				args.channel.sendMessage("Spamming " + iterations + " time !").queue();
				String message = args.arguments[0].toString();
				if (!message.startsWith("<@") && !message.endsWith(">")) {
					message = "<@" + args.arguments[0] + ">";
				}
				while (iterations > 0) {
					args.channel.sendMessage(message).queue();
					iterations--;
				}
			} else {
				args.channel
						.sendMessageEmbeds(BuildEmbed.errorEmbed("The command syntax is ?spam @User <Amount>").build())
						.queue();
			}
		} else {
			args.channel
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("You need to have the Administrator role in order to execute that.").build())
					.queue();
		}
	}
}
