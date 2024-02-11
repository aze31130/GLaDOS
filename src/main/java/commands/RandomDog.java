package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;

import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permission;

public class RandomDog extends Command {
	public RandomDog() {
		super(
				"random-dog",
				"Display a dog picture",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		try {
			EmbedBuilder info = new EmbedBuilder().setTitle("Random Dog Picture")
					.setImage(JsonDownloader.getJson("https://dog.ceo/api/breeds/image/random").getString("message"))
					.setColor(Color.WHITE).setTimestamp(Instant.now());
			event.getHook().sendMessageEmbeds(info.build()).queue();
		} catch (Exception e) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
