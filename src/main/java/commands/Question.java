package commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Question {
	public static void randomQuestion(MessageChannel ch) {
		try {
			// parameters:
			// difficulty
			// theme
			// String apiUrl = "https://opentdb.com/api.php?amount=1";
			// Buttons with answers

			String category = "";
			String question = "";
			String difficulty = "";
			String correctAnswer = "";

			System.out.println(category);
			System.out.println(difficulty);
			System.out.println(question);
			System.out.println(correctAnswer);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			EmbedBuilder info = new EmbedBuilder();

			info.setAuthor("Difficulty: " + difficulty);
			info.setTitle("Question: " + question);
			info.setDescription("Category" + category);
			info.setColor(Color.ORANGE);
			info.setFooter("Request made at " + formatter.format(date));
			ch.sendMessageEmbeds(info.build()).queue();

		} catch (Exception e) {
			ch.sendMessage(e.toString()).queue();
		}
	}
}
