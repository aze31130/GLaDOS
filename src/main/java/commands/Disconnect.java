package commands;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Disconnect extends Command {
	public Disconnect(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument arguments) {
		// AudioManager audioManager = event.getGuild().getAudioManager();
		// audioManager.closeAudioConnection();
	}

}
