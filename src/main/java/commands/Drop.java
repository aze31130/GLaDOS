package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permissions;

public class Drop extends Command {
	public Drop() {
		super("drop",
				"[WIP] Drops you a random item. Admin privileges required",
				Permissions.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
