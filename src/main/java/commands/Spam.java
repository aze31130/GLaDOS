package commands;

import utils.BuildEmbed;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Spam extends Command {
	public Spam(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
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
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("The command syntax is ?spam @User <Amount>").build())
					.queue();
		}
	}
}
