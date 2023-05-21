package commands;

import java.awt.Color;
import org.json.JSONObject;
import utils.BuildEmbed;
import utils.JsonDownloader;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

public class CheGuevara extends Command {
	public CheGuevara(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		try {
			long amount = 1;
			if(args.arguments.length > 0) {
				amount = Long.parseLong(args.arguments[0]);
			}
			
			if(amount > 10) {
				amount = 10;
			}
			
			if(amount < 0) {
				amount = 0;
			}
			
			while(amount > 0) {
				JSONObject jsonObject = JsonDownloader.getJson("https://api.chucknorris.io/jokes/random");
				String meme = jsonObject.getString("value");
				meme = meme.replace("Chuck Norris", "Che Guevara");
				meme = meme.replace("Chuck", "Che");
				meme = meme.replace("Norris", "Guevara");
				EmbedBuilder che = new EmbedBuilder()
						.setTitle("Che Guevara")
						.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Che_Guevara_vector_SVG_format.svg/1200px-Che_Guevara_vector_SVG_format.svg.png")
						.setDescription(meme)
						.setColor(Color.BLUE)
						.setFooter("Request made at " + new Logger(false));
				args.channel.sendMessage(che.build()).queue();
				amount--;
			}
		} catch(Exception e) {
			args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
