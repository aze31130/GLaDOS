package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.time.Instant;
import java.util.Arrays;

import accounts.Permissions;

public class Ping extends Command {
	public Ping() {
		super("ping", "Display ping between Discord gateway and glados",
				Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();

		EmbedBuilder ping = new EmbedBuilder().setColor(0x22ff2a)
				.setTitle("Ping: " + event.getJDA().getGatewayPing() + "ms")
				.setThumbnail(event.getJDA().getGuildById("676731153444765706").getIconUrl())
				.setTimestamp(Instant.now());
		source.sendMessageEmbeds(ping.build()).queue();
	}
}
