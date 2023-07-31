package commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

import glados.GLaDOS;
import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;

public class RandomMeme extends Command {
	public RandomMeme(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		GLaDOS g = GLaDOS.getInstance();
		try {
			String apiUrl = "https://meme-api.herokuapp.com/gimme/";
			if (args.channel.isNSFW() && args.channel.getId().equals(g.channelNsfw)){
				apiUrl += "nsfw";
			}
			//http://www.reddit.com/r/random.json
			long amount = 1;
			if(args.arguments.length > 0) {
				amount = Long.parseLong(args.arguments[0]);
			}
			
			if(amount > 10) {
				amount = 10;
			}
			
			if(amount < 0) {
				amount = 0;
			}
			
			while(amount > 0) {
				JSONObject jsonObject = JsonDownloader.getJson(apiUrl);
				String url = jsonObject.getString("url");
				String title = jsonObject.getString("title");
				String postLink = jsonObject.getString("postLink");
				String subReddit = jsonObject.getString("subreddit");
				long upVotes = jsonObject.getLong("ups");
				
				String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
				EmbedBuilder info = new EmbedBuilder();
				info.setAuthor("SubReddit: " + subReddit + "\n" + "UpVotes: " + upVotes);
				info.setTitle(title);
				info.setDescription(postLink);
				info.setImage(url);
				info.setColor(Color.MAGENTA);
				info.setFooter("Request made at " + time);
				args.channel.sendMessage(info.build()).queue();
				Thread.sleep(8000);
				amount--;
			}
		} catch(Exception e) {
			args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
