package commands;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.Arrays;

import accounts.Permission;

public class Disconnect extends Command {
	public Disconnect() {
		super(
				"disconnect",
				"Disconnects GLaDOS from a vocal channel",
				Permission.MODERATOR,
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
		AudioManager audioManager = event.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
		event.getHook().sendMessage("200 OK").queue();
	}
}
