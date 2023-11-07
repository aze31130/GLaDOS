package commands;

import java.util.List;
import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class Clear extends Command {
	public Clear(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer amount = event.getOption("amount").getAsInt();

		// source.sendMessageEmbeds(BuildEmbed.errorEmbed("Usage: /clear [1-100]").build()).queue();
		try {
			List<Message> messages = source.getHistory().retrievePast(amount).complete();
			source.deleteMessages(messages).queue();
			source.sendMessageEmbeds(BuildEmbed
					.successEmbed("Successfully deleted " + amount + " messages.").build()).queue();
		} catch (Exception exception) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed(exception.toString()).build()).queue();
		}

	}
}
