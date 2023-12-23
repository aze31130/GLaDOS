package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;

import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permission;

public class CheGuevara extends Command {
	public CheGuevara() {
		super("che-guevara", "Generate a random fact about Che-Guevara",
				Permission.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();

		try {
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
		} catch (Exception e) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
