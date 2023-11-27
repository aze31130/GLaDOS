package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permissions;
import glados.GLaDOS;

public class Help extends Command {
	public Help() {
		super("help", "Shows an help page listing each commands",
				Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		EmbedBuilder info = new EmbedBuilder();
		GLaDOS g = GLaDOS.getInstance();

		info.setColor(Color.ORANGE);
		info.setTitle("Help page");
		info.setDescription("Showing help page 1 of 1");

		for (Command c : g.commands)
			info.addField(c.name, c.description, true);

		info.setTimestamp(Instant.now());
		source.sendMessageEmbeds(info.build()).queue();
	}
}
