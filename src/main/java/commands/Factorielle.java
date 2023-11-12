package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import accounts.Permissions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import utils.BuildEmbed;

public class Factorielle extends Command {
	public Factorielle(String name, String description, Permissions permissionLevel,
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
