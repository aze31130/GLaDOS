package commands;

import java.awt.Color;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

public class RandomDog extends Command {
	public RandomDog(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		try {
			EmbedBuilder info = new EmbedBuilder()
					.setTitle("Random Dog Picture")
					.setImage(JsonDownloader.getJson("https://dog.ceo/api/breeds/image/random").getString("message"))
					.setColor(Color.WHITE)
					.setFooter("Request made at " + new Logger(false));
			args.channel.sendMessage(info.build()).queue();
		} catch(Exception e) {
			args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
