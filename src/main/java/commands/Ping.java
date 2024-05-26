package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;

import accounts.Permission;

public class Ping extends Command {
	public Ping() {
		super(
				"ping",
				"Display ping between Discord gateway and glados",
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
		EmbedBuilder ping = new EmbedBuilder()
				.setColor(Color.green)
				.setTitle("Ping: " + event.getJDA().getGatewayPing() + "ms")
				.setThumbnail(event.getJDA().getGuildById("676731153444765706").getIconUrl())
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(ping.build()).queue();
	}
}
