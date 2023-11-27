package commands;

import java.util.Arrays;
import java.util.List;
import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class Clear extends Command {
	public Clear() {
		super("clear",
				"Clears the latests messages in a channel. Admin privileges required",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.INTEGER, "amount",
						"Amount of message you want to delete")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer amount = event.getOption("amount").getAsInt();

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
