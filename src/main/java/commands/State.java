package commands;

import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class State extends Command {
	public State(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		if (args.arguments.length > 0) {
			Boolean isValidState = true;
			switch (args.arguments[0]) {
				case "online":
					args.channel.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
					break;
				case "idle":
					args.channel.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
					break;
				case "dnd":
					args.channel.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
					break;
				default:
					isValidState = false;
			}

			if (isValidState) {
				EmbedBuilder sucess = new EmbedBuilder()
						.setColor(0x22ff2a)
						.setTitle("Successfully updated to " + args.arguments[0] + " state.");
				args.channel.sendMessageEmbeds(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder()
						.setColor(0xff3923)
						.setTitle("Error in the command");
				error.setDescription("Unknown state: " + args.arguments[0] + ". All states are: <online / idle / dnd>");
				args.channel.sendMessageEmbeds(error.build()).queue();
			}
		} else {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed("Usage: state <online / idle / dnd>").build()).queue();
		}
	}
}
