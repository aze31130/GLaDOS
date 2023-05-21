package commands;

import utils.BuildEmbed;
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
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString() + "You need to be connected in a voice channel first !").build()).queue();
		}
	}
	
	public static void disconnect(GuildMessageReceivedEvent event){
		AudioManager audioManager = event.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
	}
}
