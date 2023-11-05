package commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Argument {
	public Member member;
	public MessageChannel channel;
	public String[] arguments;
	public List<Attachment> list;

	public Argument(Member member, MessageChannel channel, String[] arguments,
			List<Attachment> list) {
		this.member = member;
		this.channel = channel;
		this.arguments = arguments;
		this.list = list;
	}
}
