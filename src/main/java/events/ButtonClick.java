package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;
import utils.ItemUtils;

public class ButtonClick extends ListenerAdapter {
	public void onButtonInteraction(ButtonInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		String trigger = event.getComponentId();

		HashMap<String, String> dictionary = new HashMap<>();
		dictionary.put("Broadcast", glados.roleBroadcastMessenger);
		dictionary.put("Gamer", glados.roleGamer);
		dictionary.put("Member", glados.roleMember);
		dictionary.put("Artistic", glados.roleArtistic);
		dictionary.put("International", glados.roleInternational);
		dictionary.put("Developer", glados.roleDeveloper);
		dictionary.put("NSFW", glados.roleNsfw);
		dictionary.put(glados.roleBroadcastMessenger, glados.roleBroadcastMessenger);
		dictionary.put(glados.roleGamer, glados.roleGamer);
		dictionary.put(glados.roleMember, glados.roleMember);
		dictionary.put(glados.roleArtistic, glados.roleArtistic);
		dictionary.put(glados.roleInternational, glados.roleInternational);
		dictionary.put(glados.roleDeveloper, glados.roleDeveloper);
		dictionary.put(glados.roleNsfw, glados.roleNsfw);


		// Check if action is Next or Previous Inventory Page
		if (trigger.equals("NextPage") || trigger.equals("PrevPage")) {
			User author = event.getUser();
			Account authorAccount = glados.getAccount(author);

			/*
			 * Check if the clicker owns the inventory. This is to make sure another user cannot click on the
			 * inventory of someone else.
			 */
			String inventoryName = event.getMessage().getEmbeds().get(0).getAuthor().getName();
			String clickerName = event.getUser().getName();
			if (!inventoryName.equals(clickerName)) {
				event.replyEmbeds(
						BuildEmbed.errorEmbed("You cannot explore other's inventory !").build())
						.setEphemeral(true).queue();
				return;
			}

			// Get the current page
			int pageNumber =
					Integer.parseInt(event.getMessage().getEmbeds().get(0).getFooter().getText().replace(" ", "").split("/")[0]);

			// Check if the event is Next Page
			int newPageNumber = trigger.equals("NextPage") ? pageNumber + 1 : pageNumber - 1;

			// Check if the new page is valid (aka the page exist)
			int lastPage = (int) Math.ceil((double) authorAccount.inventory.size() / ItemUtils.AMOUNT_ITEM_PER_PAGE);

			if ((newPageNumber > lastPage) || (newPageNumber <= 0)) {
				event.replyEmbeds(BuildEmbed.errorEmbed("You cannot see page " + newPageNumber + " !")
						.build()).setEphemeral(true).queue();
				return;
			}

			EmbedBuilder inventory = BuildEmbed.inventoryEmbed(authorAccount, newPageNumber);

			for (items.Item i : ItemUtils.getUserInventory(authorAccount, newPageNumber))
				inventory.addField(i.getFQName(), i.rarity.name(), false);

			event.editMessageEmbeds(inventory.build()).queue();
			return;
		}

		if (trigger.equals("AcceptTrade") || trigger.equals("RefuseTrade")) {
			/*
			 * In order to match the trade author and trade target, we will read the description field that
			 * should look like that: <@AuthorID>=><@TargetID>. To achieve this, we can use the following regex:
			 */
			final String pattern = "<@(\\d+)>";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(event.getMessage().getEmbeds().get(0).getDescription());
			m.find();
			String tradeAuthor = m.group(1);
			m.find();
			String tradeTarget = m.group(1);

			// Ensure the target clicked the button and no one else
			String clickerId = event.getUser().getId();

			if (!(clickerId.equals(tradeAuthor) || clickerId.equals(tradeTarget))) {
				event.replyEmbeds(BuildEmbed.errorEmbed("Only the trade target can accept or refuse the trade offer.").build())
						.setEphemeral(true).queue();
				return;
			}

			// Check if trade is refused and remove buttons if refused
			if (trigger.equals("RefuseTrade")) {
				event.editMessageEmbeds(BuildEmbed.errorEmbed("Trade refused by " + event.getUser().getAsMention() + " !")
						.build()).queue();
				event.getMessage().editMessageComponents(new ArrayList<>()).queue();
				return;
			}

			if (trigger.equals("AcceptTrade") && !clickerId.equals(tradeTarget)) {
				event.replyEmbeds(BuildEmbed.errorEmbed("Only the trade target can accept the trade !").build())
						.setEphemeral(true).queue();
				return;
			}

			Optional<Account> optionalAuthorAccount = glados.getAccountById(tradeAuthor);
			Optional<Account> optionalTargetAccount = glados.getAccountById(tradeTarget);

			if (optionalAuthorAccount.isEmpty() || optionalTargetAccount.isEmpty()) {
				event.editMessageEmbeds(BuildEmbed.errorEmbed("One of the user does not have an inventory ! Operation cancelled")
						.build()).queue();
				event.getMessage().editMessageComponents(new ArrayList<>()).queue();
				return;
			}


			Account authorAccount = optionalAuthorAccount.get();
			Account targetAccount = optionalTargetAccount.get();

			String srcItemFQName = "";
			int srcMoney = 0;
			String dstItemFQName = "";
			int dstMoney = 0;

			for (Field f : event.getMessage().getEmbeds().get(0).getFields()) {
				switch (f.getName()) {
					case "Item source":
						srcItemFQName = f.getValue();
						break;
					case "Money source":
						srcMoney = Integer.parseInt(f.getValue());
						break;
					case "Item destination":
						dstItemFQName = f.getValue();
						break;
					case "Money destination":
						dstMoney = Integer.parseInt(f.getValue());
						break;
					default:
						break;
				}
			}

			// Checks again if the trade is still possible
			if (!ItemUtils.isTradePossible(authorAccount, targetAccount, srcItemFQName, srcMoney, dstItemFQName, dstMoney)) {
				event.editMessageEmbeds(BuildEmbed.errorEmbed("The trade is no longer possible ! Operation cancelled !").build())
						.queue();
				return;
			}

			// Check presence of src item
			if (srcItemFQName.length() > 1) {
				items.Item srcItem = authorAccount.getItemByFQName(srcItemFQName).get();
				authorAccount.inventory.remove(srcItem);
				targetAccount.inventory.add(srcItem);
			}

			// Check presence of dst item
			if (dstItemFQName.length() > 1) {
				items.Item dstItem = targetAccount.getItemByFQName(dstItemFQName).get();
				targetAccount.inventory.remove(dstItem);
				authorAccount.inventory.add(dstItem);
			}

			// Exchange money
			authorAccount.money -= srcMoney;
			targetAccount.money += srcMoney;
			targetAccount.money -= dstMoney;
			authorAccount.money += dstMoney;

			// Updates the embed
			event.editMessageEmbeds(BuildEmbed.successEmbed("Trade completed !").build()).queue();
			event.getMessage().editMessageComponents(new ArrayList<>()).queue();
			return;
		}

		// Check if action is add role
		if (trigger.startsWith("+")) {
			trigger = trigger.replace("+", "");
			if (!dictionary.containsKey(trigger)) {
				event.reply("You cannot get this role by doing that !").setEphemeral(true).setSuppressedNotifications(true)
						.queue();
				return;
			}

			trigger.replace("+", "");
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(dictionary.get(trigger)))
					.queue();

			event.reply("Successfully added role <@&" + dictionary.get(trigger) + "> !").setEphemeral(true)
					.setSuppressedNotifications(true).queue();
			return;
		}

