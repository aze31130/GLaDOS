package commands;

import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Permission;

public class Idea extends Command {
	public Idea() {
		super(
				"what-should-i-do",
				"Use it when you do not know what to do",
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
		JSONObject jsonObject = JsonDownloader.getJson("https://www.boredapi.com/api/activity");
		String activity = jsonObject.getString("activity");
		String type = jsonObject.getString("type");
		int participants = jsonObject.getInt("participants");
		int price = jsonObject.getInt("price");

		EmbedBuilder info = new EmbedBuilder()
				.setAuthor("What Should I Do ?")
				.setTitle(activity)
				.addField("Price: ", price + "", false)
				.addField("Type: ", type, false)
				.addField("Participants: ", participants + "", false)
				.setColor(Color.CYAN)
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(info.build()).queue();
	}
}
