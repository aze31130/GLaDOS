package commands;

import net.dv8tion.jda.api.managers.AudioManager;

public class Disconnect extends Command {

    public Disconnect(String name, String alias, String description, String example, Boolean hidden,
            int permissionLevel) {
        super(name, alias, description, example, hidden, permissionLevel);
    }

    @Override
    public void execute(Argument arguments) {
        // AudioManager audioManager = event.getGuild().getAudioManager();
		// audioManager.closeAudioConnection();
    }
    
}
