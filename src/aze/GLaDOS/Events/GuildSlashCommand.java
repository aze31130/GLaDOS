package aze.GLaDOS.events;

import aze.GLaDOS.GLaDOS;
import aze.GLaDOS.commands.Command;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
//import net.dv8tion.jda.api.interactions.components.Button;

public class GuildSlashCommand extends ListenerAdapter {
	public void onSlashCommand(SlashCommandEvent event) {
		for(Command command : GLaDOS.getInstance().commands) {
			if(event.getName().equalsIgnoreCase(command.name)) {
				GLaDOS glados = GLaDOS.getInstance();
				String[] arguments = new String[event.getOptions().size()];
				int i = 0;
				for(OptionMapping om : event.getOptions()) {
					arguments[i] = om.getAsString();
					i++;
				}
				
				//event.reply("200 OK").queue();
				
				
				event.reply("200 OK").addActionRow(
						Button.primary("AddGamer", "Join Gamerz"),
						Button.danger("RemoveGamer", "Leave Gamerz")).queue();
				
				command.execute(glados.getAccount(event.getMember().getId()), event.getTextChannel(), arguments);
				break;
			}
		}
	}
}
