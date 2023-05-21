package commands;

import java.util.List;

import javax.annotation.Nullable;

import accounts.Account;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Argument {
	public Account account;
	public Member member;
	public TextChannel channel;
	public String[] arguments;
	public List<Attachment> list;
	
	public Argument(@Nullable Account account, Member member, TextChannel channel, String[] arguments, List<Attachment> list) {
		this.account = account;
		this.member = member;
		this.channel = channel;
		this.arguments = arguments;
		this.list = list;
	}
}
