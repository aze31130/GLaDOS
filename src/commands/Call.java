package commands;

import java.awt.Color;
import java.util.Random;
import constants.Constants.Channels;
import constants.Constants.Roles;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import utils.Permission;
import constants.Constants.Permissions;
import utils.BuildEmbed;
import utils.JsonDownloader;
import org.json.JSONObject;

public class Call extends Command {
	public Call(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {

		if(!Permission.permissionLevel(args.account, args.member, Permissions.MOD.level)) {
			args.channel.sendMessage(BuildEmbed.errorEmbed("You need to have the Moderator role in order to execute that.").build()).queue();
			return;
		}

		if (args.arguments.length == 0) {
			args.channel.sendMessage(BuildEmbed.errorEmbed("You need to provide a trigger name !").build()).queue();
			return;
		}

		EmbedBuilder embed = null;

		switch(args.arguments[0]) {
			case "Gamer":
				args.channel.sendMessage("<@&" + Roles.GAMER.id + ">").queue();
				embed = BuildEmbed.gamerEmbed();
				break;
			case "Midnight":
				try {
					JSONObject jsonObject = JsonDownloader.getJson("https://api.quotable.io/random");
					String author = jsonObject.getString("author");
					String quote = jsonObject.getString("content");
					
					if(quote.length() > 256) {
						args.channel.sendMessage(quote + author).queue();
					}
					embed = BuildEmbed.midnightQuote(quote, author);
				} catch(Exception e) {
					args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				}
				break;
			default:
				embed = BuildEmbed.errorEmbed("This trigger has not been registered !");
				break;
		}

		args.channel.sendMessage(embed.build()).queue();
	}

	public static void MerryChristmas(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("May this season of giving be the start of your better life. Have a great and blessed holiday ! Merry christmas @everyone !").queue();
	}
	
	public static void HappyNewYear(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("Every end marks a new beginning. Keep your spirits and determination unshaken, and you shall always walk the glory road. With courage, faith and great effort, you shall achieve everything you desire. May your home gets filled with good fortune, happy New Year @everyone !").queue();
	}
}
