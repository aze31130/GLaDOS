package aze.GLaDOS.commands;

import javax.annotation.Nullable;

import aze.GLaDOS.accounts.Account;
import net.dv8tion.jda.api.entities.TextChannel;

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
	
	public abstract void execute(Account account, TextChannel channel, @Nullable String[] arguments);
}
