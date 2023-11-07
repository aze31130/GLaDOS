package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

import accounts.Permissions;

public class Move extends Command {
	public Move(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		TextChannel destination = event.getOption("dst").getAsChannel().asTextChannel();

		VoiceChannel dest = source.getJDA().getVoiceChannelById(destination.getId());
		for (Member m : event.getMember().getVoiceState().getChannel().getMembers()) {
			event.getMember().getGuild().moveVoiceMember(m, dest).queue();
		}
	}
}
