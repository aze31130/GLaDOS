package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import java.util.Arrays;

import accounts.Permission;

public class Move extends Command {
	public Move() {
		super(
				"move",
				"Move every voice connected users to another channel",
				Permission.MODERATOR,
				Tag.SYSTEM,
				Arrays.asList(
						new OptionData(OptionType.CHANNEL, "destination", "Channel you want to move in", true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		AudioChannel destination = event.getOption("destination").getAsChannel().asAudioChannel();

		int userAmount = event.getMember().getVoiceState().getChannel().getMembers().size();

		VoiceChannel dest = event.getJDA().getVoiceChannelById(destination.getId());
		for (Member m : event.getMember().getVoiceState().getChannel().getMembers())
			event.getMember().getGuild().moveVoiceMember(m, dest).queue();
		event.getHook()
				.sendMessageEmbeds(BuildEmbed.successEmbed("Moved " + userAmount + " members to " + dest.getAsMention()).build())
				.queue();
	}
}
