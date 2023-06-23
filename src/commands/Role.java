package commands;

import constants.Constants.Permissions;
import utils.BuildEmbed;
import utils.Logger;
import utils.Permission;

public class Role extends Command {
	public Role(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
        if(Permission.permissionLevel(args.account, args.member, Permissions.ADMIN.level)) {
            args.channel.sendMessage(BuildEmbed.errorEmbed("You need to have the Administrator role in order to execute that.").build()).queue();
            return;
        }

        //TODO
	}
}
