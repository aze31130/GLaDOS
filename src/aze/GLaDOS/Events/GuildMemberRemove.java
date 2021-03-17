package aze.GLaDOS.Events;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRemove extends ListenerAdapter {
	
	String[] messages = {
			"[member] may be gone, but he still lives on in our hearts.",
			"Oh no, we just loose [member].",
			"In this dark day, [member] decided to leave this place.",
			"[member] picked a chance card: Go to jail, do not pass go, do not collect 200$.",
			"[member] left. The party is now over."
	};
	
	public void onGuildMemberRemove(GuildMemberRemoveEvent event){
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0x66d8ff);
		join.setDescription(messages[randomNumber].replace("[member]", event.toString()));
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
	}
}
