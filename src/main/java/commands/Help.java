package commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.Logger;
import accounts.Permissions;

public class Help extends Command {
	public Help(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		EmbedBuilder info = new EmbedBuilder();

		info.setColor(Color.ORANGE);
		info.setTitle("Help page");
		info.setDescription("Showing help page 1 of 1");
		info.addField("help", "(Seriously ?)", true);
		info.addField("clear <2-100>", "[Requires mod+ role]", true);
		info.addField("random-meme | rm", "(Gives you a random joke)", true);
		info.addField("rng <number>", "(Gives a random number between 1 to (2^31)-1)", true);
		info.addField("state | st", "(Sets the state of GLaDOS)", true);
		info.addField("activity | at", "(Sets the activity of GLaDOS)", true);
		info.addField("picture-inverse | pi", "(Currently in beta version)", true);
		info.addField("fibonacci | fibo <number>",
				"Calculates Fibonacci for almost any numbers (in less of 3 seconds)", true);
		// info.addField(Main.commandPrefix + "connect", "(Coming soon)", true);
		// info.addField(Main.commandPrefix + "disconnect", "(Coming soon)", true);
		info.addField("ping", "(Displays the latency between Discord and GLaDOS)", true);
		info.addField("che-guevara | cg", "(Shows a random quote info)", true);
		info.addField("account-info", "(Shows account info)", true);
		info.addField("server-info", "(Shows server info)", true);
		info.addField("shutdown", "[Requires admin role]", true);
		info.addField("what-should-i-do | wsid", "(Shows something to do)", true);
		info.addField("random-dog | rd", "(Shows a random dog picture)", true);
		info.addField("version | v", "(Shows version info)", true);
		info.setFooter("Request made at " + new Logger(false));
		source.sendMessageEmbeds(info.build()).queue();
	}
}
