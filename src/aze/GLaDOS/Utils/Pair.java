package aze.GLaDOS.utils;

import net.dv8tion.jda.api.entities.User;

public class Pair {
	public User author;
	public int messageAmount;
	
	public Pair(User author, int messageAmount) {
		this.author = author;
		this.messageAmount = messageAmount;
	}
	
	public int getAmount() {
		return messageAmount;
	}
}
