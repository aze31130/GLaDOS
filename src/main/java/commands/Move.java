package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.Arrays;

import accounts.Permission;

public class Move extends Command {
	public Move() {
		super("move", "Move every voice connected users to another channel",
				Permission.MODERATOR, Arrays.asList(new OptionData(OptionType.CHANNEL,
						"destination", "Channel you want to move in")));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		TextChannel source = event.getChannel().asTextChannel();
		AudioChannel destination = event.getOption("destination").getAsChannel().asAudioChannel();

		VoiceChannel dest = source.getJDA().getVoiceChannelById(destination.getId());
		for (Member m : event.getMember().getVoiceState().getChannel().getMembers()) {
			event.getMember().getGuild().moveVoiceMember(m, dest).queue();
		}
	}
}
