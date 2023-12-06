package tasks;

import java.util.Calendar;

public class Backup implements Runnable {
	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();

		// if ((glados.metricLogging && cal.get(Calendar.SECOND) <= 10)) {
		// Log into the database every online account
		// DataLogger.log(jda.getGuildById(glados.guildId).retrieveMetaData().complete()
		// .getApproximatePresences());
		// }
		System.out.println("Backup test task");
	}
}
