package aze.GLaDOS.Commands;

import java.io.File;
import java.util.List;

import aze.GLaDOS.Tycoon.Game;
import aze.GLaDOS.Tycoon.TycoonException;
import aze.GLaDOS.Utils.BuildEmbed;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.TextChannel;

public class Tycoon {
	public static void startGame(TextChannel channel, List<Attachment> attachments) {
		if(attachments.size() > 0) {
			for(Attachment attachment : attachments) {
				if(attachment.getFileName().equalsIgnoreCase("bot.out")){
					//Start the game
					attachment.downloadToFile(new File("./bot.out"));
					try {
						Game newGame = new Game("./map.json", "./bot.out");
						newGame.run();
						channel.sendMessage(BuildEmbed.scoreEmbed(newGame.getScore()).build()).queue();
					} catch(TycoonException e) {
						channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
					} catch(Exception e) {
						channel.sendMessage(BuildEmbed.errorEmbed(e.toString() + " [Make sure to have a 100 lines file !]").build()).queue();
					}
				} else {
					channel.sendMessage(BuildEmbed.errorEmbed("The file has to be named bot.out").build()).queue();
				}
			}
		} else {
			channel.sendMessage(BuildEmbed.errorEmbed("You need to provide a bot.out file").build()).queue();
		}
	}
}
