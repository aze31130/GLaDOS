package commands;

import java.awt.Color;
import java.util.List;
import glados.GLaDOS;
import utils.BuildEmbed;
import utils.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Clear extends Command {
	public Clear(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(Permission.permissionLevel(args.member, 2)) {
			GLaDOS glados = GLaDOS.getInstance();
			if(args.arguments.length > 0){
				try {
					List<Message> messages = args.channel.getHistory().retrievePast(Integer.parseInt(args.arguments[0])).complete();
					args.channel.deleteMessages(messages).queue();
					EmbedBuilder success = new EmbedBuilder()
							.setColor(Color.GREEN)
							.setTitle("Successfully deleted " + args.arguments[0] + " messages.");
					args.channel.sendMessage(success.build()).queue();
				} catch(Exception exception) {
					args.channel.sendMessage(BuildEmbed.errorEmbed(exception.toString()).build()).queue();
				}
			} else {
				args.channel.sendMessage(BuildEmbed.errorEmbed("Usage: " + glados.prefix + "clear [1-100]").build()).queue();
			}
		} else {
			args.channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
}
