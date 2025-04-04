package utils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import net.dv8tion.jda.api.entities.Message;

public class TimeUtils {
	/*
	 * This function returns the amount of millis seconds between current time to midnight
	 */
	public static long getMidnightDelay() {
		String timeZone = System.getenv("TZ");
		ZoneId zoneId = (timeZone != null && !timeZone.isEmpty()) ? ZoneId.of(timeZone) : ZoneId.of("UTC");

		ZonedDateTime now = ZonedDateTime.now(zoneId);
		ZonedDateTime midnight = now.toLocalDate().atStartOfDay(zoneId);

		if (now.isAfter(midnight))
			midnight = midnight.plusDays(1);

		return Duration.between(now, midnight).minusMillis(80).toMillis();
	}

	/*
	 * This function returns the amount of seconds between current time to thursday 5pm
	 */
	public static long getEpicGameDelay() {
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime nextThursday = now.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))
				.withHour(17).withMinute(0).withSecond(0).withNano(0);

		return Duration.between(now, nextThursday).toSeconds();
	}

	/*
	 * Returns the amount of seconds before the next hour
	 */
	public static long getNewsDelay() {
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime nextHour = now.withHour(1).withMinute(0).withSecond(0).withNano(0);

		return Duration.between(now, nextHour).toSeconds();
	}

	/*
	 * This function computes the delta between midnight and the given timestamp. If the message has
	 * been send before midnight, returns a negative delta
	 */
	public static long computeDelta(OffsetDateTime timestamp) {
		ZoneId parisZone = ZoneId.of("Europe/Paris");
		LocalDate today = LocalDate.now(parisZone);
		ZonedDateTime midnight = today.atStartOfDay(parisZone);

		return ChronoUnit.MILLIS.between(midnight, timestamp);
	}

	public static int compareTo(Message a, Message b) {
		Long deltaA = computeDelta(a.getTimeCreated());
		Long deltaB = computeDelta(a.getTimeCreated());

		if (deltaA < 0 && deltaB < 0)
			return deltaB.compareTo(deltaA);
		return deltaA < deltaB ? 1 : -1;
	}

	/*
	 * This function returns true if today is a special day. Used to trigger Miracle Times.
	 */
	public static Boolean isSpecialDay() {
		String[] specialDays = {"14-02", // Valentine's day
				"01-04", // April's Fools
				"31-10", // Halloween
				"25-12", // Christmas
				"01-01", // New year
				"10-06" // Server Anniversary
		};

		LocalDate currentDate = LocalDate.now();
		String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM"));

		for (String specialDay : specialDays)
			if (formattedCurrentDate.equals(specialDay))
				return true;
		return false;
	}

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

	public String getQuote() {
		if (isSpecialDay()) {
			// get the specific quote depending on the day
			// (Inserting April Joke...) Congratulations, the test is now over. Happy April Fool's
			// Day!
		} else {
			// generate random quote
		}
		return "";
	}
}
