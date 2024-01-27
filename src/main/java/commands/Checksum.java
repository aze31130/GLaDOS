package commands;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import accounts.Permission;

public class Checksum extends Command {
	public Checksum() {
		super("checksum",
				"Compute the checksum of string input.",
				Permission.NONE,
				Arrays.asList(
						new OptionData(OptionType.STRING, "algorithm",
								"The algorithm type")
										.addChoice("md2", "md2")
										.addChoice("md5", "md5")
										.addChoice("sha1", "sha1")
										.addChoice("sha224", "sha224")
										.addChoice("sha256", "sha256")
										.addChoice("sha384", "sha384")
										.addChoice("sha512", "sha512")
										.setRequired(true),
						new OptionData(OptionType.STRING, "content",
								"The content you want to hash").setRequired(true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();
		String algorithm = event.getOption("algorithm").getAsString();
		String content = event.getOption("content").getAsString();

		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(content.getBytes(StandardCharsets.UTF_8));

			StringBuilder result = new StringBuilder();
			for (byte b : digest.digest())
				result.append(String.format("%02x", b));

			source.sendMessageEmbeds(BuildEmbed.hashEmbed(result.toString(), algorithm).build())
					.queue();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
