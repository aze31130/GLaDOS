package commands;

import utils.BuildEmbed;
import utils.Logger;
import utils.Permission;

public class Shutdown extends Command {
	public Shutdown(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(Permission.permissionLevel(args.member, 2)){
			System.out.println(new Logger(true) + " Shutting down now !");
			args.channel.sendMessage("Shutting down now !").queue();
			args.member.getJDA().shutdown();
			System.exit(0);
		} else {
			args.channel.sendMessage(BuildEmbed.errorPermissionEmbed(2).build()).queue();
		}
	}
}
