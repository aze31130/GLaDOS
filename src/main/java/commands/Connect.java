package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Connect extends Command {
	public Connect(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		// try {
		// VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
		// AudioManager audioManager = event.getGuild().getAudioManager();
		// AudioSendHandler test = null;
		// audioManager.setSendingHandler(test);
		// audioManager.openAudioConnection(voiceChannel);
		// } catch (IllegalArgumentException e) {
		// event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString() + "You need
		// to be connected in a voice channel first !").build()).queue();
		// }
	}
}
