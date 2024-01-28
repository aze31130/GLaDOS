package commands;

import java.util.Arrays;
import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Give extends Command {
	public Give() {
		super("give", "Gives an item to someone. Owner privileges required.",
				Permission.OWNER, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
