package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import utils.BuildEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import accounts.Permission;

public class Fibonacci extends Command {
	public Fibonacci() {
		super(
				"fibonacci",
				"Computes given fibonacci number",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.INTEGER, "n", "F(n) you want to compute")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		Integer n = event.getOption("n").getAsInt();

		if (n < 0) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Sorry, you cannot compute negative numbers").build())
					.queue();
			return;
		}

		/*
		 * Arbitrary limit to limit cpu usage
		 */
		if (n > 10000)
			n = 10000;

		/*
		 * Define a temporary limit to make sure midnight ranking is not affected
		 */
		if (n > 1000 && LocalDateTime.now().getHour() == 23) {
			event.getHook()
					.sendMessageEmbeds(BuildEmbed.errorEmbed("Sorry, command limited to n = 1000 between 11pm to 12pm.").build())
					.queue();
			return;
		}

		BigInteger f = new BigInteger("1");
		if (n < 2) {
			f = BigInteger.ONE;
		} else {
			BigInteger a = BigInteger.valueOf(0);
			BigInteger b = BigInteger.valueOf(1);
			BigInteger c = BigInteger.valueOf(1);
			for (int j = 2; j <= n; j++) {
				c = a.add(b);
				a = b;
				b = c;
			}
			f = b;
		}

		// Write number to a file if too big
		if (f.toString().length() >= 2000) {
			InputStream inputStream = new ByteArrayInputStream(f.toString().getBytes());
			event.getHook().sendMessage("Fibonacci(" + n + ") = ").addFiles(FileUpload.fromData(inputStream, "output.txt"))
					.queue();
		} else {
			event.getHook().sendMessage("Fibonacci(" + n + ") = " + f).queue();
		}
	}
}
