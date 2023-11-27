package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import utils.BuildEmbed;

public class Factorielle extends Command {
	public Factorielle() {
		super("factorielle", "Computes given factorial number",
				Permissions.NONE, Arrays.asList(
						new OptionData(OptionType.INTEGER, "n", "F(n) you want to compute")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer n = event.getOption("n").getAsInt();

		if (n < 0) {
			source.sendMessageEmbeds(
					BuildEmbed.errorEmbed("Sorry, you cannot compute negative numbers").build())
					.queue();
			return;
		}

		/*
		 * Arbitrary limit to limit cpu usage
		 */
		if (n > 1000000)
			n = 1000000;

		/*
		 * Define a temporary limit to make sure midnight ranking is not affected
		 */
		if (n > 1000 && LocalDateTime.now().getHour() == 23) {
			source.sendMessageEmbeds(BuildEmbed
					.errorEmbed("Sorry, command limited to n = 1000 between 11pm to 12pm.").build())
					.queue();
			return;
		}

		source.sendTyping().queue();
		BigInteger f = new BigInteger("1");
		for (int i = 2; i <= n; i++) {
			f = f.multiply(BigInteger.valueOf(i));
		}

		// Write number to a file if too big
		if (f.toString().length() >= 2000) {
			InputStream inputStream = new ByteArrayInputStream(f.toString().getBytes());
			source.sendMessage("Factorial(" + n + ") = ")
					.addFiles(FileUpload.fromData(inputStream, "output.txt")).queue();
		} else {
			source.sendMessage("Factorial(" + n + ") = " + f).queue();
		}
	}
}
