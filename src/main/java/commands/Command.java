package commands;

import java.util.List;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class Command {
	public String name;
	public String description;
	public Permission permissionLevel;
	public List<OptionData> arguments;

	public Command(String name, String description, Permission permissionLevel,
			List<OptionData> arguments) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
		this.arguments = arguments;
	}

	public abstract void execute(SlashCommandInteractionEvent event);
}
