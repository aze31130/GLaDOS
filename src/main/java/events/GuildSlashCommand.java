package events;

import glados.GLaDOS;
import commands.Argument;
import commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class GuildSlashCommand extends ListenerAdapter {
	public void onSlashCommand(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.activityCounter++;
		for (Command command : GLaDOS.getInstance().commands) {
			if (event.getName().equalsIgnoreCase(command.name) || event.getName().equalsIgnoreCase(command.alias)) {
				String[] arguments = new String[event.getOptions().size()];
				int i = 0;
				for (OptionMapping om : event.getOptions()) {
					arguments[i] = om.getAsString();
					i++;
				}

				event.reply("200 OK").queue();

				// event.reply("200 OK").addActionRow(Button.primary("+Broadcast", "Join
				// Broadcast Messenger"), Button.danger("-Broadcast", "Leave Broadcast
				// Messenger")).queue();

				command.execute(new Argument(event.getMember(), event.getChannel(), arguments, null));
				break;
			}
		}
	}
}
