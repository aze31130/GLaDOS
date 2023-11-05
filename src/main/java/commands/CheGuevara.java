package commands;

import java.awt.Color;
import java.util.List;

import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class CheGuevara extends Command {
	public CheGuevara(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		try {
			long amount = 1;
			if (args.arguments.length > 0) {
				amount = Long.parseLong(args.arguments[0]);
			}

			if (amount > 10) {
				amount = 10;
			}

			if (amount < 0) {
				amount = 0;
			}

			while (amount > 0) {
				JSONObject jsonObject = JsonDownloader.getJson("https://api.chucknorris.io/jokes/random");
				String meme = jsonObject.getString("value");
				meme = meme.replace("Chuck Norris", "Che Guevara");
				meme = meme.replace("Chuck", "Che");
				meme = meme.replace("Norris", "Guevara");
				EmbedBuilder che = new EmbedBuilder()
						.setTitle("Che Guevara")
						.setThumbnail(
								"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png")
						.setDescription(meme)
						.setColor(Color.BLUE)
						.setFooter("Request made at " + new Logger(false));
				args.channel.sendMessageEmbeds(che.build()).queue();
				amount--;
			}
		} catch (Exception e) {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
