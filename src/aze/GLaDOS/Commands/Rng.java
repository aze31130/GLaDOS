package aze.GLaDOS.Commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import aze.GLaDOS.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Rng {
	public static void rng(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		//check if the nummber is <=2147483647
		if(message.length >= 2){
			Random rng = new Random();
			int randomNumber = rng.nextInt(Integer.parseInt(message[1])) + 1;
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("The rng is: " + randomNumber).queue();
		} else {
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Error, the command syntax is ?rng <positive number>").queue();
		}
	}
	
	public static void RamdomMessage(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		
		if(message[0].equalsIgnoreCase(Constants.commandPrefix + "random-message")){
			
			Random rng = new Random();
			int randomNumber = rng.nextInt(1000000) + 1;
			
			try (Stream<String> lines = Files.lines(Paths.get("./src/aze/GLaDOS/Commands/rockyou.txt"), StandardCharsets.ISO_8859_1)) {
				String line = lines.skip(randomNumber).findFirst().get();
				event.getChannel().sendMessage(line).queue();
			}catch(IOException e){
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage(e.toString()).queue();
				event.getChannel().sendMessage("path: " + Paths.get("./")).queue();
			}
		}
	}
}
