package commands;

public abstract class Command {
	public String name;
	public String alias;
	public String description;
	public String example;
	public Boolean hidden;
	public int permissionLevel;

	public Command(String name, String description, int permissionLevel) {
		this.name = name;
		this.description = description;
		this.permissionLevel = permissionLevel;
	}

	public abstract void execute(Argument arguments);
}
