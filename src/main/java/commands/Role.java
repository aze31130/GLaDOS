package commands;

import utils.BuildEmbed;
import utils.PermissionsUtils;

import accounts.Permissions;

public class Role extends Command {
	public Role(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		if (PermissionsUtils.permissionLevel(args.member, 2)) {
			args.channel
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("You need to have the Administrator role in order to execute that.").build())
					.queue();
			return;
		}

		// Kills the jvm
		System.exit(0);
	}
}
