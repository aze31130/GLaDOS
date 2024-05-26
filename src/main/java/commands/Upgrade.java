package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.BuildEmbed;
import utils.ItemUtils;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Upgrade extends Command {
	public Upgrade() {
		super(
				"upgrade",
				"Upgrade an item from your inventory.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(new OptionData(OptionType.STRING, "name", "The item full qualified name you want to upgrade.", true,
						true)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		String itemName = event.getOption("name").getAsString();

		// Search item
		Optional<items.Item> searchingItem = authorAccount.getItemByFQName(itemName);

		// Check if the owner own the item
		if (!ItemUtils.userOwnItem(authorAccount, itemName)) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You do not own this item !").build()).queue();
			return;
		}

		// Check if the item is upgradable
		items.Item item = searchingItem.get();
		if ((item.starForceMaxLevel == 0) || item.isMaxed()) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You cannot upgrade this item !").build()).queue();
			return;
		}

		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("Upgrade", "Upgrade"),
				Button.secondary("Exit", "Exit Upgrade"));

		event.getHook().sendMessageEmbeds(BuildEmbed.upgradeEmbed(authorAccount, item).build()).addActionRow(buttons).queue();
	}
}
