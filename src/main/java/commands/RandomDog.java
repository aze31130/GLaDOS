package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;

import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permissions;

public class RandomDog extends Command {
	public RandomDog() {
		super("random-dog", "Display a dog picture", Permissions.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();

		try {
			EmbedBuilder info = new EmbedBuilder().setTitle("Random Dog Picture")
					.setImage(JsonDownloader.getJson("https://dog.ceo/api/breeds/image/random")
							.getString("message"))
					.setColor(Color.WHITE).setTimestamp(Instant.now());
			source.sendMessageEmbeds(info.build()).queue();
		} catch (Exception e) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
