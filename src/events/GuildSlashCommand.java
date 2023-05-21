package events;

import main.GLaDOS;
import commands.Argument;
import commands.Command;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class GuildSlashCommand extends ListenerAdapter {
	public void onSlashCommand(SlashCommandEvent event) {
		for(Command command : GLaDOS.getInstance().commands) {
			if(event.getName().equalsIgnoreCase(command.name) || event.getName().equalsIgnoreCase(command.alias)) {
				GLaDOS glados = GLaDOS.getInstance();
				String[] arguments = new String[event.getOptions().size()];
				int i = 0;
				for(OptionMapping om : event.getOptions()) {
					arguments[i] = om.getAsString();
					i++;
				}

				event.reply("200 OK").queue();
				command.execute(new Argument(glados.getAccount(event.getMember().getId()), event.getMember(), event.getTextChannel(), arguments, null));
				break;
			}
		}
	}
}
