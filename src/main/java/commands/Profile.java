package commands;

import java.awt.Color;
import java.util.List;

import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import accounts.Permissions;

public class Profile extends Command {
	public Profile(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
	}

	@Override
	public void execute(Argument args) {
		args.channel.sendMessage("Command not implemented yet !").queue();
		// try {
		// //get argument
		// EmbedBuilder profile = new EmbedBuilder();

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

		// //TODO: create default if not exist

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

	// private EmbedBuilder getProfile(Account a, Member m) {
	// EmbedBuilder profile = new EmbedBuilder()
	// .setColor(Color.GREEN)
	// .setAuthor("OurStory's profile",
	// "https://discord.com/channels/676731153444765706/676731153444765709",
	// "https://cdn.discordapp.com/icons/676731153444765706/078350d911e939323f8a70565a2cfc95.webp")
	// .setThumbnail(m.getUser().getAvatarUrl())
	// .setDescription(m.getAsMention())
	// .addField(":sparkles: Level:", String.valueOf(a.level), true)
	// .addField(":green_book: EXP " + Levels.getExperiencePercentage(a.level,
	// a.experience), String.valueOf(a.experience) + " / " +
	// Levels.getRequiredExperience(a.level), true)
	// .addField(":shield: Rank : ", a.rank.name, true)
	// .addField(":clock: Created : ", a.created, true)
	// .addField(":timer: Member since:", a.joined, true)
	// .addField(":wave: Fame:", String.valueOf(a.fame), true)
	// .addField(":envelope: Messages:", "0", true)
	// .addField(":green_square: Experience:", String.valueOf(a.totalExperience),
	// true)
	// .addField(":trophy: Achievements:", "null", false)
	// .setFooter("Request made at " + new Logger(false));
	// return profile;
	// }

	// server total levels, experience, message sent, total experience obtained
	private EmbedBuilder serverProfile(Guild g) {
		EmbedBuilder profile = new EmbedBuilder()
				.setColor(Color.GREEN)
				.setAuthor("GLaDOS")
				.setDescription("Server information for " + g.getName())
				.setThumbnail(g.getIconUrl())
				.addField("Server created on: ", g.getTimeCreated().toString(), true)
				.addField("Server member counts: ", Integer.toString(g.getMemberCount()), true)
				.addField("Server owner: ", g.getOwner().getAsMention(), true)
				.setFooter("Request made at " + new Logger(false));
		return profile;
	}
}
