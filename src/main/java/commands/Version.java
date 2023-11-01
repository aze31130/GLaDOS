package commands;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import glados.GLaDOS;
import utils.Converter;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;

public class Version extends Command {
	public Version(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
		GLaDOS glados = GLaDOS.getInstance();
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
		info.addField("JVM's available memory: ",
				Runtime.getRuntime().freeMemory() + " ("
						+ Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime().totalMemory() + "% free)",
				true);
		info.addField("JVM's total memory: ", "" + Runtime.getRuntime().totalMemory(), true);
		info.addField("Available threads: ", "" + Runtime.getRuntime().availableProcessors(), true);
		info.addField("Temp file path: ", System.getProperty("java.io.tmpdir"), true);
		info.addField("Request amount: ", Integer.toString(glados.getRequests()), true);
		info.addField("Uptime: ", Converter.TimeConverter(ManagementFactory.getRuntimeMXBean().getUptime() / 1000),
				true);
		info.setFooter("Request made at " + new Logger(false));
		args.channel.sendMessageEmbeds(info.build()).queue();
	}
}
