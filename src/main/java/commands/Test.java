package commands;

import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/*
 * This command is designed to test new features
 */
public class Test extends Command {
	public Test() {
		super("test", "Test command, nothing to see here", Permission.OWNER,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {}
}
