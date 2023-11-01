package commands;

import accounts.Permissions;

/*
 * This command is designed to test new features
 */
public class Test extends Command {
	public Test(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
	}
}
