package commands;

import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Permission;

public class Ip extends Command {
	public Ip() {
		super(
				"ip",
				"Retrieve the ipv4 of the server hosting GLaDOS",
				Permission.ADMINISTRATOR,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList());
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		// TODO
	}
}
