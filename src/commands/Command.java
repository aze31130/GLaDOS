package commands;

public abstract class Command {
	public String name;
	public String alias;
	public String description;
	public String example;
	public Boolean hidden;
	public int permissionLevel;
	
	public Command(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.example = example;
		this.hidden = hidden;
		this.permissionLevel = permissionLevel;
	}
	
	public abstract void execute(Argument arguments);
}
