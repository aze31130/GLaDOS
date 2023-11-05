package commands;

import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Ping extends Command {
	public Ping(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		EmbedBuilder ping = new EmbedBuilder().setColor(0x22ff2a)
				.setTitle("Ping: " + args.channel.getJDA().getGatewayPing() + "ms")
				.setThumbnail(args.channel.getJDA().getGuildById("676731153444765706").getIconUrl())
				.setFooter("Request made at " + new Logger(false));
		args.channel.sendMessageEmbeds(ping.build()).queue();
	}
}
