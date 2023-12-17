package commands;

import java.util.Arrays;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Account;
import accounts.Permissions;
import glados.GLaDOS;

public class Profile extends Command {
	public Profile() {
		super("profile", "Show a user profile", Permissions.NONE, Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		Member author = event.getMember();
		Account a = glados.getAccount(author);

		EmbedBuilder profile = BuildEmbed.profileEmbed(a);

		event.getChannel().sendMessageEmbeds(profile.build()).queue();
		// //:wave::eye::eyes::tickets::ticket::ring::crown::mortar_board::briefcase::full_moon::boom::zap::rainbow::star::rose::droplet::popcorn::beer::soccer::game_die::video_game::slot_machine::drum::mobile_phone::computer::desktop::dvd::floppy_disk::coin::crossed_swords::notepad_spiral::paperclip::tada::mega::black_joker::spades::clubs::hearts::diamonds::triangular_flag_on_post::magic_wand::robot::comet::fire::trident::recycle::infinity:
	}
}
