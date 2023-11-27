package commands;

import utils.BuildEmbed;
import java.util.Arrays;

import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Spam extends Command {
	public Spam() {
		super("spam", "Spam-mention a given user. Admin privileges required",
				Permissions.MODERATOR,
				Arrays.asList(
						new OptionData(OptionType.MENTIONABLE, "target",
								"Person you want to annoy"),
						new OptionData(OptionType.INTEGER, "amount",
								"The amount of mention you want to generate")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer amount = event.getOption("amount").getAsInt();
		String mention = event.getOption("target").getAsMember().getAsMention();

		if ((amount < 0) || (amount > 100)) {
			source.sendMessageEmbeds(
					BuildEmbed.errorEmbed("Sorry, you cannot spam this amount of time").build())
					.queue();
			return;
		}

		source.sendMessage("Spamming " + amount + " time !").queue();

		while (amount > 0) {
			source.sendMessage(mention).queue();
			amount--;
		}
	}
}
