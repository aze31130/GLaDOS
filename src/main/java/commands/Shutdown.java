package commands;

import utils.Logger;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Shutdown extends Command {
	public Shutdown(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();

		System.out.println(new Logger(true) + " Shutting down now !");
		source.sendMessage("Shutting down now !").queue();
		event.getJDA().shutdown();
		System.exit(0);
	}
}
