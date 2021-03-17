package aze.GLaDOS.Commands;

public class Command {
	public String name;
	public String description;
	public int permission;
	
	public Command(String name, String description, int permission) {
		this.name = name;
		this.description = description;
		this.permission = permission;
	}
}
