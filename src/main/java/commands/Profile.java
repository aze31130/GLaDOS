package commands;

import java.util.Arrays;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import accounts.Account;
import accounts.Permission;
import glados.GLaDOS;

public class Profile extends Command {
	public Profile() {
		super(
				"profile",
				"Show a user profile",
				Permission.NONE,
				Tag.RPG,
				Arrays.asList(Type.SLASH, Type.USER),
				Arrays.asList());
	}

	@Override
	public void executeContextUser(UserContextInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getTarget();
		Account a = glados.getAccount(author);

		EmbedBuilder profile = BuildEmbed.profileEmbed(a, glados);

		event.getHook().sendMessageEmbeds(profile.build()).queue();
	}

	@Override
	public void executeContextMessage(MessageContextInteractionEvent event) {}

	@Override
	public void executeSlash(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		User author = event.getUser();
		Account a = glados.getAccount(author);

		EmbedBuilder profile = BuildEmbed.profileEmbed(a, glados);

		event.getHook().sendMessageEmbeds(profile.build()).queue();
		// //:wave::eye::eyes::tickets::ticket::ring::crown::mortar_board::briefcase::full_moon::boom::zap::rainbow::star::rose::droplet::popcorn::beer::soccer::game_die::video_game::slot_machine::drum::mobile_phone::computer::desktop::dvd::floppy_disk::coin::crossed_swords::notepad_spiral::paperclip::tada::mega::black_joker::spades::clubs::hearts::diamonds::triangular_flag_on_post::magic_wand::robot::comet::fire::trident::recycle::infinity:
	}
}
