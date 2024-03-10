package commands;

import java.util.List;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class Command {
	public String name;
	public String description;
	public Permission permissionLevel;
	public Tag tag;
	public List<OptionData> arguments;

	public Command(String name, String description, Permission permissionLevel, Tag tag, List<OptionData> arguments) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
		this.arguments = arguments;
		this.tag = tag;
	}

	public abstract void execute(SlashCommandInteractionEvent event) throws Exception;
}
