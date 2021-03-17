package aze.GLaDOS.Events;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {
	
	String[] messages = {
			"[member] is here ! Now it's time to raise our hands !",
			"[member] just arrived ! Hide the vodka quickly !",
			"Big [member] is here !",
			"[member] joined the game.",
			"[member] just did a barrel roll"
	};
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0x66d8ff);
		join.setDescription(messages[randomNumber].replace("[member]", event.toString()));
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
	}
	
	public void onGuildJoin(GuildJoinEvent event) {
		System.out.println("Test 1");
	}
}
