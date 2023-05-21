package commands;

import java.util.List;

import utils.EmoteCounter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;

public class Stats {
	
	public static void ListChannelMessage(TextChannel channel, List<EmoteCounter> emotes) {
		channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
			ReactPerMessage(me, emotes);
			return true;
		});
	}
	
	public static void ReactPerMessage(Message m, List<EmoteCounter> emotes) {
		System.out.println(m.getContentRaw() + " # " + m.getJumpUrl());
			for(MessageReaction mr : m.getReactions()) {
				
				if(!isInEmote(emotes, mr.getReactionEmote().getName())) {
					emotes.add(new EmoteCounter(mr.getReactionEmote().getName()));
				}
				
				addEmote(emotes, mr.getReactionEmote().getName(), mr.getCount());
			}
	}
	
	public static Boolean isInEmote(List<EmoteCounter> emotes, String emote) {
		for(EmoteCounter ec : emotes) {
			if(ec.name.equals(emote)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addEmote(List<EmoteCounter> emotes, String emote, int amount) {
		for(EmoteCounter ec : emotes) {
			if(ec.name.equals(emote)) {
				ec.add(amount);
			}
		}
	}
	
	
	
	
	//The Global Elite
	//Grand Master
	//Most Valuable Person
	//Very Important Person
	//Elder
	public static void Rank(TextChannel channel) {
		
	}
	
	public int MemberAmount(Boolean includeBot) {
		//int guildMembers = event.getGuild().getMembers().stream().filter(member -> !member.getUser().isBot()).count();
		//int guildBots = event.getGuild().getMembers().stream().filter(member -> member.getUser().isBot()).count();
		return 0;
	}
}
