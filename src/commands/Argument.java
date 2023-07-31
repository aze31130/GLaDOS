package commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Argument {
	public Member member;
	public TextChannel channel;
	public String[] arguments;
	public List<Attachment> list;
	
	public Argument(Member member, TextChannel channel, String[] arguments, List<Attachment> list) {
		this.member = member;
		this.channel = channel;
		this.arguments = arguments;
		this.list = list;
	}
}
