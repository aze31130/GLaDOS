package commands;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import org.json.JSONException;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Permission;

public class RandomDog extends Command {
	public RandomDog() {
		super(
				"random-dog",
				"Display a dog picture",
				Permission.NONE,
				Tag.FUN,
				Arrays.asList(Type.SLASH),
				Arrays.asList());
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) throws JSONException, IOException {
		EmbedBuilder info = new EmbedBuilder()
				.setTitle("Random Dog Picture")
				.setImage(JsonDownloader.getJson("https://dog.ceo/api/breeds/image/random").getString("message"))
				.setColor(Color.WHITE)
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(info.build()).queue();
	}
}
