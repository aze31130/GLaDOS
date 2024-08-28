package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
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

public class Inventory extends Command {
	public Inventory() {
		super(
				"inventory",
				"Shows your inventory.",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.USER, "target", "The account you want to consult"),
						new OptionData(OptionType.INTEGER, "page", "The inventory page you want to open")));
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

		// Check if another account is given
		OptionMapping target = event.getOption("target");
		if (target != null)
			authorAccount = glados.getAccount(target.getAsUser());

		int startingPage = Optional.ofNullable(event.getOption("page")).map(OptionMapping::getAsInt).orElse(1);
		int lastPage = (int) Math.ceil((double) authorAccount.inventory.size() / ItemUtils.AMOUNT_ITEM_PER_PAGE);

		if ((startingPage > lastPage) || (startingPage <= 0)) {
			event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed(
					"You do not have " + startingPage + " pages in your inventory ! You last page is " + lastPage + ".").build())
					.queue();
			return;
		}

		EmbedBuilder inventory = BuildEmbed.inventoryEmbed(authorAccount, startingPage);

		for (items.Item i : ItemUtils.getUserInventory(authorAccount, startingPage))
			inventory.addField(i.rarity.emote + " " + i.getFQName(), i.rarity.name() + " | " + i.getValue() + " :coin:", false);

		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("PrevPage", "Previous Page"),
				Button.primary("NextPage", "Next Page"));

		event.getHook().sendMessageEmbeds(inventory.build()).addActionRow(buttons).queue();
	}
}
