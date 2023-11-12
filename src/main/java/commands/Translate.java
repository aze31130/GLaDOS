package commands;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONException;
import org.json.JSONObject;

import glados.GLaDOS;
import utils.BuildEmbed;

import java.util.Collections;
import java.util.List;

import accounts.Permissions;

public class Translate extends Command {
	public Translate(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();

		int delay = 300;

		// Check cooldown
		long secondsSinceLastExecution =
				g.translationCooldown.until(LocalDateTime.now(), ChronoUnit.SECONDS);
		if (secondsSinceLastExecution < delay) {
			event.getChannel()
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("You need to wait " + (delay - secondsSinceLastExecution)
									+ " seconds until using this command !")
							.build())
					.queue();
			return;
		}
		g.translationCooldown = LocalDateTime.now();

		List<Message> messages = event.getChannel().getHistory().retrievePast(15).complete();
		Collections.reverse(messages);

		for (Message m : messages) {
			String messageContent = m.getContentDisplay();
			if ((messageContent.length() == 0) || (m.getAuthor().isBot()))
				continue;

			JSONObject json = new JSONObject();
			json.put("q", messageContent);
			json.put("source", "auto");
			json.put("target", "en");

			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = HttpRequest
					.newBuilder(URI.create("https://translate.argosopentech.com/translate"))
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.ofString(json.toString())).build();

			// Build an embed and send it
			try {
				HttpResponse<String> response =
						client.send(request, HttpResponse.BodyHandlers.ofString());
				JSONObject responseJson = new JSONObject(response.body());
				event.getChannel().sendMessage("`[Translated] <" + m.getMember().getEffectiveName()
						+ ">`: " + responseJson.get("translatedText").toString()).queue();
			} catch (IOException | InterruptedException | JSONException e) {
				event.getChannel().sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build())
						.queue();
			}
		}
	}
}
