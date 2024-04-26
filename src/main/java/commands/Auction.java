package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.Arrays;
import accounts.Permission;

public class Auction extends Command {
	public Auction() {
		super(
				"auction",
				"[WIP] Create an auction post. Anyone can accept your offer.",
				Permission.OWNER,
				Tag.RPG,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// TextInput item = TextInput.create("subject", "Subject",
		// TextInputStyle.SHORT).setPlaceholder("Subject of the vote")
		// .setMinLength(1).setMaxLength(200).build();

		// TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
		// .setPlaceholder("Context of this vote, explain yourself
		// clearly").setMinLength(1).setMaxLength(1000).build();

		// TextInput reactions = TextInput.create("reactions", "Expected reactions",
		// TextInputStyle.PARAGRAPH)
		// .setPlaceholder(
		// "Describe here how people vote\nYes => ✅\nNo => ❌\nWarning: given reactions must be unicode
		// emotes !")
		// .setMinLength(1)
		// .setMaxLength(1000)
		// .build();

		// Modal modal = Modal.create("vote", "Submit a Vote")
		// .addComponents(ActionRow.of(subject), ActionRow.of(body), ActionRow.of(reactions))
		// .build();

		// event.replyModal(modal).queue();
	}
}
