package events;

import java.util.List;
import java.util.stream.Collectors;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoComplete extends ListenerAdapter {
	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();

		if (event.getName().equals("item")) {
			List<Command.Choice> options = glados.items.stream()
					.filter(item -> item.name.toLowerCase()
							.contains(event.getFocusedOption().getValue().toLowerCase()))
					.map(item -> new Command.Choice(item.name, item.name))
					.collect(Collectors.toList());

			// Only keep 25 first elements at max
			List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

			event.replyChoices(filtered).queue();
		}

		if (event.getName().equals("upgrade") || event.getName().equals("sell")) {
			Member author = event.getMember();
			Account authorAccount = glados.getAccount(author);
			List<Command.Choice> options = authorAccount.inventory.stream()
					.filter(item -> item.getFQName()
							.contains(event.getFocusedOption().getValue()))
					.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
					.collect(Collectors.toList());

			// Only keep 25 first elements at max
			List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

			event.replyChoices(filtered).queue();
		}
	}
}
