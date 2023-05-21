package commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.BuildEmbed;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

public class RandomCat extends Command {
	public RandomCat(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		try {
			String apiUrl = "https://api.thecatapi.com/v1/images/search";
			JSONArray jsonArray = new JSONArray(new JSONTokener(new BufferedReader(new InputStreamReader(new URL(apiUrl).openConnection().getInputStream()))));
			JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
			EmbedBuilder info = new EmbedBuilder()
				.setTitle("Random Cat Picture")
				.setImage(jsonObject.getString("url"))
				.setColor(Color.WHITE)
				.setFooter("Request made at " + new Logger(false));
			args.channel.sendMessage(info.build()).queue();
		} catch(Exception e) {
			args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
