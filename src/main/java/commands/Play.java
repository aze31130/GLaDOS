package commands;

import accounts.Permissions;

public class Play extends Command {
	public Play(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {

	}
}
