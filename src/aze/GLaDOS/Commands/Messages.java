package aze.GLaDOS.Commands;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import aze.GLaDOS.Constants;
import aze.GLaDOS.Constants.Roles;
import aze.GLaDOS.Utils.BuildEmbed;
import aze.GLaDOS.Utils.Counter;
import aze.GLaDOS.Utils.JsonIO;
import aze.GLaDOS.Utils.Pair;
import aze.GLaDOS.Utils.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Messages {
	
	/*
	 * IDEAS
	 * TOP MESSAGER
	 * TOP EMOJIER / REACTER
	 * TOP LINKER
	 * 
	 */
	
	public static void get1000(MessageChannel channel, Consumer<List<Message>> callback)
	{
	    List<Message> messages = new ArrayList<>(1000);
	    channel.getIterableHistory().cache(false).forEachAsync((message) ->
	    {
	        messages.add(message);
	        return messages.size() < 1000;
	    }).thenRun(() -> callback.accept(messages));
	}
	
	public static void sendPrivateMessage(GuildMessageReceivedEvent event){
		User author = event.getJDA().getUserById("");
		//User author2 = event.getJDA().getUserById("");
		//File file = new File("./");
		
		//author.openPrivateChannel().queue((channel) ->
		//channel.sendMessage("You want weapons? We�re in a library! Books! The best weapons in the world!").addFile(file).queue());
		
		author.openPrivateChannel().queue((channel) ->
		channel.sendMessage("Hello my dear <3").queue());
	}
	
	public static void statistics(TextChannel channel, Member member) {
		if(Permission.permissionLevel(member, 1)){
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			channel.sendMessage("Calculating stats of channel: " + channel.getAsMention()).queue();
			int i = 0;
			int TotalMessage = 0;
			List<Pair> pairList = new ArrayList<>();
			Counter count = new Counter();
			channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
				//Scan the list
				int j = 0;
				boolean found = false;
				while((j < pairList.size()) && (!found)) {
					if(pairList.get(j).author.equals(me.getAuthor())) {
						pairList.get(j).messageAmount++;
						count.add();
						found = true;
					}
					j++;
				}
				
				//If the user isn't found add him with one as amount of message
				if(!found) {
					pairList.add(new Pair(me.getAuthor(), 1));
				}
				
				if(count.value() % 250 == 0) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						channel.sendMessage(e.toString()).queue();
						e.printStackTrace();
					}
				}
		    	return pairList.size() < Constants.MAX_RETRIEVE_MESSAGES;
		    });
			
			i = 0;
			while(i < pairList.size()) {
				TotalMessage += pairList.get(i).messageAmount;
				i++;
			}
			
			pairList.sort(Comparator.comparingInt(Pair::getAmount).reversed());
			EmbedBuilder result = new EmbedBuilder();
			result.setColor(Color.YELLOW);
			result.setTitle("Message contribution for channel: " + channel.getName());
			result.setDescription("Total Message: " + TotalMessage);
			i = 0;
			String resultString = "";
			for (Pair pair : pairList){
				resultString += Integer.toString(pair.messageAmount) + " ("+ ((float)(pair.messageAmount * 100) / TotalMessage) +"%) " + pair.author.getName() + "\n";
				i++;
			}
			result.setFooter("Request made at " + time);
			
			try {
				FileWriter file = new FileWriter("./general-stats.txt");
				file.write(resultString);
				file.flush();
				file.close();
			} catch (IOException e) {
				channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				e.printStackTrace();
			}	
			channel.sendMessage(result.build()).queue();
			channel.sendMessage(resultString).queue();
		} else {
			channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
	
	public static void randomMessage(TextChannel channel, Member member){
		if(Permission.permissionLevel(member, 1)) {
			try {
				Random rng = new Random();
				JSONArray array = JsonIO.loadArray("general");
				JSONObject json = (JSONObject) array.get(rng.nextInt(array.size() - 1));
				channel.sendMessage(json.get("message").toString()).queue();
			} catch (Exception e) {
				e.printStackTrace();
				channel.sendMessage(e.toString()).queue();
			}
		} else {
			channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void jsonChannel(TextChannel channel, Member member){
		if(Permission.permissionLevel(member, 1)) {
			Counter test = new Counter();
			JSONArray messageArray = new JSONArray();
			
			channel.sendMessage("Creatin a json array of channel: " + channel.getAsMention()).queue();
			
			channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
				JSONObject json = new JSONObject();
		        json.clear();
		        if(me.getContentRaw().length() > 0) {
		        	json.put("message", me.getContentRaw());
		        	messageArray.add(json);
		        }
		        
				
				if(test.value() % 150 == 0) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				test.add();
				return true;
			});
			
			try {
				FileWriter jsonFile = new FileWriter(channel.getName() + ".json");
				jsonFile.write(messageArray.toJSONString());
				jsonFile.flush();
				jsonFile.close();
			} catch (IOException e) {
				e.printStackTrace();
				channel.sendMessage(e.toString()).queue();
			}
			channel.sendMessage("Successfully created json file of ./" + channel.getName().toLowerCase() + ".txt file").queue();
		} else {
			channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
	
	
	public static void downloadChannel(TextChannel channel, Member member){
		if(Permission.permissionLevel(member, 1)) {
			Counter test = new Counter();
			channel.sendMessage("Downloading channel: " + channel.getAsMention()).queue();
			//channel.getIterableHistory().cache(false).forEachAsync((me) -> { //Async for just backing up remaining for counting
			channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
				try {
					FileWriter fw = new FileWriter(channel.getName() + ".txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);
					out.print("[" + me.getTimeCreated() + "] [" + me.getAuthor().getAsTag() + "] " + me.getContentRaw() + "\n");
					out.close();
				} catch (IOException e) {
					channel.sendMessage(e.toString());
				}
				if(test.value() % 100 == 0) {
					System.out.println("Waiting:" + test.value());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				test.add();
		    	return true;
		    });
			channel.sendMessage("Successfully downloaded " + test.value() + " messages in ./" + channel.getName().toLowerCase() + ".txt file").queue();
		} else {
			channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
	
	
	public static void countMessages(GuildMessageReceivedEvent event, Member member){
		if(Permission.permissionLevel(member, 1)){
			event.getChannel().sendTyping().queue();
			
			
			EmbedBuilder info = new EmbedBuilder();
			info.setColor(Color.ORANGE);
			info.setTitle("Counting every messages in this channel... Note that because of the computer power of the raspberry pi, this may take a while !");
			event.getChannel().sendMessage(info.build()).queue();
			
			try {
				//CompletableFuture<List<Message>> messages = event.getChannel().getIterableHistory().takeAsync(5000);
				
				
				//List<Message> message = messages.get();
				
				
				List<Message> message = new ArrayList<>(Constants.MAX_RETRIEVE_MESSAGES);
				event.getChannel().getIterableHistory().cache(false).forEachAsync((me) ->
			    {
			    	message.add(me);
			    	System.out.println(message.size());
			        //return message.size() < Main.MessageRetrieveLimit;
			    	return true;
			    });
				
				Thread.sleep(10000);
				event.getChannel().sendMessage(message.size() + " messages").queue();
				
				
				
				//get1000(event.getChannel(), (messages) -> event.getChannel().purgeMessages(messages));
				
				int counter = 0;
				for (Message o : message){
					o.getId();
					System.out.println(o.getAuthor().getName() + " " + o.getContentRaw());
					counter++;
				}
				
				
				
				EmbedBuilder result = new EmbedBuilder();
				result.setColor(Color.GREEN);
				result.setTitle("Done ! On this channel, " + counter + " messages have been sent !");
				event.getChannel().sendMessage(result.build()).queue();
				
			} catch (Exception e) {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(Color.RED);
				error.setTitle("FATAL ERROR");
				error.setDescription(e.toString());
				event.getChannel().sendMessage(error.build()).queue();
			} catch(OutOfMemoryError e){
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(Color.RED);
				error.setTitle("FATAL ERROR");
				error.setDescription(e.toString());
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to have the Administrator role in order to execute that.");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void tableRase(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(event.getGuild().getMemberById(event.getAuthor().getId()).getRoles().contains(event.getGuild().getRoleById(Roles.ADMIN.id))){
			if(message.length >= 2){
				if(message[1].contains("confirm")){
					event.getChannel().sendMessage("Deleting every messages in this channel !").queue();
					try {
						//event.getChannel().getIterableHistory().cache(true).forEachAsync((me) ->
					    //{
					    	//me.delete();
					    	//return true;
					    //});
						get1000(event.getChannel(), (messages) -> event.getChannel().purgeMessages(messages));
					} catch (Exception e){
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("Error");
						error.setDescription(e.toString());
						event.getChannel().sendMessage(error.build()).queue();
					}
				} else {
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Error");
					error.setDescription("You need to confirm this final action !");
					event.getChannel().sendMessage(error.build()).queue();
				}
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error");
				error.setDescription("The command syntax is: " + Constants.commandPrefix + "table-rase <confirm>");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to have the Administrator role in order to execute that.");
			event.getChannel().sendMessage(error.build()).queue();
		} 
	}
	
	public static void clear(GuildMessageReceivedEvent event, Member member){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(message.length >= 2){
			if(Permission.permissionLevel(member, 1)){
				try {
					List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(message[1])).complete();
					event.getChannel().deleteMessages(messages).queue();
					event.getChannel().sendTyping().queue();
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0x22ff2a);
					error.setTitle("Successfully deleted " + message[1] + " messages.");
					event.getChannel().sendMessage(error.build()).queue();
				} catch(Exception exception) {
					event.getChannel().sendTyping().queue();
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Error in the command");
					error.setDescription(exception.toString());
					event.getChannel().sendMessage(error.build()).queue();
				}
			} else {
				event.getChannel().sendMessage(BuildEmbed.errorPermissionEmbed(2).build()).queue();
			} 
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "clear [1-100]");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
}
