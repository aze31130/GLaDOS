package commands;

import java.util.Arrays;

import accounts.Permission;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play extends Command {
	public Play() {
		super(
				"play",
				"Play a youtube music in channel [WIP]",
				Permission.NONE,
				Tag.SYSTEM,
				Arrays.asList(Type.SLASH),
				Arrays.asList());
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		try {
			VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
			AudioManager audioManager = event.getGuild().getAudioManager();

			// TODO use lavaplayer
			AudioSendHandler test = null;

			audioManager.setSendingHandler(test);
			audioManager.openAudioConnection(voiceChannel);

			event.getHook().sendMessage("200 OK").queue();
		} catch (IllegalArgumentException e) {
			LOGGER.severe(e.toString());
		}
	}
}
