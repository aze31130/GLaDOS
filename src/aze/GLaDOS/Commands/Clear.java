package aze.GLaDOS.commands;

import aze.GLaDOS.GLaDOS;
import aze.GLaDOS.Constants.Permissions;
import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.utils.BuildEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class Clear extends Command {
	public Clear(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Account account, TextChannel channel, String[] arguments) {
		GLaDOS glados = GLaDOS.getInstance();
		if(glados.leveling) {
			if(account.permission.level >= Permissions.MOD.level) {
				channel.sendMessage("Not implemented yet !").queue();
			} else {
				channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
			}
		} else {
			channel.sendMessage("Cannot call auth system. Aborting command !").queue();
		}
	}
}
