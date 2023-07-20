package commands;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import utils.BuildEmbed;

public class Connect extends Command {

    public Connect(String name, String alias, String description, String example, Boolean hidden, int permissionLevel) {
        super(name, alias, description, example, hidden, permissionLevel);
    }

    @Override
    public void execute(Argument arguments) {
        // try {
		// 	VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
		// 	AudioManager audioManager = event.getGuild().getAudioManager();
		// 	AudioSendHandler test = null;
		// 	audioManager.setSendingHandler(test);
		// 	audioManager.openAudioConnection(voiceChannel);
		// } catch (IllegalArgumentException e) {
		// 	event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString() + "You need to be connected in a voice channel first !").build()).queue();
		// }
    }
}
