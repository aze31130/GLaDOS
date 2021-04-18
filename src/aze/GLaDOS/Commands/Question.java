package aze.GLaDOS.Commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;


public class Question {
	public static void randomQuestion(TextChannel ch){
		try {
			//parameters:
			//difficulty
			//theme
			String apiUrl = "https://opentdb.com/api.php?amount=1";
			URL questionURL = new URL(apiUrl);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(questionURL.openConnection().getInputStream()));
			
			String lines;
			
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
				ch.sendMessage(info.build()).queue();
				
			} catch(Exception e) {
				ch.sendMessage(e.toString()).queue();
			}
	}
}
