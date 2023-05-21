package commands;

import java.awt.Color;
import org.json.JSONObject;

import constants.Constants.Roles;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Quote {
	public static void epitaReminder(TextChannel channel) {
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("Reminder CPXA");
		info.setAuthor("moodle.cri.epita.fr", "https://moodle.cri.epita.fr/course/view.php?id=557");
		info.setDescription("Do not forget to do the MCQ on Moodle before tomorrow :D");
		info.setColor(Color.WHITE);
		info.setThumbnail("https://newsroom.ionis-group.com/wp-content/uploads/2018/12/epita-logo-baseline-blanc.png");
		info.setFooter("Request made at " + new Logger(false));
		channel.sendMessage("<@&" + Roles.ING1.id + ">").queue();
		channel.sendMessage(info.build()).queue();
	}
	
	public static void randomQuotes(TextChannel channel) {
		try {
			JSONObject jsonObject = JsonDownloader.getJson("https://api.quotable.io/random");
			String author = jsonObject.getString("author");
			String quote = jsonObject.getString("content");
			
			if(quote.length() > 256) {
				channel.sendMessage(quote + author).queue();
			}
			
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle(quote);
			info.setDescription(author);
			info.setColor(Color.CYAN);
			info.setFooter("Request made at " + new Logger(false));
			channel.sendMessage(info.build()).queue();
		} catch(Exception e) {
			channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			Quote.randomQuotes(channel);
		}
	}
}
