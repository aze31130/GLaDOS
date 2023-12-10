package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import java.util.Arrays;
import accounts.Permissions;

public class Report extends Command {
	public Report() {
		super("report", "Reports a bug, a feature request or any subject you want",
				Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextInput subject = TextInput.create("subject", "Subject", TextInputStyle.SHORT)
				.setPlaceholder("Subject of this ticket").setMinLength(10).setMaxLength(100)
				.build();

		TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
				.setPlaceholder("Your concerns go here").setMinLength(30).setMaxLength(1000)
				.build();

		Modal modal = Modal.create("report", "Report")
				.addComponents(ActionRow.of(subject), ActionRow.of(body)).build();

		event.replyModal(modal).queue();
	}
}
