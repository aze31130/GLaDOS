package utils;

public class Converter {
	public static String TimeConverter(long seconds) {

		String upTime = "";
		int[] TimeTable = {0, 0, 0, 0, 0};

		// Add Weeks
		while (seconds >= 604800) {
			TimeTable[0]++;
			seconds -= 604800;
		}

		// Add Days
		while (seconds >= 86400) {
			TimeTable[1]++;
			seconds -= 86400;
		}

		// Add Hours
		while (seconds >= 3600) {
			TimeTable[2]++;
			seconds -= 3600;
		}

		// Add Minutes
		while (seconds >= 60) {
			TimeTable[3]++;
			seconds -= 60;
		}

		// Add Seconds
		while (seconds >= 0) {
			TimeTable[4]++;
			seconds -= 1;
		}

		if (TimeTable[0] != 0) {
			upTime += TimeTable[0] + " week(s), ";
		}

		if (TimeTable[1] != 0) {
			upTime += TimeTable[1] + " day(s), ";
		}

		if (TimeTable[2] != 0) {
			upTime += TimeTable[2] + " hour(s), ";
		}

		if (TimeTable[3] != 0) {
			upTime += TimeTable[3] + " minute(s), ";
		}

		if (TimeTable[4] != 0) {
			upTime += TimeTable[4] + " second(s)";
		}

		return upTime;
	}
}
