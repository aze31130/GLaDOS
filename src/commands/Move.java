package commands;

import constants.Constants.Permissions;
import utils.BuildEmbed;
import utils.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class Move extends Command {
	
	public Move(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(Permission.permissionLevel(args.account, args.member, Permissions.ADMIN.level)) {
			try {
				VoiceChannel dest = args.member.getJDA().getVoiceChannelById(args.arguments[0]);
				for(Member m : args.member.getVoiceState().getChannel().getMembers()) {
					args.member.getGuild().moveVoiceMember(m, dest).queue();
				}
			} catch(Exception e) {
				args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		} else {
			args.channel.sendMessage(BuildEmbed.errorEmbed("You need to have the Administrator role in order to execute that.").build()).queue();
		}
	}
}
