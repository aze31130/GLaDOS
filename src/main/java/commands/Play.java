package commands;

import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.BuildEmbed;

public class Play extends Command {
	public Play() {
		super(
				"play",
				"Play a youtube music in channel [WIP]",
				Permission.NONE,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		try {
			VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
			AudioManager audioManager = event.getGuild().getAudioManager();

			// TODO use lavaplayer
			AudioSendHandler test = null;

			audioManager.setSendingHandler(test);
			audioManager.openAudioConnection(voiceChannel);

			event.getHook().sendMessage("200 OK").queue();
		} catch (IllegalArgumentException e) {
			event.getHook().sendMessageEmbeds(
					BuildEmbed.errorEmbed(e.toString() + "You need to be connected in a voice channel first !").build()).queue();
		}
	}
}
