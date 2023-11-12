package commands;

import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.time.Instant;
import java.util.List;

import accounts.Permissions;

public class Ping extends Command {
	public Ping(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
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
