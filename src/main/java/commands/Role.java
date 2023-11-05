package commands;

import utils.BuildEmbed;
import utils.PermissionsUtils;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Role extends Command {
	public Role(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		if (PermissionsUtils.permissionLevel(args.member, 2)) {
			args.channel.sendMessageEmbeds(BuildEmbed
					.errorEmbed("You need to have the Administrator role in order to execute that.")
					.build()).queue();
			return;
		}

		// Kills the jvm
		System.exit(0);
	}
}
