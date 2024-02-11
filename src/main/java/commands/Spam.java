package commands;

import utils.BuildEmbed;
import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Spam extends Command {
	public Spam() {
		super(
				"spam",
				"Spam-mention a given user. Moderator privileges required.",
				Permission.MODERATOR,
				Arrays.asList(
						new OptionData(OptionType.MENTIONABLE, "target", "Person you want to annoy", true),
						new OptionData(OptionType.INTEGER, "amount", "The amount of mention you want to generate", true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		Integer amount = event.getOption("amount").getAsInt();
		String mention = event.getOption("target").getAsMember().getAsMention();

		if ((amount < 0) || (amount > 100)) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Sorry, you cannot spam this amount of time").build())
					.queue();
			return;
		}

		event.getHook().sendMessage("Spamming " + amount + " time !").queue();

		while (amount > 0) {
			event.getHook().sendMessage(mention).queue();
			amount--;
		}
	}
}
