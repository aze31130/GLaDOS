package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import utils.BuildEmbed;
import utils.ItemUtils;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Inventory extends Command {
	public Inventory() {
		super("inventory",
				"Shows your inventory.",
				Permission.NONE,
				Arrays.asList(new OptionData(OptionType.INTEGER, "page",
						"The inventory page you want to open")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel source = event.getMessageChannel();
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account authorAccount = glados.getAccount(author);

		int startingPage =
				Optional.ofNullable(event.getOption("page")).map(OptionMapping::getAsInt).orElse(1);
		int lastPage = (int) Math
				.ceil((double) authorAccount.inventory.size() / ItemUtils.AMOUNT_ITEM_PER_PAGE);

		if ((startingPage > lastPage) || (startingPage <= 0)) {
			source.sendMessageEmbeds(BuildEmbed
					.errorEmbed("You do not have " + startingPage
							+ " pages in your inventory ! You last page is " + lastPage + ".")
					.build()).queue();
			return;
		}

		EmbedBuilder inventory = BuildEmbed.inventoryEmbed(authorAccount, startingPage);

		for (items.Item i : ItemUtils.getUserInventory(authorAccount, startingPage))
			inventory.addField(i.getFQName(), i.rarity.name(), false);

		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("PrevPage", "Previous Page"),
				Button.primary("NextPage", "Next Page"));

		source.sendMessageEmbeds(inventory.build()).addActionRow(buttons).queue();
	}
}
