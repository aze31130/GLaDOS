package commands;

import net.dv8tion.jda.api.managers.AudioManager;

import accounts.Permissions;

public class Disconnect extends Command {
	public Disconnect(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument arguments) {
		// AudioManager audioManager = event.getGuild().getAudioManager();
		// audioManager.closeAudioConnection();
	}

}
