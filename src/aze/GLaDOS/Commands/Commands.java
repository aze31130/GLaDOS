package aze.GLaDOS.Commands;

import java.awt.Color;
import aze.GLaDOS.Constants;
import aze.GLaDOS.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Commands {
	public static void Main(GuildMessageReceivedEvent event) {
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(message[0].startsWith(Constants.commandPrefix)){
			switch(message[0].substring(1)){
				//case "channel-histogram": case "chanHisto":
					//Messages.Histogram();
					//break;
				//case "online-list":
					//Generate online list
					//break;
				case "hardspam": case "hs":
					Call.hardSpam(event, event.getMember());
					break;
				case "count": case "cm":
					Messages.countMessages(event, event.getMember());
					break;
				case "che-guevara": case "cg":
					Meme.cheGuevara(event);
					break;
				case "state": case "st":
					Status.state(event);
					break;
				case "activity": case "at":
					Status.activity(event);
					break;
				case "rng":
					Rng.rng(event);
					break;
				case "clear":
					Messages.clear(event, event.getMember());
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
				case "statistics": case "stats-chan":
					Messages.statistics(event.getChannel(), event.getMember());
					break;
				case "download-channel": case "dl-chan":
					Messages.downloadChannel(event.getChannel(), event.getMember());
					break;
				case "json-channel": case "json-chan":
					Messages.jsonChannel(event.getChannel(), event.getMember());
					break;
				case "random-meme": case "rm":
					Meme.meme(event);
					break;
				case "account-info":
					Info.accountInfo(event);
					break;
				case "server-info":
					Info.serverInfo(event);
					break;
				case "hash":
					Hash.hash2(event);
					break;
				case "fibonacci": case "fibo":
					Maths.Fibonacci(event);
					break;
				case "factorielle": case "facto":
					Maths.Factorielle(event);
					break;
				case "mute":
					break;
				case "help":
					Info.help(event);
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
					//Call.TestFunction(event.getJDA());
					//Messages.randomMessage(event.getChannel(), event.getMember());
					break;
				case "parrot":
					Call.parrot(event, event.getMember());
					break;
				case "table-rase":
					Messages.tableRase(event);
					break;
				case "update-settings":
					Status.updateSettings(event);
					break;
				case "shutdown":
					Main.shutdown(event, event.getMember());
					break;
				default:
					if(message[0].length() > 1){
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(Color.RED);
						error.setTitle("Unknown command !");
						error.setDescription("Type " + Constants.commandPrefix + "help for help.");
						event.getChannel().sendMessage(error.build()).queue();
					}
					break;
			}
		}
	}
}
