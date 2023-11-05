package commands;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/*
 * This command is designed to test new features
 */
public class Test extends Command {
	public Test(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {}
}
