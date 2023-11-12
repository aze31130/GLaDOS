package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import accounts.Permissions;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import utils.BuildEmbed;
import utils.JsonDownloader;

/*
 * This command will send an embed containing a question with buttons to answer. Warning, only the
 * user that requested the question is allowed to anwser.
 */
public class Question extends Command {
	public Question(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();

		if (g.goodAnswer.length() > 0) {
			event.getChannel()
					.sendMessageEmbeds(
							BuildEmbed.errorEmbed("You have to anwser previous question").build())
					.queue();
			return;
		}

		// Check if the user provided a difficulty
		List<String> possibleDifficulties = Arrays.asList("easy", "normal", "hard");
		String difficulty = "";

		for (OptionMapping om : event.getOptions())
			if (om != null && possibleDifficulties.contains(om.getAsString()))
				difficulty = om.getAsString();

		try {
			JSONObject response = JsonDownloader
					.getJson("https://opentdb.com/api.php?amount=1&difficulty=" + difficulty);

			JSONObject question = response.getJSONArray("results").getJSONObject(0);
			List<ItemComponent> responses = new ArrayList<>();
			responses.add(Button.primary("?" + question.getString("correct_answer"),
					question.getString("correct_answer")));
			JSONArray wrongAnswers = question.getJSONArray("incorrect_answers");

			for (int i = 0; i < wrongAnswers.length(); i++)
				responses.add(
						Button.primary("?" + wrongAnswers.getString(i), wrongAnswers.getString(i)));

			Collections.shuffle(responses);

			// Post message with buttons with answers
			event.getChannel()
					.sendMessageEmbeds(BuildEmbed.questionEmbed(
							question.getString("question").replace("&quot;", "'").replace("&#039;",
									"'"),
							question.getString("category"), question.getString("difficulty"))
							.build())
					.addActionRow(responses).queue();

			// Save good answer in glados variable
			g.goodAnswer = "?" + question.getString("correct_answer");

		} catch (JSONException | IOException exception) {
			exception.printStackTrace();
		}
	}
}
