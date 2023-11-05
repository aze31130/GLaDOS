package commands;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class Command {
	public String name;
	public String description;
	public Permissions permissionLevel;
	public List<OptionData> arguments;

	public Command(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
		this.arguments = arguments;
	}

	public abstract void execute(Argument arguments);
}
