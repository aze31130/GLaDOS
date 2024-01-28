package commands;

import java.util.Arrays;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Delete extends Command {
	public Delete() {
		super("delete", "Deletes an item from someone's inventory. Owner privileges required.",
				Permission.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
