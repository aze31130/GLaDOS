package aze.GLaDOS.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import aze.GLaDOS.GLaDOS;
import aze.GLaDOS.Main;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.utils.BuildEmbed;
import aze.GLaDOS.utils.Resolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Commands {
	public static void Main(GuildMessageReceivedEvent event, GLaDOS glados) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		switch(message[0].substring(1)){
			//case "online-list":
				//Generate online list
				//break;
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
			case "hardspam": case "hs":
				//Call.hardSpam(event, event.getMember());
				break;
			case "move":
				Move.move(event.getGuild(),event.getJDA().getVoiceChannelsByName(message[1], true).get(0), event.getJDA().getVoiceChannelsByName(message[2], true).get(0));
				break;
			case "che-guevara": case "cg":
				Meme.cheGuevara(event);
				break;
			case "state": case "st":
				Status.state(event, glados);
				break;
			case "activity": case "at":
				Status.activity(event, glados);
				break;
			case "rng":
				Rng.rng(event);
				break;
			case "clear":
				Messages.clear(event, event.getMember(), glados);
				break;
			case "what-should-i-do": case "wsid":
				Meme.whatShouldIDo(event);
				break;
			case "random-dog": case "rd":
				Meme.randomDog(event);
				break;
			case "random-cat": case "rc":
				Meme.randomCat(event);
				break;
			case "statistics":
				Messages.statistics(event.getChannel(), event.getMember());
				break;
			case "json-channel": case "json-chan":
				//Messages.jsonChannel(event.getChannel(), event.getMember());
				break;
			case "random-meme": case "rm":
				Meme.meme(event);
				break;
			case "account": case "profile":
				Info.account(event);
				break;
			case "server-info":
				Info.serverInfo(event, glados);
				break;
			case "hash":
				//Hash.hash2(event);
				break;
			case "fibonacci": case "fibo":
				Maths.Fibonacci(event, glados);
				break;
			case "factorielle": case "facto":
				Maths.Factorielle(event, glados);
				break;
			case "mute":
				break;
			case "help":
				Info.help(event, glados);
				break;
			case "connect":
				Music.connect(event);
				break;
			case "disconnect":
				Music.disconnect(event);
				break;
			case "version": case "v":
				Info.version(event);
				break;
			case "ping":
				Info.ping(event);
				break;
			case "picture-inverse": case "pi":
				Picture.inverse(event);
				break;
			case "save-channel":
				Picture.Save(event.getChannel());
				break;
			case "test":
				break;
			case "parrot":
				Call.parrot(event, event.getMember());
				break;
			case "table-rase":
				Messages.tableRase(event, glados);
				break;
			case "update-settings":
				Status.updateSettings(event, glados);
				break;
			case "shutdown":
				Main.shutdown(event, event.getMember());
				break;
			default:
				if(message[0].length() > 1){
					event.getChannel().sendMessage(BuildEmbed.errorEmbed("Unknown command !").build()).queue();
				}
				break;
		}
	}
}
