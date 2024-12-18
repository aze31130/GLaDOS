package commands;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import utils.BuildEmbed;

public class Give extends Command {
	public Give() {
		super(
				"give",
				"Gives an item to someone. Owner privileges required.",
				Permission.OWNER,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList(
						new OptionData(OptionType.USER, "target", "The user you want to give an item", true),
						new OptionData(OptionType.STRING, "item", "The item you want to give", false, true),
						new OptionData(OptionType.NUMBER, "quality", "The item quality you want to generate", false),
						new OptionData(OptionType.INTEGER, "starforce", "The item starforce level you want to generate", false),
						new OptionData(OptionType.INTEGER, "money", "The amount of money you want to give", false)));
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) throws CloneNotSupportedException {
		GLaDOS glados = GLaDOS.getInstance();

		Optional<Account> optionalTarget = glados.getAccountById(event.getOption("target").getAsString());

		if (optionalTarget.isEmpty()) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed("This user does not have an account. He needs to /drop first !").build()).queue();
			return;
		}

		Account target = optionalTarget.get();

		Optional<String> itemName = Optional.ofNullable(event.getOption("item")).map(OptionMapping::getAsString);
		Optional<Double> itemQuality = Optional.ofNullable(event.getOption("quality")).map(OptionMapping::getAsDouble);
		Optional<Integer> itemStarforce = Optional.ofNullable(event.getOption("starforce")).map(OptionMapping::getAsInt);
		Optional<Integer> moneyAmount = Optional.ofNullable(event.getOption("money")).map(OptionMapping::getAsInt);


		// Check if it's an item give
		if (itemName.isPresent()) {
			// Check if the given item name exist
			Optional<items.Item> item = glados.getItemByName(itemName.get());

			if (item.isEmpty()) {
				event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("Unknown item " + itemName.get()).build()).queue();
				return;
			}

			items.Item i = (items.Item) item.get().clone();

			if (itemQuality.isPresent())
				i.quality = itemQuality.get();
			else
				i.quality = new Random().nextDouble();

			if (itemStarforce.isPresent())
				i.starForceLevel = itemStarforce.get();

			// Ensure item cannot go beyond max stats
			i.makeLegit();

			target.inventory.add(i);

			event.getHook().sendMessageEmbeds(BuildEmbed.itemDropEmbed(target.user, i, glados.cdn).build()).queue();
		}

		// Check if it's a money give
		if (moneyAmount.isPresent()) {
			if (moneyAmount.get() <= 0) {
				event.getHook().sendMessageEmbeds(BuildEmbed.errorEmbed("You cannot give this amount of money !").build())
						.queue();
				return;
			}

			target.money += moneyAmount.get();
			event.getHook().sendMessageEmbeds(BuildEmbed.moneyDropEmbed(target.user, moneyAmount.get(), target.money).build())
					.queue();
		}
	}
}
