package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.BuildEmbed;
import utils.JsonDownloader;

/*
 * This command will send an embed containing a question with buttons to answer. Warning, only the
 * user that requested the question is allowed to anwser.
 */
public class Question extends Command {
	public Question() {
		super(
				"question",
				"Challenges your general knowledge",
				Permission.NONE,
				Tag.FUN,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.STRING, "difficulty", "Can be [easy, normal, hard]. Default is random")
								.addChoice("easy", "easy")
								.addChoice("normal", "normal")
								.addChoice("hard", "hard")));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) throws JSONException, IOException {
		GLaDOS g = GLaDOS.getInstance();

		if (g.goodAnswer.length() > 0) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You have to anwser previous question").build()).queue();
			return;
		}

		// Check if the user provided a difficulty
		List<String> possibleDifficulties = Arrays.asList("easy", "normal", "hard");
		String difficulty = "";

		for (OptionMapping om : event.getOptions())
			if (om != null && possibleDifficulties.contains(om.getAsString()))
				difficulty = om.getAsString();

		JSONObject response =
				JsonDownloader.getJson("https://opentdb.com/api.php?amount=1&encode=base64&difficulty=" + difficulty);

		JSONObject question = response.getJSONArray("results").getJSONObject(0);
		List<ItemComponent> responses = new ArrayList<>();
		responses.add(Button.primary("?" + new String(Base64.getDecoder().decode(question.getString("correct_answer"))),
				new String(Base64.getDecoder().decode(question.getString("correct_answer")))));
		JSONArray wrongAnswers = question.getJSONArray("incorrect_answers");

		for (int i = 0; i < wrongAnswers.length(); i++)
			responses.add(Button.primary("?" + new String(Base64.getDecoder().decode(wrongAnswers.getString(i))),
					new String(Base64.getDecoder().decode(wrongAnswers.getString(i)))));

		Collections.shuffle(responses);

		// Post message with buttons with answers
		event.getHook().sendMessageEmbeds(BuildEmbed.questionEmbed(
				new String(Base64.getDecoder().decode(question.getString("question"))),
				new String(Base64.getDecoder().decode(question.getString("category"))),
				new String(Base64.getDecoder().decode(question.getString("difficulty"))))
				.build()).addActionRow(responses).queue();

		// Save good answer in glados variable
		g.goodAnswer = "?" + new String(Base64.getDecoder().decode(question.getString("correct_answer")));
	}
}
