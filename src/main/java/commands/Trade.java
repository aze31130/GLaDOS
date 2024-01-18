package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import accounts.Permission;

public class Trade extends Command {
	public Trade() {
		super("trade",
				"Request a trade with another user.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.USER, "destination",
								"The target of your trade offer").setRequired(true),
						new OptionData(OptionType.STRING, "srcitem",
								"The item you want to give"),
						new OptionData(OptionType.INTEGER, "amount",
								"The amount of money you want to give"),
						new OptionData(OptionType.STRING, "dstitem",
								"What the target gives you in exchange"),
						new OptionData(OptionType.INTEGER, "dstamount",
								"The amount of money the target gives you")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();

		// Ensure the trade is possible (src owns the item and money, dst owns the item and money)

		// Build the embed

		// Put the button accept and refuse trade

		source.sendMessageEmbeds(
				BuildEmbed.errorEmbed("This system has not been implemented yet !").build())
				.queue();
	}
}
