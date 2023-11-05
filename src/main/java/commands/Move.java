package commands;

import utils.BuildEmbed;
import utils.PermissionsUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Move extends Command {
	public Move(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		try {
			VoiceChannel dest = args.member.getJDA().getVoiceChannelById(args.arguments[0]);
			for (Member m : args.member.getVoiceState().getChannel().getMembers()) {
				args.member.getGuild().moveVoiceMember(m, dest).queue();
			}
		} catch (Exception e) {
			args.channel.sendMessageEmbeds(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
}
