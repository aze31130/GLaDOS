package events;

import java.util.List;
import java.util.stream.Collectors;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoComplete extends ListenerAdapter {
	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		String eventName = event.getName();

		if (eventName.equals("item") || eventName.equals("give")) {
			List<Command.Choice> options = glados.items.stream()
					.filter(item -> item.name.toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
					.map(item -> new Command.Choice(item.name, item.name))
					.collect(Collectors.toList());

			// Only keep 25 first elements at max
			List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

			event.replyChoices(filtered).queue();
			return;
		}

		if (eventName.equals("upgrade") || eventName.equals("sell")) {
			User author = event.getUser();
			Account authorAccount = glados.getAccount(author);
			List<Command.Choice> options = authorAccount.inventory.stream()
					.filter(item -> item.getFQName().contains(event.getFocusedOption().getValue()))
					.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
					.collect(Collectors.toList());

			// Only keep 25 first elements at max
			List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

			event.replyChoices(filtered).queue();
			return;
		}

		if (eventName.equals("trade") && event.getOption("target") != null) {
			String field = event.getFocusedOption().getName();

			if (field.equals("srcitem")) {
				Account authorAccount = glados.getAccount(event.getUser());

				List<Command.Choice> options = authorAccount.inventory.stream()
						.filter(item -> item.getFQName().toLowerCase()
								.contains(event.getFocusedOption().getValue().toLowerCase()))
						.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
						.collect(Collectors.toList());

				// Only keep 25 first elements at max
				List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

				event.replyChoices(filtered).queue();
				return;
			}

			if (field.equals("dstitem")) {
				Account targetAccount = glados.getAccountById(event.getOption("target").getAsString());

				// Check if the user put a user with no account
				if (targetAccount == null) {
					// Return no choices
					event.replyChoice("", "");
					return;
				}

				List<Command.Choice> options = targetAccount.inventory.stream()
						.filter(item -> item.getFQName().toLowerCase()
								.contains(event.getFocusedOption().getValue().toLowerCase()))
						.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
						.collect(Collectors.toList());

				// Only keep 25 first elements at max
				List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

				event.replyChoices(filtered).queue();
				return;
			}
		}

		if (eventName.equals("delete") && event.getOption("target") != null) {
			Account targetAccount = glados.getAccountById(event.getOption("target").getAsString());

			// Check if the user put a user with no account
			if (targetAccount == null) {
				// Return no choices
				event.replyChoice("", "");
				return;
			}

			List<Command.Choice> options = targetAccount.inventory.stream()
					.filter(item -> item.getFQName().toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
					.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
					.collect(Collectors.toList());

			// Only keep 25 first elements at max
			List<Command.Choice> filtered = options.subList(0, Math.min(options.size(), 25));

			event.replyChoices(filtered).queue();
			return;
		}
	}
}
