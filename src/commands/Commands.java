package commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import main.GLaDOS;
import constants.Constants.Channels;
import utils.BuildEmbed;
import utils.Resolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Commands {
	public static void Main(GuildMessageReceivedEvent event, GLaDOS glados) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		switch(message[0].substring(1)){
			case "tycoon":
				Tycoon.startGame(event.getJDA().getTextChannelById(Channels.BOT_SNAPSHOT.id), event.getMessage().getAttachments());
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
			case "json-channel": case "json-chan":
				//Messages.jsonChannel(event.getChannel(), event.getMember());
				break;
			case "connect":
				Music.connect(event);
				break;
			case "disconnect":
				Music.disconnect(event);
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
