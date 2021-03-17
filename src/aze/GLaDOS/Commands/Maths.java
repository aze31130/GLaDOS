package aze.GLaDOS.Commands;

import java.math.BigInteger;
import aze.GLaDOS.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Maths {
	public static void Fibonacci(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		
		if(message.length >= 2){
			int number = 0;
			try{
				number = Integer.parseInt(message[1]);
			} catch (Exception e){
				event.getChannel().sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the syntax !");
				error.setDescription(e.toString());
				event.getChannel().sendMessage(error.build()).queue();
			}
			
			if((number >= 0) && (number < 10000)){
				BigInteger f = new BigInteger("1");
				if(number < 2) {
					f = BigInteger.ONE;
				} else {
					BigInteger a = BigInteger.valueOf(0); 
					BigInteger b = BigInteger.valueOf(1); 
					BigInteger c = BigInteger.valueOf(1); 
					for (int j=2 ; j<=number ; j++) { 
						c =  a.add(b);
						a = b;
						b = c;
					}
					f = b;
				}
				
				event.getChannel().sendTyping().queue();
				//event.getChannel().sendMessage("Fibonacci("+number+") = " + Math.round(Math.pow(1.61803398875, number)/Math.sqrt(5))).queue();
				try {
					event.getChannel().sendMessage("Fibonacci("+number+") = " + f).queue();
				} catch(Exception e) {
					event.getChannel().sendTyping().queue();
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Error");
					error.setDescription("Cannot send more than 2000 characters !");
					error.addField("Note: ", " In the next update, theses numbers will be send in a textfile", true);
					event.getChannel().sendMessage(error.build()).queue();
				}
			} else {
				event.getChannel().sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the command");
				error.setDescription("Sorry, the optimised Fibonacci formula cannot handle negative numbers.");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			event.getChannel().sendTyping().queue();
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "Fibonacci <positive integer>");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void Factorielle(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(message.length >= 2){
			int number = 0;
			try {
				number = Integer.parseInt(message[1]);
			} catch (Exception e){
				event.getChannel().sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the syntax !");
				error.setDescription(e.toString());
				event.getChannel().sendMessage(error.build()).queue();
			}
			
			if((number >= 0) && (number <= 1000)){
				BigInteger f = new BigInteger("1");
				for (int i = 2 ; i <= number ; i++) {
					f = f.multiply(BigInteger.valueOf(i));
				}
				event.getChannel().sendTyping().queue();
				try {
					event.getChannel().sendMessage("Factorial("+number+") = " + f).queue();
				} catch(Exception e) {
					//FileWriter fw = new FileWriter("output.txt", true);
					//BufferedWriter bw = new BufferedWriter(fw);
					//PrintWriter out = new PrintWriter(bw);
					//out.print(f);
					//out.close();
					//event.getChannel().sendFile(new File("output.txt")).queue();
					event.getChannel().sendTyping().queue();
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Error");
					error.setDescription("Cannot send more than 2000 characters !");
					error.addField("Note: ", " In the next update, theses numbers will be send in a textfile", true);
					event.getChannel().sendMessage(error.build()).queue();
				}
			} else {
				event.getChannel().sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the command");
				error.setDescription("Sorry, negative numbers cannot be handled.");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			event.getChannel().sendTyping().queue();
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "facto <positive integer>");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
}
