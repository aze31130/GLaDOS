package commands;

import java.awt.Color;
import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

public class Idea extends Command {
	public Idea(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
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
			args.channel.sendMessage(info.build()).queue();
		} catch(Exception e) {
			args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
