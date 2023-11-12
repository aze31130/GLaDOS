package commands;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import utils.BuildEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class Profile extends Command {
	public Profile(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		EmbedBuilder profile = BuildEmbed.profileEmbed(event.getMember());
		/*
		 * TODO, Possible argument to check others profile. If empty, get user profile Check if
		 * profile exists, Create if not exists
		 */


		event.getChannel().sendMessageEmbeds(profile.build()).queue();


		// if(args.arguments.length == 0) {
		// if(args.account != null) {
		// profile = getProfile(args.account,
		// args.channel.getGuild().getMemberById(args.account.id));
		// }
		// } else {
		// //special account: server
		// if(args.arguments[0].equalsIgnoreCase("server")) {
		// profile = serverProfile(args.channel.getGuild());
		// } else {
		// profile = getProfile(GLaDOS.getInstance().getAccount(args.arguments[0]),
		// args.channel.getGuild().getMemberById(args.arguments[0]));
		// }
		// }

		// //:wave::eye::eyes::tickets::ticket::ring::crown::mortar_board::briefcase::full_moon::boom::zap::rainbow::star::rose::droplet::popcorn::beer::soccer::game_die::video_game::slot_machine::drum::mobile_phone::computer::desktop::dvd::floppy_disk::coin::crossed_swords::notepad_spiral::paperclip::tada::mega::black_joker::spades::clubs::hearts::diamonds::triangular_flag_on_post::magic_wand::robot::comet::fire::trident::recycle::infinity:

		// if(args.account == null) {
		// args.channel.sendMessage(BuildEmbed.errorEmbed("Sorry, this account does not
		// exist !").build()).queue();
		// } else {
		// args.channel.sendMessage(profile.build()).queue();
		// }
		// } catch(Exception e) {
		// args.channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		// }
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
