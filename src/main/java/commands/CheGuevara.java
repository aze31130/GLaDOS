package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.List;

import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class CheGuevara extends Command {
	public CheGuevara(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		Integer amount = event.getOption("amount").getAsInt();

		if (amount > 10)
			amount = 10;

		if (amount < 0)
			amount = 0;

		try {
			while (amount > 0) {
				JSONObject jsonObject =
						JsonDownloader.getJson("https://api.chucknorris.io/jokes/random");
				String meme = jsonObject.getString("value");
				meme = meme.replace("Chuck Norris", "Che Guevara");
				meme = meme.replace("Chuck", "Che");
				meme = meme.replace("Norris", "Guevara");
				EmbedBuilder che = new EmbedBuilder().setTitle("Che Guevara").setThumbnail(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png")
						.setDescription(meme).setColor(Color.BLUE).setTimestamp(Instant.now());
				source.sendMessageEmbeds(che.build()).queue();
				amount--;
			}
		} catch (Exception e) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