		// Check if action is remove role
		if (trigger.startsWith("-")) {
			trigger = trigger.replace("-", "");
			if (!dictionary.containsKey(trigger)) {
				event.reply("You cannot get rid of this role by doing that !").setEphemeral(true)
						.setSuppressedNotifications(true).queue();
				return;
			}

			event.getGuild()
					.removeRoleFromMember(event.getMember().getUser(), event.getGuild().getRoleById(dictionary.get(trigger)))
					.queue();

			event.reply("Successfully removed role <@&" + dictionary.get(trigger) + "> !").setEphemeral(true)
					.setSuppressedNotifications(true).queue();
			return;
		}

		// Check if action is question
		if (trigger.startsWith("?")) {
			if (glados.goodAnswer.equals(trigger)) {
				event.replyEmbeds(BuildEmbed.successEmbed("Good answer " + event.getButton().getLabel() + " !").build()).queue();
				// Removes buttons when good answer is triggered
				event.getMessage().editMessageComponents(new ArrayList<>()).queue();
				glados.goodAnswer = "";
			} else {
				event.replyEmbeds(BuildEmbed.errorEmbed("Wrong answer " + event.getButton().getLabel() + " !").build()).queue();
			}
			return;
		}

		// Else, check if the trigger is the good anwser
		event.replyEmbeds(BuildEmbed.errorEmbed("Unknown signal " + event.getButton().getLabel() + " !").build()).queue();
	}
}
