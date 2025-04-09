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

	private static final int MAX_AUTOCOMPLETE = 25;

	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		String eventName = event.getName();
		String field = event.getFocusedOption().getName();

		if (eventName.equals("item") || eventName.equals("give")) {
			event.replyChoices(autoCompleteItems(event.getFocusedOption().getValue().toLowerCase())).queue();
			return;
		}

		if (eventName.equals("upgrade") || eventName.equals("sell") || eventName.equals("contract")) {
			User author = event.getUser();
			Account authorAccount = glados.getAccount(author);

			event.replyChoices(autoCompleteItems(authorAccount, event.getFocusedOption().getValue().toLowerCase())).queue();
			return;
		}

		if (eventName.equals("trade") && event.getOption("target") != null) {

			if (field.equals("srcitem")) {
				Account authorAccount = glados.getAccount(event.getUser());

				event.replyChoices(autoCompleteItems(authorAccount, event.getFocusedOption().getValue().toLowerCase())).queue();
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

				event.replyChoices(autoCompleteItems(targetAccount, event.getFocusedOption().getValue().toLowerCase())).queue();
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

			event.replyChoices(autoCompleteItems(targetAccount, event.getFocusedOption().getValue().toLowerCase())).queue();
			return;
		}
	}

	public List<Command.Choice> autoCompleteItems(String itemName) {
		GLaDOS glados = GLaDOS.getInstance();

		List<Command.Choice> options = glados.items.stream()
				.filter(item -> item.name.toLowerCase().contains(itemName))
				.map(item -> new Command.Choice(item.name, item.name))
				.collect(Collectors.toList());

		// Only keep the few first elements at max
		return options.subList(0, Math.min(options.size(), MAX_AUTOCOMPLETE));
	}

	public List<Command.Choice> autoCompleteItems(Account account, String itemName) {
		List<Command.Choice> options = account.inventory.stream()
				.filter(item -> item.name.toLowerCase().contains(itemName))
				.map(item -> new Command.Choice(item.getFQName(), item.getFQName()))
				.collect(Collectors.toList());

		// Only keep the few first elements at max
		return options.subList(0, Math.min(options.size(), MAX_AUTOCOMPLETE));
	}
}
