package commands;

import utils.Logging;
import java.util.Arrays;
import java.util.logging.Level;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;

public class Shutdown extends Command implements Logging {
	public Shutdown() {
		super(
				"shutdown",
				"Gracely Shutdown GLaDOS. Moderator privileges required.",
				Permission.MODERATOR,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList());
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		Logging.LOGGER.log(Level.INFO, "Shutting down now !");

		event.getHook().sendMessage("Shutting down now !").queue();

		// Triggers the backup task
		new tasks.Backup(event.getJDA()).run();

		event.getJDA().shutdown();
		System.exit(0);
	}
}
