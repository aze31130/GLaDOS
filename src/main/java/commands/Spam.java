package commands;

import utils.BuildEmbed;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Spam extends Command {
	public Spam(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
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
