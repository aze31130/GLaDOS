package commands;

import utils.Logger;
import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Shutdown extends Command {
	public Shutdown() {
		super("shutdown", "Gracely Shutdown GLaDOS. Moderator privileges required.",
				Permission.MODERATOR, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();

		System.out.println(new Logger(true) + " Shutting down now !");
		source.sendMessage("Shutting down now !").queue();

		// Triggers the backup task
		new tasks.Backup(event.getJDA()).run();

		event.getJDA().shutdown();
		System.exit(0);
	}
}
