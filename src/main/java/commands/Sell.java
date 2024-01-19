package commands;

import java.util.Arrays;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;

public class Sell extends Command {
	public Sell() {
		super("sell",
				"Sells an item.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "item",
								"The item you want to sell").setRequired(true)
										.setAutoComplete(true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account authorAccount = glados.getAccount(author);

		String itemName = event.getOption("name").getAsString();

		// Ensure the owner own the item

		// Sells the item

		source.sendMessageEmbeds(
				BuildEmbed.errorEmbed("This system has not been implemented yet !").build())
				.queue();
	}

}
