package aze.GLaDOS.commands;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.sun.management.OperatingSystemMXBean;
import aze.GLaDOS.GLaDOS;
import aze.GLaDOS.accounts.Account;
import aze.GLaDOS.accounts.Levels;
import aze.GLaDOS.utils.BuildEmbed;
import aze.GLaDOS.utils.Converter;
import aze.GLaDOS.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Info {
	public static void account(GuildMessageReceivedEvent event){
		//Get the account
		try {
			Account a = GLaDOS.getInstance().getAccount(event.getAuthor().getId());
			EmbedBuilder info = new EmbedBuilder();
			info.setColor(Color.GREEN);
			info.setAuthor(event.getMember().getNickname(), "https://discord.com/channels/676731153444765706/", event.getAuthor().getAvatarUrl());
			info.setThumbnail(event.getAuthor().getAvatarUrl());
			info.setDescription(event.getAuthor().getAsMention());
			info.addField(":sparkles: Level:", String.valueOf(a.level), true);
			info.addField(":green_book: EXP " + Levels.getExperiencePercentage(a.level, a.experience), String.valueOf(a.experience) + " / " + Levels.getRequiredExperience(a.level), true);
			info.addField(":shield: Rank : ", a.rank.name, true);
			info.addField(":clock: Created : ", a.created, true);
			info.addField(":timer: Member since:", a.joined, true);
			info.addField(":wave: Fame:", String.valueOf(a.fame), false);
			info.addField(":envelope: Messages:", "0", false);
			info.addField(":green_square: Experience:", String.valueOf(a.totalExperience), false);
			info.addField(":trophy: Achievements:", "null", false);
			//:wave::eye::eyes::tickets::ticket::ring::crown::mortar_board::briefcase::full_moon::boom::zap::rainbow::star::rose::droplet::popcorn::beer::soccer::game_die::video_game::slot_machine::drum::mobile_phone::computer::desktop::dvd::floppy_disk::coin::crossed_swords::notepad_spiral::paperclip::tada::mega::black_joker::spades::clubs::hearts::diamonds::triangular_flag_on_post::magic_wand::robot::comet::fire::trident::recycle::infinity:
			info.setFooter(new Logger(false).toString());
			event.getChannel().sendMessage(info.build()).queue();
		} catch(Exception e) {
			event.getChannel().sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
		}
	}
	
	public static void serverInfo(GuildMessageReceivedEvent event, GLaDOS glados){
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0x14db17);
		info.setAuthor("GlaDOS");
		info.setTitle("GLaDOS info");
		info.setDescription("Server information for " + event.getGuild().getName());
		info.setThumbnail(event.getGuild().getIconUrl());
		info.addField("Server created on: ", event.getGuild().getTimeCreated().toString(), true);
		info.addField("Server member counts: ", Integer.toString(event.getGuild().getMemberCount()), true);
		info.addField("Server owner: ", event.getGuild().getOwner().getAsMention(), true);
		//info.addField("Server total level: ", "1", true);
		//info.addField("Server total experience: ", "0", true);
		//info.addField("Total message sent: ", "UNKNOWN", true);
		//info.addField("Total experience obtained: ", "UNKNOWN", true);
		info.addField("Command prefix: ", glados.prefix, true);
		info.setFooter("Request made at " + formatter.format(date));
		event.getChannel().sendMessage(info.build()).queue();
	}
	
	public static void ping(GuildMessageReceivedEvent event){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
		EmbedBuilder ping = new EmbedBuilder();
		ping.setColor(0x22ff2a);
		ping.setTitle("Ping: " + event.getJDA().getGatewayPing() + "ms");
		ping.setThumbnail(event.getGuild().getIconUrl());
		ping.setFooter("Request made at " + formatter.format(date));
		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage(ping.build()).queue();
	}
	
	public static void help(GuildMessageReceivedEvent event, GLaDOS glados){
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0xff3923);
		info.setTitle("Help page");
		info.setDescription("Showing help page 1 of 1");
		info.addField(glados.prefix + "help", "(Seriously ?)", true);
		info.addField(glados.prefix + "clear <2-100>", "[Requires mod+ role]", true);
		info.addField(glados.prefix + "random-meme | rm", "(Gives you a random joke)", true);
		info.addField(glados.prefix + "rng <number>", "(Gives a random number between 1 to (2^31)-1)", true);
		info.addField(glados.prefix + "state | st", "(Sets the state of GLaDOS)", true);
		info.addField(glados.prefix + "activity | at", "(Sets the activity of GLaDOS)", true);
		info.addField(glados.prefix + "picture-inverse | pi", "(Currently in beta version)", true);
		info.addField(glados.prefix + "fibonacci | fibo <number>", "Calculates Fibonacci for almost any numbers (in less of 3 seconds)", true);
		//info.addField(Main.commandPrefix + "connect", "(Coming soon)", true);
		//info.addField(Main.commandPrefix + "disconnect", "(Coming soon)", true);
		info.addField(glados.prefix + "count-messages | cm", "[Requires Admin role TEMPORARLY]", true);
		info.addField(glados.prefix + "ping", "(Displays the latency between Discord and GLaDOS)", true);
		info.addField(glados.prefix + "che-guevara | cg", "(Shows a random quote info)", true);
		info.addField(glados.prefix + "account-info", "(Shows account info)", true);
		info.addField(glados.prefix + "server-info", "(Shows server info)", true);
		info.addField(glados.prefix + "shutdown", "[Requires admin role]", true);
		info.addField(glados.prefix + "what-should-i-do | wsid", "(Shows something to do)", true);
		info.addField(glados.prefix + "random-dog | rd", "(Shows a random dog picture)", true);
		info.addField(glados.prefix + "version | v", "(Shows version info)", true);
		event.getChannel().sendMessage(info.build()).queue();
	}
	
	public static void version(GuildMessageReceivedEvent event){
		GLaDOS glados = GLaDOS.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0x593695);
		info.setTitle("GLaDOS info");
		info.setDescription("GLaDOS is running on version: " + glados.version);
		info.addField("OS name: ", System.getProperty("os.name"), true);
		info.addField("OS version: ", System.getProperty("os.version"), true);
		info.addField("OS architecture: ", System.getProperty("os.arch"), true);
		info.addField("CPU usage: ", os.getProcessCpuLoad() * 100 + "%", true);
		info.addField("Java version: ", System.getProperty("java.runtime.version"), true);
		info.addField("Java class version: ", System.getProperty("java.class.version"), true);
		info.addField("JVM's version: ", System.getProperty("java.vm.version"), true);
		info.addField("JVM's available memory: ", Runtime.getRuntime().freeMemory() + " (" + Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime().totalMemory() + "% free)", true);
		info.addField("JVM's total memory: ", "" + Runtime.getRuntime().totalMemory(), true);
		info.addField("Available threads: ", "" + Runtime.getRuntime().availableProcessors(), true);
		info.addField("Temp file path: ", System.getProperty("java.io.tmpdir"), true);
		info.addField("Request amount: ", Integer.toString(glados.getRequests()), true);
		info.addField("Uptime: ", Converter.TimeConverter(ManagementFactory.getRuntimeMXBean().getUptime()/1000), true);
		info.setFooter("Request made at " + formatter.format(date));
		event.getChannel().sendMessage(info.build()).queue();
	}
}
