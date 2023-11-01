package commands;

import java.awt.Color;
import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

import accounts.Permissions;

public class Idea extends Command {
	public Idea(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		try {
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
					.setFooter("Request made at " + new Logger(false));
			args.channel.sendMessageEmbeds(info.build()).queue();
		} catch (Exception e) {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
