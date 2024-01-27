package commands;

import utils.Logging;
import java.util.Arrays;
import java.util.logging.Level;
import accounts.Permission;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Shutdown extends Command implements Logging {
	public Shutdown() {
		super("shutdown", "Gracely Shutdown GLaDOS. Moderator privileges required.",
				Permission.MODERATOR, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();

		Logging.LOGGER.log(Level.INFO, "Shutting down now !");

		source.sendMessage("Shutting down now !").queue();

		// Triggers the backup task
		new tasks.Backup(event.getJDA()).run();

		event.getJDA().shutdown();
		System.exit(0);
	}
}
