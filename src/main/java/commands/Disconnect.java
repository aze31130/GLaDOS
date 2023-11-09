package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.List;

import accounts.Permissions;

public class Disconnect extends Command {
	public Disconnect(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		AudioManager audioManager = event.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
	}
}
