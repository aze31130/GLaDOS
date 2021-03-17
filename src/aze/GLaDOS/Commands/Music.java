package aze.GLaDOS.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Music {
	public static void connect(GuildMessageReceivedEvent event){
		try {
			VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
			AudioManager audioManager = event.getGuild().getAudioManager();
			AudioSendHandler test = null;
			audioManager.setSendingHandler(test);
			audioManager.openAudioConnection(voiceChannel);
			
		} catch (IllegalArgumentException e) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to be connected in a voice channel first !");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void disconnect(GuildMessageReceivedEvent event){
		AudioManager audioManager = event.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
	}
}
