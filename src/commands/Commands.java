package commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import glados.GLaDOS;
import utils.BuildEmbed;
import utils.Resolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import tycoon.Game;

public class Commands {
	public static void Main(GuildMessageReceivedEvent event, GLaDOS glados) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		GLaDOS g = GLaDOS.getInstance();
		switch(message[0].substring(1)){
			case "tycoon":
				Tycoon.startGame(event.getJDA().getTextChannelById(g.channelBotSnapshot), event.getMessage().getAttachments());
				break;
			case "resolver":
				Resolver resolver = new Resolver();
				try {
					EmbedBuilder test = resolver.runTest(event.getMessage().getAttachments());
					event.getChannel().sendMessage(test.build()).queue();
				} catch (NoSuchAlgorithmException | IOException | InterruptedException e) {
					e.printStackTrace();
					event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				}
				break;
			case "picture-inverse": case "pi":
				Picture.inverse(event);
				break;
			case "save-channel":
				Picture.Save(event.getChannel());
				break;
			default:
				if(message[0].length() > 1){
					event.getChannel().sendMessage(BuildEmbed.errorEmbed("Unknown command !").build()).queue();
				}
				break;
		}
	}
}
