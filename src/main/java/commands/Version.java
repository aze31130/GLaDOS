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
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0x593695);
		info.setTitle("GLaDOS");
		info.setUrl("https://github.com/aze31130/GLaDOS/tree/" + glados.version);
		info.setDescription("Miscellaneous informations");
		info.addField("Git commit: ", glados.version, true);
		info.addField("CPU usage: ", os.getProcessCpuLoad() * 100 + "%", true);
		info.addField("OS: ", System.getProperty("os.name") + " " + System.getProperty("os.version") + " ("
				+ System.getProperty("os.arch") + ")", true);
		info.addField("Java version: ", System.getProperty("java.runtime.version"), true);
		info.addField("Java class version: ", System.getProperty("java.class.version"), true);
		info.addField("JVM's version: ", System.getProperty("java.vm.version"), true);
		info.addField("JVM's available memory: ",
				Runtime.getRuntime().freeMemory() + " ("
						+ Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime().totalMemory() + "% free)",
				true);
		info.addField("JVM's total memory: ", "" + Runtime.getRuntime().totalMemory(), true);
		info.addField("Available threads: ", "" + Runtime.getRuntime().availableProcessors(), true);
		info.addField("Temp file path: ", System.getProperty("java.io.tmpdir"), true);
		info.addField("Event registered: ", Integer.toString(glados.requestsAmount), true);
		info.addField("Uptime: ", TimeUtils.TimeConverter(ManagementFactory.getRuntimeMXBean().getUptime() / 1000), true);
		info.setTimestamp(Instant.now());
		event.getHook().sendMessageEmbeds(info.build()).queue();
	}
}
