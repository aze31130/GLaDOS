package commands;

import accounts.Permissions;

public abstract class Command {
	public String name;
	public String description;
	public Permissions permissionLevel;

	public Command(String name, String description, Permissions permissionLevel) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
	}

	public abstract void execute(Argument arguments);
}
