package commands;

import java.math.BigInteger;
import glados.GLaDOS;
import utils.BuildEmbed;

public class Factorielle extends Command {
	public Factorielle(String name, String description, int permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		GLaDOS glados = GLaDOS.getInstance();
		if (args.arguments.length > 0) {
			int number = 0;
			try {
				number = Integer.parseInt(args.arguments[0]);
			} catch (Exception e) {
				args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}

			if ((number >= 0) && (number <= 1000)) {
				BigInteger f = new BigInteger("1");
				for (int i = 2; i <= number; i++) {
					f = f.multiply(BigInteger.valueOf(i));
				}
				args.channel.sendTyping().queue();
				try {
					args.channel.sendMessage("Factorial(" + number + ") = " + f).queue();
				} catch (Exception e) {
					// FileWriter fw = new FileWriter("output.txt", true);
					// BufferedWriter bw = new BufferedWriter(fw);
					// PrintWriter out = new PrintWriter(bw);
					// out.print(f);
					// out.close();
					// event.getChannel().sendFile(new File("output.txt")).queue();
					args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				}
			} else {
				args.channel
						.sendMessageEmbeds(BuildEmbed.errorEmbed("Sorry, negative numbers cannot be handled.").build())
						.queue();
			}
		} else {
			args.channel
					.sendMessageEmbeds(
							BuildEmbed.errorEmbed("Usage: " + glados.prefix + "facto <positive integer>").build())
					.queue();
		}
	}
}
