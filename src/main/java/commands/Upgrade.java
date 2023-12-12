package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permissions;

public class Upgrade extends Command {
	public Upgrade() {
		super("upgrade",
				"[WIP] Upgrade an item. Admin privileges required",
				Permissions.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
