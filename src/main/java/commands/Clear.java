package commands;

import java.util.Arrays;
import java.util.List;
import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permission;

public class Clear extends Command {
	public Clear() {
		super(
				"clear",
				"Clears the latests messages in a channel. Moderator privileges required",
				Permission.MODERATOR,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.INTEGER, "amount", "Amount of message you want to delete", true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer amount = event.getOption("amount").getAsInt();

		List<Message> messages = source.getHistory().retrievePast(amount).complete();
		source.deleteMessages(messages).queue();
		event.getHook().sendMessageEmbeds(BuildEmbed.successEmbed("Successfully deleted " + amount + " messages.").build())
				.queue();
	}
}
