package commands;

import java.util.List;
import java.util.Random;

import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Rng extends Command {
	public Rng(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer up = event.getOption("upperbound").getAsInt();
		Integer down = event.getOption("downbound").getAsInt();

		long rng = new Random().nextLong() % up;

		if (rng < 0) {
			rng *= -1;
		}
		source.sendMessage("The rng is: " + rng + " [ " + down + " - " + up + " ]").queue();
	}
}
