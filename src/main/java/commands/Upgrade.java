package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
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
				Arrays.asList(new OptionData(OptionType.STRING, "name", "The item name you want to upgrade.", true, true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannelUnion source = event.getChannel();
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account authorAccount = glados.getAccount(author);

		String itemName = event.getOption("name").getAsString();

		// Search item
		Optional<items.Item> searchingItem = glados.items.stream().filter(i -> i.getFQName().equals(itemName)).findFirst();

		if (searchingItem.isEmpty()) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("This item does not exist !").build()).queue();
			return;
		}

		// Check if the owner own the item
		if (!ItemUtils.userOwnItem(authorAccount, itemName)) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("You do not own this item !").build()).queue();
			return;
		}

		// Check if the item is upgradable
		items.Item item = searchingItem.get();
		if ((item.starForceMaxLevel == 0) || (item.starForceLevel >= item.starForceMaxLevel)) {
			source.sendMessageEmbeds(BuildEmbed.errorEmbed("This item is not upgradable !").build()).queue();
			return;
		}

		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("Upgrade", "Upgrade"),
				Button.secondary("Exit", "Exit Upgrade"));

		source.sendMessageEmbeds(BuildEmbed.upgradeEmbed(authorAccount, item).build()).addActionRow(buttons).queue();
	}
}
