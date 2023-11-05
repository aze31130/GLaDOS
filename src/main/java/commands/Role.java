package commands;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Role extends Command {
	public Role(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		// Kills the jvm
		System.exit(0);
	}
}
