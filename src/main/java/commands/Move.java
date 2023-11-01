package commands;

import utils.BuildEmbed;
import utils.PermissionsUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import accounts.Permissions;

public class Move extends Command {
	public Move(String name, String description, Permissions permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		if (PermissionsUtils.permissionLevel(args.member, 2)) {
			try {
				VoiceChannel dest = args.member.getJDA().getVoiceChannelById(args.arguments[0]);
				for (Member m : args.member.getVoiceState().getChannel().getMembers()) {
					args.member.getGuild().moveVoiceMember(m, dest).queue();
				}
			} catch (Exception e) {
				args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
			}
		} else {
			args.channel
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("You need to have the Administrator role in order to execute that.").build())
					.queue();
		}
	}
}
