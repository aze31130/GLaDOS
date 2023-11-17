package utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import net.dv8tion.jda.api.entities.Message;

public class TimeUtils {
	/*
	 * This function computes the delta between midnight and the given timestamp. If the message has
	 * been send before midnight, returns a negative delta
	 */
	public static long computeDelta(OffsetDateTime timestamp) {
		LocalDate today = LocalDate.now();
		OffsetDateTime midnight = today.atStartOfDay().atOffset(ZoneOffset.UTC);

		return Duration.between(midnight.toInstant(), timestamp.plusHours(1).toInstant())
				.toMillis();
	}

	public static int compareTo(Message a, Message b) {
		Long deltaA = computeDelta(a.getTimeCreated());
		Long deltaB = computeDelta(a.getTimeCreated());

		if (deltaA < 0 && deltaB < 0)
			return deltaB.compareTo(deltaA);
		return deltaA < deltaB ? 1 : -1;
	}

	/*
	 * This function returns true if today is a special day.
	 */
	public static Boolean isSpecialDay() {
		String[] specialDays = {"14-02", // Valentine's day
				"01-04", // April's Fools
				"31-10", // Halloween
				"25-12", // Christmas
				"01-01" // New year
		};

		LocalDate currentDate = LocalDate.now();
		String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM"));

		for (String specialDay : specialDays)
			if (formattedCurrentDate.equals(specialDay))
				return true;
		return false;
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
