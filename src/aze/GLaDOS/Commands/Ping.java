package aze.GLaDOS.commands;

import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Ping extends Command {
	
	public Ping(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Account account, TextChannel channel, String[] arguments) {
		EmbedBuilder ping = new EmbedBuilder()
				.setColor(0x22ff2a)
				.setTitle("Ping: " + channel.getJDA().getGatewayPing() + "ms")
				.setThumbnail(channel.getGuild().getIconUrl())
				.setFooter("Request made at " + new Logger(false));
		channel.sendMessage(ping.build()).queue();
	}
}
