package commands;

import java.io.IOException;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Permission;

public class CheGuevara extends Command {
	public CheGuevara() {
		super(
				"che-guevara",
				"Generate a random fact about Che-Guevara",
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
		JSONObject jsonObject = JsonDownloader.getJson("https://api.chucknorris.io/jokes/random");
		String meme = jsonObject.getString("value")
				.replace("Chuck Norris", "Che Guevara")
				.replace("Chuck", "Che")
				.replace("Norris", "Guevara");

		event.getHook().sendMessageEmbeds(BuildEmbed.cheGuevaraEmbed(meme).build()).queue();
	}
}
