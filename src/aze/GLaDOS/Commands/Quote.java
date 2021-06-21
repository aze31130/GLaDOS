package aze.GLaDOS.commands;

import java.awt.Color;
import org.json.JSONObject;
import aze.GLaDOS.utils.BuildEmbed;
import aze.GLaDOS.utils.JsonDownloader;
import aze.GLaDOS.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Quote {
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
