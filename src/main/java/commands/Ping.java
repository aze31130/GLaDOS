package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		EmbedBuilder ping = new EmbedBuilder()
				.setColor(Color.green)
				.setTitle("Ping: " + event.getJDA().getGatewayPing() + "ms")
				.setThumbnail(event.getJDA().getGuildById("676731153444765706").getIconUrl())
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(ping.build()).queue();
	}
}
