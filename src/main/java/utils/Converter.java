package utils;

public class Converter {
	/*
	 * This function returns GLaDOS's uptime in a readable way
	 */
	public static String TimeConverter(long seconds) {
		StringBuilder uptime = new StringBuilder();
		int[] timeTable = {0, 0, 0, 0, 0};
		int[] durations = {604800, 86400, 3600, 60, 1};
		String[] labels = {"week", "day", "hour", "minute", "second"};

		for (int i = 0; i < durations.length; i++) {
			while (seconds >= durations[i]) {
				timeTable[i]++;
				seconds -= durations[i];
			}
		}

		for (int i = 0; i < timeTable.length; i++) {
			if (timeTable[i] != 0) {
				uptime.append(timeTable[i]).append(" ").append(labels[i]);
				if (timeTable[i] > 1)
					uptime.append("s");
				uptime.append(", ");
			}
		}

		// Remove the trailing comma and space if any
		if (uptime.length() > 0)
			uptime.setLength(uptime.length() - 2);

		return uptime.toString();
	}
}
