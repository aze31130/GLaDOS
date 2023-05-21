package commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Meme {
	public static void aprilFools(TextChannel channel) {
		String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("(Inserting April Joke...) Congratulations, the test is now over. Happy April Fool's Day!");
		info.setDescription("GLaDOS");
		info.setColor(Color.CYAN);
		info.setFooter("Request made at " + time);
		channel.sendMessage(info.build()).queue();
	}
}
