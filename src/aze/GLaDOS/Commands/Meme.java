package aze.GLaDOS.Commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.Utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Meme {
	public static void meme(GuildMessageReceivedEvent event){
		try {
			String apiUrl = "https://meme-api.herokuapp.com/gimme/";
			if (event.getChannel().isNSFW() && event.getChannel().getId().equals(Channels.NSFW.id)){
				apiUrl = apiUrl + "nsfw";
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
				URL memeURL = new URL(apiUrl);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
				JSONParser parser = new JSONParser();
				String lines;
				String url = "";
				String title = "";
				String postLink = "";
				String subReddit = "";
				long upVotes = 0;
				
				while((lines = bufferedReader.readLine()) != null){
					JSONArray list = new JSONArray();
					list.add(parser.parse(lines));
					
					for (Object o : list){
						JSONObject jsonObject = (JSONObject) o;
						title = (String) jsonObject.get("title");
						url = (String) jsonObject.get("url");
						postLink = (String) jsonObject.get("postLink");
						subReddit = (String) jsonObject.get("subreddit");
						upVotes = (long) jsonObject.get("ups");
					}
				}
				
				String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
				EmbedBuilder info = new EmbedBuilder();
				info.setAuthor("SubReddit: " + subReddit + "\n" + "UpVotes: " + upVotes);
				info.setTitle(title);
				info.setDescription(postLink);
				info.setImage(url);
				info.setColor(Color.MAGENTA);
				info.setFooter("Request made at " + time);
				event.getChannel().sendMessage(info.build()).queue();
				Thread.sleep(5000);
				amount--;
			}
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
/*
try (InputStream is = new URL(url).openStream();
BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
	while ((line = br.readLine()) != null) {
		result.append(line);
	}
}
*/
	public static void randomDog(GuildMessageReceivedEvent event){
		try {
			String apiUrl = "https://dog.ceo/api/breeds/image/random";
			
			URL memeURL = new URL(apiUrl);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			JSONParser parser = new JSONParser();
			String lines;
			String picture = "";
			
			while((lines = bufferedReader.readLine()) != null){
				JSONArray list = new JSONArray();
				list.add(parser.parse(lines));
				
				for (Object o : list){
					JSONObject jsonObject = (JSONObject) o;
					picture = (String) jsonObject.get("message");
				}
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Random Dog Picture");
			info.setImage(picture);
			info.setColor(Color.WHITE);
			info.setFooter("Request made at " + formatter.format(date));
			event.getChannel().sendMessage(info.build()).queue();
			
		} catch(Exception e) {
			event.getChannel().sendMessage(e.toString()).queue();
		}
	}
	
	public static void whatShouldIDo(GuildMessageReceivedEvent event) {
		try {
			String apiUrl = "https://www.boredapi.com/api/activity";
			
			URL memeURL = new URL(apiUrl);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			JSONParser parser = new JSONParser();
			String lines;
			String activity = "";
			String type = "";
			Object participants = 0;
			Object price = 0;
			
			while ((lines = bufferedReader.readLine()) != null) {
				JSONArray list = new JSONArray();
				list.add(parser.parse(lines));
				
				for (Object o : list){
					JSONObject jsonObject = (JSONObject) o;
					activity = (String) jsonObject.get("activity");
					type = (String) jsonObject.get("type");
					participants = jsonObject.get("participants");
					price = jsonObject.get("price");
				}
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();
			EmbedBuilder info = new EmbedBuilder();
			info.setAuthor("What Should I Do ?");
			info.setTitle(activity);
			info.addField("Price: ", price + "", false);
			info.addField("Type: ", type, false);
			info.addField("Participants: ", participants + "", false);
			info.setColor(Color.CYAN);
			info.setFooter("Request made at " + formatter.format(date));
			event.getChannel().sendMessage(info.build()).queue();
			
		} catch(Exception e) {
			event.getChannel().sendMessage(e.toString()).queue();
		}
	}
	
	public static void randomQuotes(TextChannel channel) {
		try {
			String apiUrl = "https://api.quotable.io/random";
			URL memeURL = new URL(apiUrl);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			JSONParser parser = new JSONParser();
			String lines;
			String author = "";
			String quote = "";
			
			while ((lines = bufferedReader.readLine()) != null) {
				JSONArray list = new JSONArray();
				list.add(parser.parse(lines));
				
				for (Object o : list) {
					JSONObject jsonObject = (JSONObject) o;
					quote = (String) jsonObject.get("content");
					author = (String) jsonObject.get("author");
				}
			}
			
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
	
	public static void randomCat(GuildMessageReceivedEvent event){
		try {
			String apiUrl = "https://api.thecatapi.com/v1/images/search?format=json";
			URL memeURL = new URL(apiUrl);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			JSONParser parser = new JSONParser();
			String lines;
			String url = "";
			StringBuffer response = new StringBuffer();
			
			while ((lines = bufferedReader.readLine()) != null) {
				response.append(lines);
			}
			String test = response.substring(1, response.length() - 1);
			JSONArray list = new JSONArray();
			list.add(parser.parse(test));
			
			for (Object o : list){
				JSONObject jsonObject = (JSONObject) o;
				url = (String) jsonObject.get("url");
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Random Cat Picture");
			info.setImage(url);
			info.setColor(Color.WHITE);
			info.setFooter("Request made at " + formatter.format(date));
			event.getChannel().sendMessage(info.build()).queue();
		} catch(Exception e) {
			event.getChannel().sendMessage(e.toString()).queue();
		}
	}
	
	public static void cheGuevara(GuildMessageReceivedEvent event){
		try {
			URL obj = new URL("https://api.chucknorris.io/jokes/random");
			String meme = "";
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			JSONParser parser = new JSONParser();
			
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String lines;
				StringBuffer response = new StringBuffer();
				
				while ((lines = bufferedReader.readLine()) != null) {
					response.append(lines);
					JSONArray list = new JSONArray();
					list.add(parser.parse(lines));
					for (Object o : list){
						JSONObject jsonObject = (JSONObject) o;
						meme = (String) jsonObject.get("value");
					}
				}
				bufferedReader.close();
				
				meme = meme.replace("Chuck Norris", "Che Guevara");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			    Date date = new Date();
				EmbedBuilder info = new EmbedBuilder();
				info.setTitle("Che Guevara");
				info.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png");
				info.setDescription(meme);
				info.setColor(Color.BLUE);
				info.setFooter("Request made at " + formatter.format(date));
				event.getChannel().sendMessage(info.build()).queue();
			} else {
				event.getChannel().sendMessage("HTTP GET request did not worked ! (API may be down)").queue();
			}
		}catch(Exception e) {
			event.getChannel().sendMessage(e.toString()).queue();
			System.out.print(e);
		}
	}
}
