package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import java.util.Arrays;
import accounts.Permissions;

public class Vote extends Command {
	public Vote() {
		super("vote", "Submit a vote, for a feature request or any other subject you want",
				Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextInput subject = TextInput.create("subject", "Subject", TextInputStyle.SHORT)
				.setPlaceholder("Subject of the vote").setMinLength(1).setMaxLength(200)
				.build();

		TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
				.setPlaceholder("Context of this vote, explain yourself clearly").setMinLength(1)
				.setMaxLength(1000)
				.build();

		TextInput reactions =
				TextInput.create("reactions", "Expected reactions", TextInputStyle.PARAGRAPH)
						.setPlaceholder(
								"Describe here how people vote\nYes => ✅\nNo => ❌\nWarning: given reactions must be unicode emotes !")
						.setMinLength(1)
						.setMaxLength(1000)
						.build();

		Modal modal = Modal.create("vote", "Submit a Vote")
				.addComponents(ActionRow.of(subject), ActionRow.of(body), ActionRow.of(reactions))
				.build();

		event.replyModal(modal).queue();
	}
}
