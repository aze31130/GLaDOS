package commands;

import utils.BuildEmbed;
import utils.Logger;
import utils.PermissionsUtils;

import java.util.List;

import accounts.Permissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Shutdown extends Command {
	public Shutdown(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		if (PermissionsUtils.permissionLevel(args.member, 2)) {
			System.out.println(new Logger(true) + " Shutting down now !");
			args.channel.sendMessage("Shutting down now !").queue();
			args.member.getJDA().shutdown();
			System.exit(0);
		} else {
			args.channel.sendMessageEmbeds(BuildEmbed.errorPermissionEmbed(2).build()).queue();
		}
	}
}
