package commands;

import java.util.Arrays;
import java.util.List;
import accounts.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Role extends Command {
	public Role() {
		super("role", "Generate buttons for members to clic on.",
				Permission.MODERATOR, Arrays.asList(new OptionData(OptionType.MENTIONABLE, "role",
						"Generates the join/leave button for the given role.")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		net.dv8tion.jda.api.entities.Role argument = event.getOption("role").getAsRole();

		List<ItemComponent> buttons = Arrays.asList(
				Button.primary("+" + argument.getId(), "Join " + argument.getName()),
				Button.danger("-" + argument.getId(), "Leave" + argument.getName()));

		source.sendMessage("200 OK").addActionRow(buttons).queue();
	}
}
