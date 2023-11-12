package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import utils.BuildEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import accounts.Permissions;

public class Fibonacci extends Command {
	public Fibonacci(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
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

		source.sendTyping().queue();
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
			source.sendMessage("Fibonacci(" + n + ") = ")
					.addFiles(FileUpload.fromData(inputStream, "output.txt")).queue();
		} else {
			source.sendMessage("Fibonacci(" + n + ") = " + f).queue();
		}
	}
}
