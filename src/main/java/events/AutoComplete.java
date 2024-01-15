package events;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoComplete extends ListenerAdapter {
	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();

		if (event.getName().equals("item")) {
			List<Command.Choice> options = glados.items.stream()
					.filter(item -> item.name.startsWith(event.getFocusedOption().getValue()))
					.map(item -> new Command.Choice(item.name, item.name))
					.collect(Collectors.toList());

			if (options.size() > 25) {
				event.replyChoices(new ArrayDeque<>()).queue();
				return;
			}
			event.replyChoices(options).queue();
		}
	}
}
