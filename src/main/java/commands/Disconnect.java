package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.Arrays;

import accounts.Permission;

public class Disconnect extends Command {
	public Disconnect() {
		super("disconnect", "Disconnects GLaDOS from a vocal channel",
				Permission.MODERATOR, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		AudioManager audioManager = event.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
	}
}
