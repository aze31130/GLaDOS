package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permissions;

public class Inventory extends Command {
	public Inventory() {
		super("inventory",
				"[WIP] Shows your inventory. Admin privileges required",
				Permissions.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
