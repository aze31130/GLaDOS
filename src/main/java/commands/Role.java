package commands;

import java.util.Arrays;

import accounts.Permissions;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Role extends Command {
	public Role() {
		super("role", "Generate buttons for members to clic on.",
				Permissions.MODERATOR, Arrays.asList(new OptionData(OptionType.MENTIONABLE, "role",
						"Generates the join/leave button for the given role.")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
