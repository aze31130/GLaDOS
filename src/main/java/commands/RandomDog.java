package commands;

import java.awt.Color;
import java.util.List;

import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class RandomDog extends Command {
	public RandomDog(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		try {
			EmbedBuilder info = new EmbedBuilder()
					.setTitle("Random Dog Picture")
					.setImage(JsonDownloader.getJson("https://dog.ceo/api/breeds/image/random").getString("message"))
					.setColor(Color.WHITE)
					.setFooter("Request made at " + new Logger(false));
			args.channel.sendMessageEmbeds(info.build()).queue();
		} catch (Exception e) {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
