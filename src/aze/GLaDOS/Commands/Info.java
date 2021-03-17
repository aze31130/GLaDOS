package aze.GLaDOS.Commands;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.management.OperatingSystemMXBean;

import aze.GLaDOS.Constants;
import aze.GLaDOS.Main;
import aze.GLaDOS.Utils.Converter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Info {
	public static void accountInfo(GuildMessageReceivedEvent event){
		String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(Color.GREEN);
		info.setTitle(event.getAuthor().getName() + "'s profile");
		info.setThumbnail(event.getAuthor().getAvatarUrl());
		//info.setDescription("Account information for " + event.getAuthor().getAsTag() + " known as " + event.getAuthor().getAsMention());
		info.addField("Creation date:", event.getAuthor().getTimeCreated().toString(), true);
		info.addField("Member since:", event.getMember().getTimeJoined().toString(), true);
		info.addField("Rank:", "UNKOWN", true);
		info.addField("Level:", "1", true);
		info.addField("Experience: [0%]", "0 / 15", true);
		info.addField("Total message sent:", "UNKOWN", true);
		info.addField("Total experience obtained:", "UNKNOWN", true);
		info.addField("Achievements:", "", true);
		info.setFooter("Request made at " + time);
		event.getChannel().sendMessage(info.build()).queue();
	}
	
	public static void serverInfo(GuildMessageReceivedEvent event){
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
		info.addField("Command prefix: ", Constants.commandPrefix, true);
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
	
	public static void help(GuildMessageReceivedEvent event){
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0xff3923);
		info.setTitle("Help page");
		info.setDescription("Showing help page 1 of 1");
		info.addField(Constants.commandPrefix + "help", "(Seriously ?)", true);
		info.addField(Constants.commandPrefix + "clear <2-100>", "[Requires mod+ role]", true);
		info.addField(Constants.commandPrefix + "random-meme | rm", "(Gives you a random joke)", true);
		info.addField(Constants.commandPrefix + "rng <number>", "(Gives a random number between 1 to (2^31)-1)", true);
		info.addField(Constants.commandPrefix + "state | st", "(Sets the state of GLaDOS)", true);
		info.addField(Constants.commandPrefix + "activity | at", "(Sets the activity of GLaDOS)", true);
		info.addField(Constants.commandPrefix + "picture-inverse | pi", "(Currently in beta version)", true);
		info.addField(Constants.commandPrefix + "fibonacci | fibo <number>", "Calculates Fibonacci for almost any numbers (in less of 3 seconds)", true);
		//info.addField(Main.commandPrefix + "connect", "(Coming soon)", true);
		//info.addField(Main.commandPrefix + "disconnect", "(Coming soon)", true);
		info.addField(Constants.commandPrefix + "count-messages | cm", "[Requires Admin role TEMPORARLY]", true);
		info.addField(Constants.commandPrefix + "ping", "(Displays the latency between Discord and GLaDOS)", true);
		info.addField(Constants.commandPrefix + "che-guevara | cg", "(Shows a random quote info)", true);
		info.addField(Constants.commandPrefix + "account-info", "(Shows account info)", true);
		info.addField(Constants.commandPrefix + "server-info", "(Shows server info)", true);
		info.addField(Constants.commandPrefix + "shutdown", "[Requires admin role]", true);
		info.addField(Constants.commandPrefix + "what-should-i-do | wsid", "(Shows something to do)", true);
		info.addField(Constants.commandPrefix + "random-dog | rd", "(Shows a random dog picture)", true);
		info.addField(Constants.commandPrefix + "version | v", "(Shows version info)", true);
		event.getChannel().sendMessage(info.build()).queue();
	}
	
	public static void version(GuildMessageReceivedEvent event){
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0x593695);
		info.setTitle("GLaDOS info");
		info.setDescription("GLaDOS is running on version: " + Constants.version);
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
		info.addField("Request amount: ", Integer.toString(Main.RequestAmount), true);
		info.addField("Uptime: ", Converter.TimeConverter(ManagementFactory.getRuntimeMXBean().getUptime()/1000), true);
		info.setFooter("Request made at " + formatter.format(date));
		event.getChannel().sendMessage(info.build()).queue();
	}
}
