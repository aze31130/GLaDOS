package commands;

import java.util.Arrays;
import java.util.Random;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;

public class Rng extends Command {
	public Rng() {
		super(
				"rng",
				"Generate a random number using 'perfect and totally not rigged' random",
				Permission.NONE,
				Tag.MATHS,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.INTEGER, "lower_bound", "The lower bound is included", true),
						new OptionData(OptionType.INTEGER, "upper_bound", "The upper bound is included", true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		Integer up = event.getOption("upperbound").getAsInt();
		Integer down = event.getOption("downbound").getAsInt();

		long rng = new Random().nextLong() % up;

		if (rng < 0)
			rng *= -1;

		event.getHook().sendMessage("The rng is: " + rng + " [ " + down + " - " + up + " ]").queue();
	}
}
