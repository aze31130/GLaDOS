package aze.GLaDOS.Commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.Utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Meme {
	private static JSONObject getJson(String url) {
		try {
			return new JSONObject(new JSONTokener(new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()))));
		} catch(Exception e) {
			
		}
		return new JSONObject();
	}
	
	public static void meme(GuildMessageReceivedEvent event){
		try {
			String apiUrl = "https://meme-api.herokuapp.com/gimme/";
			if (event.getChannel().isNSFW() && event.getChannel().getId().equals(Channels.NSFW.id)){
				apiUrl.concat("nsfw");
			}
			//http://www.reddit.com/r/random.json
			String[] message = event.getMessage().getContentRaw().split("\\s+");
			int amount = 1;
			if(message.length >= 2){
				amount = Integer.parseInt(message[1]);
				if(amount > 10) {
					amount = 10;
				}
				if(amount < 0) {
					amount = 0;
				}
			}
			while(amount > 0) {
				JSONObject jsonObject = getJson(apiUrl);
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
				event.getChannel().sendMessage(info.build()).queue();
				Thread.sleep(8000);
				amount--;
			}
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
	public static void randomDog(GuildMessageReceivedEvent event){
		try {
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Random Dog Picture");
			info.setImage(getJson("https://dog.ceo/api/breeds/image/random").getString("message"));
			info.setColor(Color.WHITE);
			info.setFooter("Request made at " + time);
			event.getChannel().sendMessage(info.build()).queue();
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
	public static void whatShouldIDo(GuildMessageReceivedEvent event) {
		try {
			JSONObject jsonObject = getJson("https://www.boredapi.com/api/activity");
			String activity = jsonObject.getString("activity");
			String type = jsonObject.getString("type");
			int participants = jsonObject.getInt("participants");
			int price = jsonObject.getInt("price");
			
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			EmbedBuilder info = new EmbedBuilder();
			info.setAuthor("What Should I Do ?");
			info.setTitle(activity);
			info.addField("Price: ", price + "", false);
			info.addField("Type: ", type, false);
			info.addField("Participants: ", participants + "", false);
			info.setColor(Color.CYAN);
			info.setFooter("Request made at " + time);
			event.getChannel().sendMessage(info.build()).queue();
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
	public static void randomQuotes(TextChannel channel) {
		try {
			JSONObject jsonObject = getJson("https://api.quotable.io/random");
			String author = jsonObject.getString("author");
			String quote = jsonObject.getString("content");
			
			if(quote.length() > 256) {
				channel.sendMessage(quote + author).queue();
			}
			
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle(quote);
			info.setDescription(author);
			//info.setDescription("We are now at the day #" +  Converter.AmountOfLockdownDays() + " of this lockdown.");
			info.setColor(Color.CYAN);
			info.setFooter("Request made at " + time);
			channel.sendMessage(info.build()).queue();
		} catch(Exception e) {
			channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			Meme.randomQuotes(channel);
		}
	}
	
	public static void aprilFools(TextChannel channel) {
		String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("(Inserting April Joke...) Congratulations, the test is now over. Happy April Fool’s Day!");
		info.setDescription("GLaDOS");
		info.setColor(Color.CYAN);
		info.setFooter("Request made at " + time);
		channel.sendMessage(info.build()).queue();
	}
	
	public static void randomCat(GuildMessageReceivedEvent event){
		try {
			String apiUrl = "https://api.thecatapi.com/v1/images/search";
			JSONArray jsonArray = new JSONArray(new JSONTokener(new BufferedReader(new InputStreamReader(new URL(apiUrl).openConnection().getInputStream()))));
			JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Random Cat Picture");
			info.setImage(jsonObject.getString("url"));
			info.setColor(Color.WHITE);
			info.setFooter("Request made at " + formatter.format(date));
			event.getChannel().sendMessage(info.build()).queue();
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
	public static void cheGuevara(GuildMessageReceivedEvent event){
		try {
			JSONObject jsonObject = getJson("https://api.chucknorris.io/jokes/random");
			String meme = jsonObject.getString("value");
			meme = meme.replace("Chuck Norris", "Che Guevara");
			meme = meme.replace("Chuck", "Che");
			meme = meme.replace("Norris", "Guevara");
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Che Guevara");
			info.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png");
			info.setDescription(meme);
			info.setColor(Color.BLUE);
			info.setFooter("Request made at " + time);
			event.getChannel().sendMessage(info.build()).queue();
		}catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			System.out.print(e);
		}
	}
}
