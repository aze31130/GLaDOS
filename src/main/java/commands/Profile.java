package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Account;
import accounts.Permissions;
import glados.GLaDOS;

public class Profile extends Command {
	public Profile(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS g = GLaDOS.getInstance();
		Account a = g.getAccountById(event.getMember().getId());

		EmbedBuilder profile = BuildEmbed.profileEmbed(event.getMember());


		event.getChannel().sendMessageEmbeds(profile.build()).queue();

		// //:wave::eye::eyes::tickets::ticket::ring::crown::mortar_board::briefcase::full_moon::boom::zap::rainbow::star::rose::droplet::popcorn::beer::soccer::game_die::video_game::slot_machine::drum::mobile_phone::computer::desktop::dvd::floppy_disk::coin::crossed_swords::notepad_spiral::paperclip::tada::mega::black_joker::spades::clubs::hearts::diamonds::triangular_flag_on_post::magic_wand::robot::comet::fire::trident::recycle::infinity:
	}

	// server total levels, experience, message sent, total experience obtained
	private EmbedBuilder serverProfile(Guild g) {
		EmbedBuilder profile = new EmbedBuilder().setColor(Color.GREEN).setAuthor("GLaDOS")
				.setDescription("Server information for " + g.getName())
				.setThumbnail(g.getIconUrl())
				.addField("Server created on: ", g.getTimeCreated().toString(), true)
				.addField("Server member counts: ", Integer.toString(g.getMemberCount()), true)
				.addField("Server owner: ", g.getOwner().getAsMention(), true)
				.setTimestamp(Instant.now());
		return profile;
	}
}
