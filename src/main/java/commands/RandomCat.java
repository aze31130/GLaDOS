package commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permission;

public class RandomCat extends Command {
	public RandomCat() {
		super(
				"random-cat",
				"Displays a cat picture",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		try {
			String apiUrl = "https://api.thecatapi.com/v1/images/search";
			JSONArray jsonArray = new JSONArray(new JSONTokener(
					new BufferedReader(new InputStreamReader(new URL(apiUrl).openConnection().getInputStream()))));
			JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
			EmbedBuilder info = new EmbedBuilder().setTitle("Random Cat Picture")
					.setImage(jsonObject.getString("url")).setColor(Color.WHITE)
					.setTimestamp(Instant.now());
			event.getHook().sendMessageEmbeds(info.build()).queue();
		} catch (Exception e) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
