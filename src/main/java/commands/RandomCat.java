package commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Permission;

public class RandomCat extends Command {
	public RandomCat() {
		super(
				"random-cat",
				"Displays a cat picture",
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
	public void executeSlash(SlashCommandInteractionEvent event) throws JSONException, MalformedURLException, IOException {
		String apiUrl = "https://api.thecatapi.com/v1/images/search";
		JSONArray jsonArray = new JSONArray(new JSONTokener(
				new BufferedReader(new InputStreamReader(new URL(apiUrl).openConnection().getInputStream()))));
		JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
		EmbedBuilder info = new EmbedBuilder()
				.setTitle("Random Cat Picture")
				.setImage(jsonObject.getString("url"))
				.setColor(Color.WHITE)
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(info.build()).queue();
	}
}
