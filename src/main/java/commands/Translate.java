package commands;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import org.json.JSONObject;
import java.util.Arrays;
import accounts.Permission;

public class Translate extends Command {
	public Translate() {
		super(
				"translate",
				"Translates the latests messages in a text channel",
				Permission.NONE,
				Tag.SYSTEM,
				Arrays.asList(Type.MESSAGE),
				Arrays.asList(
						new OptionData(OptionType.STRING, "amount", "Amount of message you want to translate.")));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) throws IOException, InterruptedException {
		String messageContent = event.getTarget().getContentDisplay();

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
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject responseJson = new JSONObject(response.body());
		event.getHook().sendMessage(
				"`[Translated] <" + event.getTarget().getMember().getEffectiveName() + ">`: "
						+ responseJson.get("translatedText").toString())
				.queue();
	}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {}
}
