package events;

import java.util.List;
import java.util.Optional;
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

		if (eventName.equals("upgrade") || eventName.equals("sell") || eventName.equals("contract")) {
			User author = event.getUser();
			Account authorAccount = glados.getAccount(author);
			List<Command.Choice> options = authorAccount.inventory.stream()
					.filter(item -> item.getFQName().toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
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
				Optional<Account> optionalTargetAccount = glados.getAccountById(event.getOption("target").getAsString());

				// Check if the user put a user with no account
				if (optionalTargetAccount.isEmpty()) {
					// Return no choices
					event.replyChoice("", "");
					return;
				}

				Account targetAccount = optionalTargetAccount.get();

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
			Optional<Account> optionalTargetAccount = glados.getAccountById(event.getOption("target").getAsString());

			// Check if the user put a user with no account
			if (optionalTargetAccount.isEmpty()) {
				// Return no choices
				event.replyChoice("", "");
				return;
			}

			Account targetAccount = optionalTargetAccount.get();

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
