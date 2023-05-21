package commands;

import java.math.BigInteger;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;

public class Fibonacci extends Command {
	public Fibonacci(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(args.arguments.length > 0){
			int number = 0;
			try{
				number = Integer.parseInt(args.arguments[0]);
			} catch (Exception e){
				EmbedBuilder error = new EmbedBuilder()
						.setColor(0xff3923)
						.setTitle("Error in the syntax !")
						.setDescription(e.toString());
				args.channel.sendMessage(error.build()).queue();
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
				
				try {
					args.channel.sendMessage("Fibonacci("+number+") = " + f).queue();
				} catch(Exception e) {
					EmbedBuilder error = new EmbedBuilder()
							.setColor(0xff3923)
							.setTitle("Error")
							.setDescription("Cannot send more than 2000 characters !")
							.addField("Note: ", " In the next update, theses numbers will be send in a textfile", true);
					args.channel.sendMessage(error.build()).queue();
				}
			} else {
				args.channel.sendMessage(BuildEmbed.errorEmbed("The given number " + number + " is out of range.").build()).queue();
			}
		} else {
			args.channel.sendMessage(BuildEmbed.errorEmbed("Usage: Fibonacci [0;10000]").build()).queue();
		}
	}
}
