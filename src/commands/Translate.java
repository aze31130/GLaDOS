package commands;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import org.json.JSONObject;

import constants.Constants.Permissions;
import main.GLaDOS;
import utils.BuildEmbed;
import utils.Permission;

import java.util.Collections;
import java.util.List;

public class Translate extends Command {
	public Translate(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		GLaDOS g = GLaDOS.getInstance();

		int delay = 300;
		
		//Check cooldown
		long secondsSinceLastExecution = g.translationCooldown.until(LocalDateTime.now(), ChronoUnit.SECONDS);
		if (secondsSinceLastExecution < delay) {
			args.channel.sendMessage(BuildEmbed.errorEmbed("You need to wait " + (delay - secondsSinceLastExecution) + " seconds until using this command !").build()).queue();
			return;
		}

		//Get the last 15 messages
		List<Message> messages = args.channel.getHistory().retrievePast(15).complete();
		Collections.reverse(messages);

		for (Message m : messages) {
			String messageContent = m.getContentDisplay();
			if ((messageContent.length() == 0) || (m.getAuthor().isBot()))
				continue;

			JSONObject json = new JSONObject();
			json.put("q", messageContent);
			json.put("source", "fr");
			json.put("target", "en");
	
			HttpClient client = HttpClient.newHttpClient();
	
			HttpRequest request = HttpRequest.newBuilder(
				URI.create("https://translate.argosopentech.com/translate"))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(json.toString()))
				.build();
	
			//Build an embed and send it
			try {
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				JSONObject responseJson = new JSONObject(response.body());
				args.channel.sendMessage("`[Translated] <" + m.getMember().getEffectiveName() + ">`: " + responseJson.get("translatedText").toString()).queue();
			} catch(IOException|InterruptedException e) {
				args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		}
	}
}
