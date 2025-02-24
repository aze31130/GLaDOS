package commands;

import java.util.List;

import accounts.Permission;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.Logging;
import net.dv8tion.jda.api.interactions.commands.Command.Type;

public abstract class Command implements Logging {
	public String name;
	public String description;
	public Permission permissionLevel;
	public Tag tag;
	public List<Type> type;
	public List<OptionData> arguments;

	public Command(String name, String description, Permission permissionLevel, Tag tag, List<Type> type,
			List<OptionData> arguments) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
		this.arguments = arguments;
		this.tag = tag;
		this.type = type;
	}

	public abstract void executeSlash(SlashCommandInteractionEvent event) throws Exception;

	public abstract void executeContextUser(UserContextInteractionEvent event) throws Exception;

	public abstract void executeContextMessage(MessageContextInteractionEvent event) throws Exception;
}
