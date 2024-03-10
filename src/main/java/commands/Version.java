package commands;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.util.Arrays;
import com.sun.management.OperatingSystemMXBean;
import glados.GLaDOS;
import utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import accounts.Permission;

public class Version extends Command {
	public Version() {
		super(
				"version",
				"Displays version alongside others indicators",
				Permission.NONE,
				Tag.SYSTEM,
				Arrays.asList());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		EmbedBuilder info = new EmbedBuilder()
				.setColor(0x593695)
				.setTitle("GLaDOS")
				.setUrl("https://github.com/aze31130/GLaDOS/tree/" + glados.version)
				.setDescription("Miscellaneous informations")
				.addField("Git commit: ", glados.version, true)
				.addField("CPU usage: ", os.getProcessCpuLoad() * 100 + "%", true)
				.addField("OS: ", System.getProperty("os.name") + " " + System.getProperty("os.version") + " ("
						+ System.getProperty("os.arch") + ")", true)
				.addField("Java version: ", System.getProperty("java.runtime.version"), true)
				.addField("Java class version: ", System.getProperty("java.class.version"), true)
				.addField("JVM's version: ", System.getProperty("java.vm.version"), true)
				.addField("JVM's available memory: ",
						Runtime.getRuntime().freeMemory() + " ("
								+ Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime().totalMemory() + "% free)",
						true)
				.addField("JVM's total memory: ", "" + Runtime.getRuntime().totalMemory(), true)
				.addField("Available threads: ", "" + Runtime.getRuntime().availableProcessors(), true)
				.addField("Event registered: ", Integer.toString(glados.requestsAmount), true)
				.addField("Uptime: ", TimeUtils.TimeConverter(ManagementFactory.getRuntimeMXBean().getUptime() / 1000), true)
				.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(info.build()).queue();
	}
}
